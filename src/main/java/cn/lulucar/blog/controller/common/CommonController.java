package cn.lulucar.blog.controller.common;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Slf4j
@Controller

public class CommonController {
    @RequestMapping("common/getCode")
    public void getCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        // 禁止图片缓存
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/png");

        // 定义图形验证码的长、宽、验证码字符数、干扰线宽度 
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(150, 30, 4, 2);
        try {
            // 验证码存入session
            httpServletRequest.getSession().setAttribute("verifyCode", shearCaptcha);
            // 输出图片流
            shearCaptcha.write(httpServletResponse.getOutputStream());
            // 打印日志
            log.info("生成的验证码:{}", shearCaptcha.getCode());
            // 关闭流
            httpServletResponse.getOutputStream().close();
        } catch (IOException e) {
            log.error(String.valueOf(e));
        }

        

        
    }
}

