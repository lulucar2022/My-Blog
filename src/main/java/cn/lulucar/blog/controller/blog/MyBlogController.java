package cn.lulucar.blog.controller.blog;

import cn.hutool.captcha.ShearCaptcha;
import cn.lulucar.blog.controller.vo.BlogDetailVO;
import cn.lulucar.blog.entity.BlogComment;
import cn.lulucar.blog.entity.BlogLink;
import cn.lulucar.blog.service.*;
import cn.lulucar.blog.util.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author wenxiaolan
 * @ClassName MyBlog
 * @date 2024/5/6 15:07
 */
@Slf4j
@Controller
public class MyBlogController {
    // 主题
    public static String theme = "amaze";
    // 另外一个主题
    // public static String theme = "yummy-jekyll";
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

    /**
     * 前台首页访问
     * @param request http请求
     * @return 携带首页数据跳转到前台首页
     */
    @GetMapping({"/","/index","index.html"})
    public String index(HttpServletRequest request) {
        // 返回 首页分页数据的第1页
        return page(request,1);
    }

    /**
     * 查询首页博客文章分页数据
     * 
     * @param request http请求
     * @param pageNum 页码
     * @return 
     */
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
    

    /**
     * 查询分类列表
     * 这个只能在 主题theme为yummy-jekyll 才能用
     * @param request
     * @return
     */
    @GetMapping("/categories")
    public String categories(HttpServletRequest request) {
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("categories", categoryService.getAllCategories());
        request.setAttribute("pageName", "分类页面");
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/category";
    }

    /**
     * 根据 Id 查询博客文章详情
     * @param request http请求
     * @param blogId 博客id
     * @param commentPage 评论列表
     * @return
     */
    @GetMapping("/blog/{blogId}")
    public String detail(HttpServletRequest request, @PathVariable("blogId") Long blogId,
                         @RequestParam(value = "commentPage", required = false, defaultValue = "1") Integer commentPage) {
        BlogDetailVO blogDetailVO = blogService.getBlogDetail(blogId);
        // 如果博客文章存在
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO);
            request.setAttribute("commentPageResult", commentService.getCommentPageByBlogIdAndPageNum(blogId,commentPage));
            
        }
        // 设置页面名称 和 网站配置信息
        request.setAttribute("pageName","详情");
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/detail";
    }

    /**
     * 标签页数据
     * @param model 数据模型 是Controller 与 View 之间的桥梁
     * @param tagName 标签名称
     * @return
     */
    @GetMapping("/tag/{tagName}")
    public String tag(Model model, @PathVariable("tagName") String tagName) {
        return tag(model,tagName,1);
    }

    /**
     * 根据标签查询博客文章分页数据列表
     * @param model 数据模型
     * @param tagName 标签名称
     * @param page 页码
     * @return
     */
    @GetMapping({"/tag/{tagName}/{page}"})
    public String tag(Model model, @PathVariable("tagName") String tagName, @PathVariable("page") Integer page) {
        PageResult blogPageResult = blogService.getBlogsPageByTag(tagName, page);
        model.addAttribute("blogPageResult", blogPageResult);
        model.addAttribute("pageName", "标签");
        model.addAttribute("pageUrl", "tag");
        model.addAttribute("keyword", tagName);
        model.addAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        model.addAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        model.addAttribute("hotTags", tagService.getBlogTagCountForIndex());
        model.addAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/list";
    }

    /**
     * 分类页数据
     * @param model 数据模型
     * @param categoryName 分类名称
     * @return
     */
    @GetMapping("/categories/{categoryName}")
    public String category(Model model, @PathVariable("categoryName") String categoryName) {
        return category(model,categoryName,1);
    }

    /**
     * 根据分类查询博客文章分页数据列表
     * @param model 数据模型
     * @param categoryName 分类名称
     * @param page 页码
     * @return
     */
    @GetMapping("/categories/{categoryName}/{page}")
    public String category(Model model, @PathVariable("categoryName") String categoryName,
                           @PathVariable("page") Integer page) {
        PageResult blogPageResult = blogService.getBlogsPageByCategory(categoryName,page);
        model.addAttribute("blogPageResult",blogPageResult);
        model.addAttribute("pageName", "分类");
        model.addAttribute("pageUrl", "category");
        model.addAttribute("keyword", categoryName);
        model.addAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        model.addAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        model.addAttribute("hotTags", tagService.getBlogTagCountForIndex());
        model.addAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/list";
    }

    /**
     * 搜索页数据
     * @param model
     * @param keyword
     * @return
     */
    @GetMapping("/search/{keyword}")
    public String search(Model model, @PathVariable("keyword") String keyword) {
        return search(model,keyword,1);
    }

    /**
     * 根据关键字搜索博客文章分页数据
     * @param model 数据模型
     * @param keyword 搜索关键字
     * @param page 页码
     * @return
     */
    @GetMapping("/search/{keyword}/{page}")
    public String search(Model model,
                         @PathVariable("keyword") String keyword,
                         @PathVariable("page") Integer page) {
        PageResult blogPageResult = blogService.getBlogsPageBySearch(keyword,page);
        model.addAttribute("blogPageResult",blogPageResult);
        model.addAttribute("pageName", "搜索");
        model.addAttribute("pageUrl", "search");
        model.addAttribute("keyword", keyword);
        model.addAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        model.addAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        model.addAttribute("hotTags", tagService.getBlogTagCountForIndex());
        model.addAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/list";
    }

    /**
     * 友联页面
     * @param model 数据模型
     * @return
     */
    @GetMapping("/link")
    public String link(Model model) {
        model.addAttribute("pageName", "友情链接");
        // 用Map集合封装三种链接类型
        Map<Byte, List<BlogLink>> linkMap = linkService.getLinksForLinkPage();
        if (linkMap != null ) {
            // 判断友联的类型，封装成三种数据
            if (linkMap.containsKey((byte) 0)){
                model.addAttribute("favoriteLinks", linkMap.get((byte) 0));
            }
            if (linkMap.containsKey((byte) 1)) {
                model.addAttribute("recommendLinks", linkMap.get((byte) 1));
            }
            if (linkMap.containsKey((byte) 2)) {
                model.addAttribute("personalLinks", linkMap.get((byte) 2));
            }
        }
        model.addAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/link";
    }

    /**
     * 发表评论
     * @param request http请求
     * @param session 会话
     * @param blogId 博客id
     * @param verifyCode 验证码
     * @param commentator 评论者
     * @param email 评论者的邮箱
     * @param websiteUrl 评论者的个人网站
     * @param commentBody 评论内容
     * @return
     */
    @PostMapping(value = "/blog/comment")
    @ResponseBody
    public Result comment(HttpServletRequest request, HttpSession session,
                          @RequestParam Long blogId, @RequestParam String verifyCode,
                          @RequestParam String commentator, @RequestParam String email,
                          @RequestParam String websiteUrl, @RequestParam String commentBody) {
        if (!StringUtils.hasText(verifyCode)) {
            return ResultGenerator.genFailResult("验证码不能为空");
        }
        ShearCaptcha shearCaptcha = (ShearCaptcha) session.getAttribute("verifyCode");
        if (shearCaptcha == null || !shearCaptcha.verify(verifyCode)) {
            return ResultGenerator.genFailResult("验证码错误");
        }
        String ref = request.getHeader("Referer");
        if (!StringUtils.hasText(ref)) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if (null == blogId || blogId < 0) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if (!StringUtils.hasText(commentator)) {
            return ResultGenerator.genFailResult("请输入称呼");
        }
        if (!StringUtils.hasText(email)) {
            return ResultGenerator.genFailResult("请输入邮箱地址");
        }
        if (!PatternUtil.isEmail(email)) {
            return ResultGenerator.genFailResult("请输入正确的邮箱地址");
        }
        if (!StringUtils.hasText(commentBody)) {
            return ResultGenerator.genFailResult("请输入评论内容");
        }
        if (commentBody.trim().length() > 200) {
            return ResultGenerator.genFailResult("评论内容过长");
        }
        BlogComment comment = new BlogComment();
        comment.setBlogId(blogId);
        comment.setCommentator(UrlUtil.cleanString(commentator));
        comment.setEmail(email);
        if (PatternUtil.isURL(websiteUrl)) {
            comment.setWebsiteUrl(websiteUrl);
        }
        comment.setCommentBody(UrlUtil.cleanString(commentBody));
        return ResultGenerator.genSuccessResult(commentService.addComment(comment));
    }

    /**
     * 跳转内部链接
     * @param request
     * @param subUrl
     * @return
     */
    @GetMapping({"/{subUrl}"})
    public String detail(HttpServletRequest request, @PathVariable("subUrl") String subUrl) {
        BlogDetailVO blogDetailVO = blogService.getBlogDetailBySubUrl(subUrl);
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO);
            request.setAttribute("pageName", subUrl);
            request.setAttribute("configurations", configService.getAllConfigs());
            return "blog/" + theme + "/detail";
        } else {
            return "error/error_400";
        }
    }
}
