package cn.lulucar.blog.controller.admin;

import cn.lulucar.blog.config.Constants;
import cn.lulucar.blog.entity.Blog;
import cn.lulucar.blog.service.BlogService;
import cn.lulucar.blog.service.CategoryService;
import cn.lulucar.blog.util.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author wenxiaolan
 * @ClassName BlogController
 * @date 2024/4/29 22:49
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class BlogController {
    @Resource
    private BlogService blogService;
    @Resource
    private CategoryService categoryService;
    

    /**
     * 博客列表数据
     * @param params 分页参数
     * @return 返回分页后的博客列表数据
     */
    @GetMapping("/blogs/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))) {
            log.error("分页参数异常：page={}，limit={}",params.get("page"),params.get("limit"));
            return ResultGenerator.genFailResult("参数异常");
        }
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(blogService.getBlogsPage(pageQueryUtil));
    }

    /**
     * 博客管理页面
     * @param request 请求
     * @return 跳转到博客管理页面
     */
    @GetMapping("/blogs")
    public String list(HttpServletRequest request) {
        request.setAttribute("path","blogs");
        return "admin/blog";
    }

    /**
     * 发布/修改博客
     * @param request 请求
     * @return 跳转到发布博客页面
     */
    @GetMapping("/blogs/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        request.setAttribute("categories", categoryService.getAllCategories());
        return "admin/edit";
    }

    /**
     * 根据 blogId 编辑博客
     * @param request 请求
     * @param blogId 博客Id
     * @return
     */
    @GetMapping("/blogs/edit/{blogId}")
    public String edit(HttpServletRequest request, @PathVariable("blogId") Long blogId) {
        request.setAttribute("path", "edit");
        Blog blog = blogService.getBlogById(blogId);
        if (blog == null) {
            log.error("未找到该博客");
            return "error/error_400";
        }
        log.info("blogId={}",blogId);
        log.info("blog内容：{}",blog.getBlogContent());
        request.setAttribute("blog", blog);
        request.setAttribute("categories", categoryService.getAllCategories());
        return "admin/edit";
    }

    /**
     * 保存博客
     * 
     * @param blogTitle 博客标题
     * @param blogSubUrl 博客子Url
     * @param blogCategoryId 博客分类Id
     * @param blogTags 博客标签
     * @param blogContent 博客内容
     * @param blogCoverImage 博客封面
     * @param blogStatus 博客发布状态
     * @param enableComment 能否评论
     * @return
     */
    @PostMapping("/blogs/save")
    @ResponseBody
    public Result save(@RequestParam("blogTitle") String blogTitle,
                       @RequestParam(name = "blogSubUrl", required = false) String blogSubUrl,
                       @RequestParam("blogCategoryId") Integer blogCategoryId,
                       @RequestParam("blogTags") String blogTags,
                       @RequestParam("blogContent") String blogContent,
                       @RequestParam("blogCoverImage") String blogCoverImage,
                       @RequestParam("blogStatus") Byte blogStatus,
                       @RequestParam("enableComment") Byte enableComment) {
        if (!StringUtils.hasText(blogTitle)) {
            return ResultGenerator.genFailResult("请输入文章标题");
        }
        if (blogTitle.trim().length() > 150) {
            return ResultGenerator.genFailResult("标题过长");
        }
        if (!StringUtils.hasText(blogTags)) {
            return ResultGenerator.genFailResult("请输入文章标签");
        }
        if (blogTags.trim().length() > 150) {
            return ResultGenerator.genFailResult("标签过长");
        }
        if (blogSubUrl.trim().length() > 150) {
            return ResultGenerator.genFailResult("路径过长");
        }
        if (!StringUtils.hasText(blogContent)) {
            return ResultGenerator.genFailResult("请输入文章内容");
        }
        if (blogTags.trim().length() > 100000) {
            return ResultGenerator.genFailResult("文章内容过长");
        }
        if (!StringUtils.hasText(blogCoverImage)) {
            return ResultGenerator.genFailResult("封面图不能为空");
        }
        Blog blog = new Blog();
        blog.setBlogTitle(blogTitle);
        blog.setBlogSubUrl(blogSubUrl);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogTags(blogTags);
        blog.setBlogContent(blogContent);
        blog.setBlogCoverImage(blogCoverImage);
        blog.setBlogStatus(blogStatus);
        blog.setEnableComment(enableComment);
        String saveBlogResult = blogService.saveBlog(blog);
        if ("success".equals(saveBlogResult)) {
            return ResultGenerator.genSuccessResult("添加成功");
        } else {
            return ResultGenerator.genFailResult(saveBlogResult);
        }
    }

    /**
     * 更新博客
     * 
     * @param blogId
     * @param blogTitle
     * @param blogSubUrl
     * @param blogCategoryId
     * @param blogTags
     * @param blogContent
     * @param blogCoverImage
     * @param blogStatus
     * @param enableComment
     * @return
     */
    @PostMapping("/blogs/update")
    @ResponseBody
    public Result update(@RequestParam("blogId") Long blogId,
                         @RequestParam("blogTitle") String blogTitle,
                         @RequestParam(name = "blogSubUrl", required = false) String blogSubUrl,
                         @RequestParam("blogCategoryId") Integer blogCategoryId,
                         @RequestParam("blogTags") String blogTags,
                         @RequestParam("blogContent") String blogContent,
                         @RequestParam("blogCoverImage") String blogCoverImage,
                         @RequestParam("blogStatus") Byte blogStatus,
                         @RequestParam("enableComment") Byte enableComment) {
        if (!StringUtils.hasText(blogTitle)) {
            return ResultGenerator.genFailResult("请输入文章标题");
        }
        if (blogTitle.trim().length() > 150) {
            return ResultGenerator.genFailResult("标题过长");
        }
        if (!StringUtils.hasText(blogTags)) {
            return ResultGenerator.genFailResult("请输入文章标签");
        }
        if (blogTags.trim().length() > 150) {
            return ResultGenerator.genFailResult("标签过长");
        }
        if (blogSubUrl.trim().length() > 150) {
            return ResultGenerator.genFailResult("路径过长");
        }
        if (!StringUtils.hasText(blogContent)) {
            return ResultGenerator.genFailResult("请输入文章内容");
        }
        if (blogTags.trim().length() > 100000) {
            return ResultGenerator.genFailResult("文章内容过长");
        }
        if (!StringUtils.hasText(blogCoverImage)) {
            return ResultGenerator.genFailResult("封面图不能为空");
        }
        Blog blog = new Blog();
        blog.setBlogId(blogId);
        blog.setBlogTitle(blogTitle);
        blog.setBlogSubUrl(blogSubUrl);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogTags(blogTags);
        blog.setBlogContent(blogContent);
        blog.setBlogCoverImage(blogCoverImage);
        log.info("图片路径：{}",blogCoverImage);
        blog.setBlogStatus(blogStatus);
        blog.setEnableComment(enableComment);
        String updateBlogResult = blogService.updateBlog(blog);
        if ("success".equals(updateBlogResult)) {
            log.info("文章修改成功");
            return ResultGenerator.genSuccessResult("修改成功");
        } else {
            return ResultGenerator.genFailResult(updateBlogResult);
        }
    }
    
    @PostMapping("/blogs/md/uploadfile")
    public void uploadFileByEditormd(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(name = "editormd-image-file", required = true)
                                     MultipartFile file) throws IOException, URISyntaxException {
        String fileName = file.getOriginalFilename();
        log.debug("原文件名称：{}",fileName);
        assert fileName != null;
        // 生成文件名方法
        String newFileName = FileNameGenerator.generateNewFileName(fileName);
        log.debug("新文件名称：{}",newFileName);
        // 创建文件
        File destFile = new File(Constants.getFileUploadPath() + newFileName);
        log.debug("目标文件名称：{}",destFile);
        // 生成文件URL
        String fileUrl = UrlUtil.getHost(new URI(request.getRequestURI() )) + "/upload/" + newFileName;
        log.debug("文件url：{}",fileUrl);
        // 文件存放目录
        File fileDirectory = new File(Constants.getFileUploadPath());
        log.debug("文件目录：{}",fileDirectory);
        // 确保文件目录存在
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件创建失败，路径为："+fileDirectory);
                }
            }
            // 传输文件
            file.transferTo(destFile);
            // 请求响应设置
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            response.getWriter().write("{\"success\": 1, \"message\":\"success\",\"url\":\"" + fileUrl + "\"}");
        } catch (IOException e) {
            response.getWriter().write("{\"success\":0}");
        }

    }

    /**
     * 批量删除博客
     * @param ids 博客ids
     * @return
     */
    @PostMapping("/blogs/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        // 未传参数
        if (ids.length < 1) {
            log.error("参数异常：{}", (Object[]) ids);
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (blogService.deleteBatch(ids)) {
            log.info("博客删除成功ids：{}", (Object[]) ids);
            return ResultGenerator.genSuccessResult();
        } else {
            log.error("博客删除失败");
            return ResultGenerator.genFailResult("博客删除失败");
        }
    }
}
