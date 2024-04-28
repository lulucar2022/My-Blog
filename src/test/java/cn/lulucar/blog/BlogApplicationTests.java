package cn.lulucar.blog;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.lulucar.blog.service.impl.AdminUserServiceImpl;
import cn.lulucar.blog.util.PasswordEncodeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class BlogApplicationTests {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    AdminUserServiceImpl adminUserService;
    @Test
    
    void contextLoads() {
        String userName = "root";
        String password = "123456";
        String nickName = "lulucar";
        Boolean b = adminUserService.signUp(userName,nickName ,passwordEncoder.encode(password));
        Assertions.assertTrue(b);
    }

}
