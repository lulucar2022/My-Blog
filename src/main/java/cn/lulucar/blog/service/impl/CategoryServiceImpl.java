package cn.lulucar.blog.service.impl;

import cn.lulucar.blog.entity.BlogCategory;
import cn.lulucar.blog.mapper.BlogCategoryMapper;
import cn.lulucar.blog.mapper.BlogMapper;
import cn.lulucar.blog.service.CategoryService;
import cn.lulucar.blog.util.PageQueryUtil;
import cn.lulucar.blog.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wenxiaolan
 * @ClassName CategoryServiceImpl
 * @date 2024/4/21 18:47
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private final BlogMapper blogMapper;
    private final BlogCategoryMapper blogCategoryMapper;

    public CategoryServiceImpl(BlogMapper blogMapper, BlogCategoryMapper blogCategoryMapper) {
        this.blogMapper = blogMapper;
        this.blogCategoryMapper = blogCategoryMapper;
    }

    /**
     * 查询分类的分页数据
     * 分页需要记录总数，当前页码，总页数
     * @param pageUtil 分页工具类
     * @return
     */
    @Override
    public PageResult getBlogCategoryPage(PageQueryUtil pageUtil) {
        List<BlogCategory> blogCategoryList = blogCategoryMapper.findCategoryList(pageUtil);
        int totalCount = blogCategoryMapper.getTotalCategories(pageUtil);
        int pageSize = pageUtil.getLimit();
        int currentPage = pageUtil.getPage();
        return new PageResult(blogCategoryList,totalCount,pageSize,currentPage);
    }

    /**
     * 获取分类总数
     * @return 分类的总数目
     */
    @Override
    public int getTotalCategories() {
        return blogCategoryMapper.getTotalCategories(null);
    }

    /**
     * 添加分类数据
     * 1. 根据分类名称判断是否存在
     * 2. 如果存在，返回 false
     * 3. 不存在，就新增
     * @param categoryName 分类名称
     * @param categoryIcon 分类图标
     * @return
     */
    @Override
    public Boolean saveCategory(String categoryName, String categoryIcon) {
        BlogCategory blogCategory = blogCategoryMapper.selectByCategoryName(categoryName);
        // 判断 是否已经存在，不存在才添加
        if (blogCategory == null){
            blogCategory = new BlogCategory();
            blogCategory.setCategoryName(categoryName);
            blogCategory.setCategoryIcon(categoryIcon);
            // 插入分类
            return blogCategoryMapper.insertSelective(blogCategory) > 0;
        }
        return false;
    }

    /**
     * @param categoryId 分类Id
     * @param categoryName 分类名称
     * @param categoryIcon 分类图标
     * @return
     */
    @Override
    @Transactional
    public Boolean updateCategory(Integer categoryId, String categoryName, String categoryIcon) {
        BlogCategory blogCategory = blogCategoryMapper.selectByPrimaryKey(categoryId);
        // 判断 分类是否存在，存在才继续更新
        if (blogCategory != null) {
            blogCategory.setCategoryName(categoryName);
            blogCategory.setCategoryIcon(categoryIcon);
            // 修改blog实体的分类
            // todo 为什么这里要用 分类id数组作为参数去更新 blog实体的分类
            // 因为批量删除分类时，blog实体的分类如果包含在里面，就需要修改blog实体的分类，省去了循环。
            blogMapper.updateBlogCategorys(categoryName, categoryId, new Integer[]{categoryId});
            // 更新分类
            return blogCategoryMapper.updateByPrimaryKeySelective(blogCategory) > 0;
        }
        return false;
    }

    /**
     * 批量删除
     *
     * @param ids 分类Id
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1){
            return false;
        }
        // 修改被涉及到的blog
        blogMapper.updateBlogCategorys("默认分类", 0, ids);
        // 逻辑删除分类
        return blogCategoryMapper.deleteBatch(ids)> 0;
    }

    /**
     * 获取所有未删除的分类
     *
     * @return 返回分类集合
     */
    @Override
    public List<BlogCategory> getAllCategories() {

        return blogCategoryMapper.findCategoryList(null);
    }
}
