package cn.lulucar.blog.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
    private final int defaultCapacity;
    private final double defaultRefillRate;
    private final ScheduledExecutorService scheduler;

    @Autowired
    public TokenBucketRateLimiter(@Value("${token.bucket.capacity}") int defaultCapacity,
                                  @Value("${token.bucket.refillRate}") double defaultRefillRate) {
        this.defaultCapacity = defaultCapacity;
        this.defaultRefillRate = defaultRefillRate;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public synchronized boolean allowRequest(String ip) {
        TokenBucket bucket = buckets.computeIfAbsent(ip, key -> createNewBucket());
        log.info("Token bucket for IP: {} has {} tokens", ip, bucket.tokenCount);
        return bucket.allowRequest();
    }

    private synchronized TokenBucket createNewBucket() {
        TokenBucket bucket = new TokenBucket(defaultCapacity, defaultRefillRate, scheduler);
        bucket.startRefilling();
        log.info("Created new token bucket for IP: {}", bucket);
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
