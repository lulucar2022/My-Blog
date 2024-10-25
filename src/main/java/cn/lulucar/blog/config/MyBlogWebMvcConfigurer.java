package cn.lulucar.blog.config;

import cn.lulucar.blog.entity.TokenBucketRateLimiter;
import cn.lulucar.blog.interceptor.AdminLoginInterceptor;
import cn.lulucar.blog.interceptor.TokenBucketInterceptor;
import cn.lulucar.blog.listener.ShutdownListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyBlogWebMvcConfigurer implements WebMvcConfigurer {

    private final TokenBucketRateLimiter tokenBucketRateLimiter;
    private final AdminLoginInterceptor adminLoginInterceptor;

    public MyBlogWebMvcConfigurer(AdminLoginInterceptor adminLoginInterceptor, TokenBucketRateLimiter tokenBucketRateLimiter) {
        this.adminLoginInterceptor = adminLoginInterceptor;
        this.tokenBucketRateLimiter = tokenBucketRateLimiter;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/admin为前缀的url路径
        registry.addInterceptor(adminLoginInterceptor).addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/dist/**")
                .excludePathPatterns("/admin/plugins/**")
                .excludePathPatterns("/admin/register");
        // 添加限流拦截器，拦截所有url路径
        registry.addInterceptor(new TokenBucketInterceptor(tokenBucketRateLimiter)).addPathPatterns("/**");
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + Constants.getFileUploadPath());
    }

    /**
     * 注入组件
     * 关闭应用时，释放令牌桶
     * @return ShutdownListener
     */
    @Bean
    public ShutdownListener shutdownListener() {
        return new ShutdownListener(tokenBucketRateLimiter);
    }
}
