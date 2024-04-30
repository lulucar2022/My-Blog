package cn.lulucar.blog.controller.admin;

import cn.hutool.core.util.ObjectUtil;
import cn.lulucar.blog.service.BlogService;
import cn.lulucar.blog.service.CategoryService;
import cn.lulucar.blog.util.PageQueryUtil;
import cn.lulucar.blog.util.Result;
import cn.lulucar.blog.util.ResultGenerator;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author wenxiaolan
 * @ClassName BlogController
 * @date 2024/4/29 22:49
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    @Resource
    private BlogService blogService;
    @Resource
    private CategoryService categoryService;
    
    // todo 完成 Blog 的功能

    /**
     * 博客列表数据
     * @param params 分页参数
     * @return 返回分页后的博客列表数据
     */
    @GetMapping("/blogs/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常");
        }
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(blogService.getBlogsPage(pageQueryUtil));
    }

    /**
     * 博客管理模块
     * @param request 请求
     * @return 跳转到博客管理页面
     */
    @GetMapping("/blogs")
    public String list(HttpServletRequest request) {
        request.setAttribute("path","blogs");
        return "admin/blog";
    }
    
    
    
    
}
