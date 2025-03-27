package cn.lulucar.blog.limit;

import cn.lulucar.blog.entity.RateLimitRule;
import cn.lulucar.blog.mapper.RateLimitRuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wenxiaolan
 * @ClassName TokenBucketRateLimiter
 * @date 2024/10/24 18:48
 * @description
 */
@Slf4j
@Component
public class TokenBucketRateLimiter {
    private final ConcurrentHashMap<String, TokenBucket> buckets = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler;
    private final RateLimitRuleMapper rateLimitRuleMapper;
    @Autowired
    public TokenBucketRateLimiter(RateLimitRuleMapper rateLimitRuleMapper) {
        
        this.rateLimitRuleMapper = rateLimitRuleMapper;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        loadRules();
    }

    private void loadRules() {
        List<RateLimitRule> rules = rateLimitRuleMapper.findAll();
        for (RateLimitRule rule : rules) {
            String key = rule.getDimension();
            TokenBucket bucket = new TokenBucket(rule.getCapacity(), rule.getRefillRate(), scheduler);
            bucket.startRefilling();
            buckets.put(key, bucket);
        }
    }

    public synchronized boolean allowRequest(String key) {
        TokenBucket bucket = buckets.get(key);
        if (bucket == null) {
            bucket = createDefaultBucket();
            buckets.put(key, bucket);
        }
        return bucket.allowRequest();
    }

    private synchronized TokenBucket createDefaultBucket() {
        int defaultCapacity = 10;
        double defaultRefillRate = 1;
        TokenBucket bucket = new TokenBucket(defaultCapacity, defaultRefillRate, scheduler);
        bucket.startRefilling();
        return bucket;
    }

    public void shutdown() {
        scheduler.shutdown();
        buckets.values().forEach(TokenBucket::shutdown);
    }

    static class TokenBucket {
        private final int capacity;
        private final double refillRate;
        private final ScheduledExecutorService scheduler;
        private int tokenCount;
        private long lastRefillTime;

        public TokenBucket(int capacity, double refillRate, ScheduledExecutorService scheduler) {
            this.capacity = capacity;
            this.refillRate = refillRate;
            this.scheduler = scheduler;
            this.tokenCount = capacity;
            this.lastRefillTime = System.currentTimeMillis();
        }

        private synchronized void startRefilling() {
            scheduler.scheduleAtFixedRate(this::refillTokens, 0, 1, TimeUnit.SECONDS);
        }

        private synchronized void refillTokens() {
            long currentTime = System.currentTimeMillis();
            int tokensToAdd = (int) ((currentTime - lastRefillTime) * refillRate / 1000);
            tokenCount = Math.min(capacity, tokenCount + tokensToAdd);
            lastRefillTime = currentTime;
        }

        public synchronized boolean allowRequest() {
            if (tokenCount > 0) {
                tokenCount--;
                return true;
            }
            return false;
        }

        public void shutdown() {
            scheduler.shutdown();
        }
    }
}
