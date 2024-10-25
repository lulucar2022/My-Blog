package cn.lulucar.blog.interceptor;

import cn.lulucar.blog.entity.TokenBucketRateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
    @Autowired
    public TokenBucketInterceptor(TokenBucketRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            // 获取客户端IP或其他标识符
            String ip = request.getRemoteAddr();

            // 检查是否有足够的令牌
            if (!rateLimiter.allowRequest(ip)) {
                response.setStatus(429);    // 请求频率过高
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
