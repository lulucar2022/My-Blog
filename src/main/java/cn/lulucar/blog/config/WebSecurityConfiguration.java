package cn.lulucar.blog.config;

import cn.lulucar.blog.mapper.AdminUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author wenxiaolan
 * @ClassName WebSecurityConfiguration
 * @date 2024/4/23 12:23
 */
@Slf4j
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfiguration {
    @Autowired
    AdminUserMapper adminUserMapper;
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
                                
        // 请求授权
        http.authorizeHttpRequests(register -> {
            // register.requestMatchers("/","/index.html","/index").permitAll()  // 1. 允许 所有人都可以访问 pattern= "/"
            //         .requestMatchers("/admin/dist/**","/admin/plugins/**","/admin/login").permitAll()
            //         .requestMatchers("/common/getCode").permitAll()
            //         .anyRequest().authenticated();        // 2. 除了上面，剩下的请求都需要认证（登录认证）
            register.anyRequest().permitAll();
        });
        // 表单功能
        // 3. 表单登录功能：开启默认表单登录功能，由 SpringSecurity 提供
        // http.formLogin(
        //         httpSecurityFormLoginConfigurer -> {
        //             // 自定义 登录页面，并允许所有人都可以访问。
        //             httpSecurityFormLoginConfigurer.loginPage("/admin/login").permitAll()
        //                     .defaultSuccessUrl("/admin/index").permitAll();
        //         }
        // );
        http.logout(LogoutConfigurer::permitAll);
        http.csrf(AbstractHttpConfigurer::disable);
        http.headers(headr -> {
            headr.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
        });
        return http.build() ;
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
