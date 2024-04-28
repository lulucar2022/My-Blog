package cn.lulucar.blog.controller.admin;

import cn.hutool.captcha.ShearCaptcha;
import cn.lulucar.blog.entity.AdminUser;
import cn.lulucar.blog.service.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author wenxiaolan
 * @ClassName AdminController
 * @date 2024/4/21 13:46
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private BlogService blogService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private LinkService linkService;
    @Resource
    private TagService tagService;
    @Resource
    private CommentService commentService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    // 登录页面
    @GetMapping({"/login"})
    public String login() {
        log.info("进入 login 页面");
        return "admin/login";
    }

    // 主页
    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        request.setAttribute("categoryCount", categoryService.getTotalCategories());
        request.setAttribute("blogCount", blogService.getTotalBlogs());
        request.setAttribute("linkCount", linkService.getTotalLinks());
        request.setAttribute("tagCount", tagService.getTotalTags());
        request.setAttribute("commentCount", commentService.getTotalComments());
        return "admin/index";
    }

    // 登录请求
    
    @PostMapping(value = "login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {
        
        log.info("userName：{}\n password:{}",userName,password);
        // 用户名/密码/验证码 参数判断
        if (!StringUtils.hasText(verifyCode)){
            session.setAttribute("errorMsg", "验证码不能为空");
            log.warn("验证码不能为空");
        }
        if (!StringUtils.hasText(userName) || !StringUtils.hasText(password)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            
            log.warn("用户名或密码为空");
            return "/admin/login";
        }
        // hutool 工具包
        ShearCaptcha shearCaptcha = (ShearCaptcha) session.getAttribute("verifyCode");
        if (shearCaptcha == null || !shearCaptcha.verify(verifyCode)) {
            session.setAttribute("errorMsg", "验证码错误");
           
            log.warn("验证码错误");

            return "/admin/login";

        }
        
        
        AdminUser adminUser = adminUserService.login(userName, password);
        
        if (adminUser != null) {
            // 登录成功后，给 session 添加 登录信息（用户名+用户Id）
            session.setAttribute("loginUser", adminUser.getNickName());
            session.setAttribute("loginUserId", adminUser.getAdminUserId());
            //session过期时间设置为7200秒 即两小时
            //session.setMaxInactiveInterval(60 * 60 * 2);
            // 成功则跳转的index页面
            
            log.info("登录成功");
            return "redirect:/admin/index";
        } else {
            
            session.setAttribute("errorMsg", "登陆失败");
            log.warn("Session attribute:{}",session.getAttribute("errorMsg"));
            log.error("登录失败");
            return "/admin/login";

        }
        
    }
    // 用户配置信息
    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Integer loginUserId = (Integer) request.getSession().getAttribute("loginUserId");
        log.info("用户Id：{}",loginUserId.toString());
        AdminUser adminUser = adminUserService.getUserDetailById(loginUserId);
        if (adminUser == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("loginUserName", adminUser.getLoginUserName());
        request.setAttribute("nickName", adminUser.getNickName());
        return "admin/profile";
    }

    /**
     * 用户修改密码
     * @param request
     * @param originalPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    @PostMapping("/profile/password")
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request, 
                                 @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (!StringUtils.hasText(originalPassword) || !StringUtils.hasText(newPassword)) {
            return "参数不能为空";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updatePassword(loginUserId, originalPassword, newPassword)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return "success";
        } else {
            return "修改失败";
        }
    }
    
    
    
}
