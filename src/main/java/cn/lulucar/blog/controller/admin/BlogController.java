package cn.lulucar.blog.controller.admin;

import cn.lulucar.blog.service.BlogService;
import cn.lulucar.blog.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
}
