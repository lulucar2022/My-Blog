package cn.lulucar.blog.service;


import cn.lulucar.blog.vo.BlogDetailVO;
import cn.lulucar.blog.vo.SimpleBlogListVO;
import cn.lulucar.blog.entity.Blog;
import cn.lulucar.blog.util.PageQueryUtil;
import cn.lulucar.blog.util.PageResult;

import java.util.List;

public interface BlogService {
    String saveBlog(Blog blog);

    PageResult getBlogsPage(PageQueryUtil pageUtil);

    Boolean deleteBatch(Integer[] ids);

    int getTotalBlogs();

    /**
     * 根据id获取详情
     *
     * @param blogId 博客Id
     * @return
     */
    Blog getBlogById(Long blogId);

    /**
     * 后台修改
     *
     * @param blog 博客实体类
     * @return
     */
    String updateBlog(Blog blog);

    /**
     * 获取首页文章列表
     *
     * @param page 当前页码
     * @return
     */
    PageResult getBlogsForIndexPage(int page);

    /**
     * 首页侧边栏数据列表
     * 0-点击最多 1-最新发布
     *
     * @param type
     * @return
     */
    List<SimpleBlogListVO> getBlogListForIndexPage(int type);
    /**
     * 文章详情
     *
     * @param blogId
     * @return
     */
    BlogDetailVO getBlogDetail(Long blogId);

    /**
     * 根据标签获取文章列表
     *
     * @param tagName
     * @param page
     * @return
     */
    PageResult getBlogsPageByTag(String tagName, int page);

    /**
     * 根据分类获取文章列表
     *
     * @param categoryName
     * @param page
     * @return
     */
    PageResult getBlogsPageByCategory(String categoryName, int page);

    /**
     * 根据搜索获取文章列表
     *
     * @param keyword
     * @param page
     * @return
     */
    PageResult getBlogsPageBySearch(String keyword, int page);

    BlogDetailVO getBlogDetailBySubUrl(String subUrl);
}
