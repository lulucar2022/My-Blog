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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * 验证用户身份：首先，需要验证执行修改操作的用户是否是其账户的所有者。这通常通过验证用户的登录状态以及对应的会话信息来实现。
     * 检查用户名可用性：在允许修改之前，需要检查新的用户名称是否已被其他用户占用。如果新的用户名已经被使用，应提示用户选择其他名称。
     * 检查用户名格式：新的用户名应符合预定义的格式要求，例如长度限制、特殊字符限制等。不符合格式要求的用户名应被拒绝，并给出相应的提示。
     * 清理缓存和会话：修改用户名后，可能需要清理相关的缓存和会话信息，以确保系统的一致性。
     * @param request
     * @param loginUserName 登录名称
     * @param nickName 昵称
     * @return
     */
    @PostMapping("/profile/name")
    @ResponseBody
    public String nameUpdate(HttpServletRequest request,
                             @RequestParam("loginUserName") String loginUserName,
                             @RequestParam("nickName") String nickName) {
        log.info("用户修改的loginUserName：{}\n用户修改的nickName：{}",loginUserName,nickName);
        if (!StringUtils.hasText(loginUserName) || !StringUtils.hasText(nickName)) {
            log.error("传入的loginUserName或者nickName为空");
            return "参数不能为空";
        }
        // 定义用户名格式的正则表达式  
        // 示例：用户名由字母、数字和下划线组成，长度在4到16个字符之间  
        final Pattern USER_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{4,16}$");
        Matcher matcher = USER_NAME_PATTERN.matcher(loginUserName);
        if (!matcher.matches()) {
            // 返回匹配结果 
            log.error("用户名不合规");
            return "用户名不合规";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updateName(loginUserId,loginUserName, nickName)) {
            return "success";
        } else {
            log.error("用户名修改失败");
            return "修改失败";
        }
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
        final int MIN_LENGTH = 8;
        final int MAX_LENGTH = 20;
        final String PASSWORD_PATTERN =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(newPassword);
        if (!StringUtils.hasText(originalPassword) || !StringUtils.hasText(newPassword)) {
            return "参数不能为空";
        }
        if (newPassword.length() < MIN_LENGTH ) {
            log.error("密码长度为{}，小于 8",newPassword.length());
            return "密码长度小于 8，不符合";
        }
        if (newPassword.length() > MAX_LENGTH) {
            log.error("密码长度为{}，大于 20",newPassword.length());
            return "密码长度大于 20，不符合";
        }
        if (!matcher.matches()){
            log.error("密码至少要1个大小写字母和1个数字");
            return "密码至少要1个大小写字母和1个数字";
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

    /**
     * 用户退出
     * @param request
     * @return 跳转到登录页面
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }
    
}
