package cn.lulucar.blog.interceptor;

import cn.lulucar.blog.entity.RateLimitLog;
import cn.lulucar.blog.limit.TokenBucketRateLimiter;
import cn.lulucar.blog.mapper.RateLimitLogMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

/**
 * @author wenxiaolan
 * @ClassName TokenBucketInterceptor
 * @date 2024/10/24 18:59
 * @description
 */
@Slf4j
@Component
public class TokenBucketInterceptor implements HandlerInterceptor {
    private final TokenBucketRateLimiter rateLimiter;
    private final RateLimitLogMapper rateLimitLogMapper;
    
    @Autowired
    public TokenBucketInterceptor(TokenBucketRateLimiter rateLimiter, RateLimitLogMapper rateLimitLogMapper) {
        this.rateLimiter = rateLimiter;
        this.rateLimitLogMapper = rateLimitLogMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            // 获取客户端IP或其他标识符
            String ip = request.getRemoteAddr();
            String userId = request.getHeader("User-ID");
            String uri = request.getRequestURI();
            
            // 根据需求选择不同的维度进行限流，这里以 IP 和 接口路径组合为例
            String key = ip + "_" + uri;
            
            // 检查是否有足够的令牌
            if (!rateLimiter.allowRequest(key)) {
                // 请求频率过高
                response.setStatus(429);
                
                // 限流日志记录
                RateLimitLog log = new RateLimitLog();
                log.setIp(ip);
                log.setUserId(userId);
                log.setUri(uri);
                log.setRequestTime(LocalDateTime.now());
                rateLimitLogMapper.insert(log);
                
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 可以在这里做其他处理
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 可以在这里做其他处理
    }
}
