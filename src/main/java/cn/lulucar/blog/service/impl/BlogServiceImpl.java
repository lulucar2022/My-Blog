package cn.lulucar.blog.service.impl;

import cn.lulucar.blog.controller.vo.BlogDetailVO;
import cn.lulucar.blog.controller.vo.SimpleBlogListVO;
import cn.lulucar.blog.entity.Blog;
import cn.lulucar.blog.mapper.*;
import cn.lulucar.blog.service.BlogService;
import cn.lulucar.blog.util.PageQueryUtil;
import cn.lulucar.blog.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wenxiaolan
 * @ClassName BlogServiceImpl
 * @date 2024/4/21 16:16
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogCategoryMapper categoryMapper;
    /**
     * 保存博客
     * @param blog Blog类型 博客
     * @return
     */
    @Override
    @Transactional
    public String saveBlog(Blog blog) {

        return null;
    }

    @Override
    public PageResult getBlogsPage(PageQueryUtil pageUtil) {
        return null;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return null;
    }

    @Override
    public int getTotalBlogs() {
        return 0;
    }

    /**
     * 根据id获取详情
     *
     * @param blogId
     * @return
     */
    @Override
    public Blog getBlogById(Long blogId) {
        return null;
    }

    /**
     * 后台修改
     *
     * @param blog
     * @return
     */
    @Override
    public String updateBlog(Blog blog) {
        return null;
    }

    /**
     * 获取首页文章列表
     *
     * @param page
     * @return
     */
    @Override
    public PageResult getBlogsForIndexPage(int page) {
        return null;
    }

    /**
     * 首页侧边栏数据列表
     * 0-点击最多 1-最新发布
     *
     * @param type
     * @return
     */
    @Override
    public List<SimpleBlogListVO> getBlogListForIndexPage(int type) {
        return null;
    }

    /**
     * 文章详情
     *
     * @param blogId
     * @return
     */
    @Override
    public BlogDetailVO getBlogDetail(Long blogId) {
        return null;
    }

    /**
     * 根据标签获取文章列表
     *
     * @param tagName
     * @param page
     * @return
     */
    @Override
    public PageResult getBlogsPageByTag(String tagName, int page) {
        return null;
    }

    /**
     * 根据分类获取文章列表
     *
     * @param categoryId
     * @param page
     * @return
     */
    @Override
    public PageResult getBlogsPageByCategory(String categoryId, int page) {
        return null;
    }

    /**
     * 根据搜索获取文章列表
     *
     * @param keyword
     * @param page
     * @return
     */
    @Override
    public PageResult getBlogsPageBySearch(String keyword, int page) {
        return null;
    }

    @Override
    public BlogDetailVO getBlogDetailBySubUrl(String subUrl) {
        return null;
    }
}
