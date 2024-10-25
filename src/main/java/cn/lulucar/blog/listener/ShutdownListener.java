package cn.lulucar.blog.listener;

import cn.lulucar.blog.entity.TokenBucketRateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @author wenxiaolan
 * @ClassName ShutdownListener
 * @date 2024/10/24 23:34
 * @description
 */
public class ShutdownListener implements ApplicationListener<ContextClosedEvent> {

    private final TokenBucketRateLimiter rateLimiter;

    @Autowired
    public ShutdownListener(TokenBucketRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        rateLimiter.shutdown();
    }
}