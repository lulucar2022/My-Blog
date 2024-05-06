package cn.lulucar.blog.controller.blog;

import cn.lulucar.blog.controller.vo.BlogDetailVO;
import cn.lulucar.blog.service.*;
import cn.lulucar.blog.util.PageResult;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wenxiaolan
 * @ClassName MyBlog
 * @date 2024/5/6 15:07
 */
@Slf4j
@Controller
public class MyBlogController {
    
    public static String theme = "amaze";
    @Resource
    private BlogService blogService;
    @Resource
    private TagService tagService;
    @Resource
    private LinkService linkService;
    @Resource
    private CommentService commentService;
    @Resource
    private ConfigService configService;
    @Resource
    private CategoryService categoryService;
    
    // todo 首页
    @GetMapping({"/","/index","index.html"})
    public String index(HttpServletRequest request) {
        // 返回 首页分页数据的第1页
        return page(request,1);
    }
    
    // todo 首页分页数据
    @GetMapping("/page/{pageNum}")
    public String page(HttpServletRequest request, @PathVariable("pageNum") int pageNum) {
        PageResult blogPageResult = blogService.getBlogsForIndexPage(pageNum);
        // 判断 数据是否存在
        if (blogPageResult == null) {
            return "error/error_404";
        }
        // 给请求设置属性
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("pageName", "首页");
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/index";
    }
    
    // todo 分类页面数据（分类数据和标签数据）
    @GetMapping("/categories")
    public String categories(HttpServletRequest request) {
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("categories", categoryService.getAllCategories());
        request.setAttribute("pageName", "分类页面");
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/category";
    }
    
    // todo 博客文章详情页
    @GetMapping("/blog/{blogId}")
    public String detail(HttpServletRequest request, @PathVariable("blogId") Long blogId,
                         @RequestParam(value = "commentPage", required = false, defaultValue = "1") Integer commentPage) {
        BlogDetailVO blogDetailVO = blogService.getBlogDetail(blogId);
        // 如果博客文章存在，就传输博客和评论
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO);
            request.setAttribute("commentPageResult", commentService.getCommentPageByBlogIdAndPageNum(blogId,commentPage));
            
        }
        // 设置页面名称 和 网站配置信息
        request.setAttribute("pageName","详情");
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/detail";
    }
    // todo 标签列表首页
    
    // todo 标签列表分页查询
    
    // todo 分类列表首页
    
    // todo 分类列表分页查询
    
    // todo 搜索列表首页
    
    // todo 搜索列表分页查询
    
    // todo 友联列表首页
    
    // todo 评论操作
    
    // todo subUrl配置页面
}
