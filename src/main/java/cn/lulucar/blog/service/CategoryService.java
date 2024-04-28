package cn.lulucar.blog.service;



import cn.lulucar.blog.entity.BlogCategory;
import cn.lulucar.blog.util.PageQueryUtil;
import cn.lulucar.blog.util.PageResult;

import java.util.List;

public interface CategoryService {

    /**
     * 查询分类的分页数据
     *
     * @param pageUtil 分页工具类
     * @return
     */
    PageResult getBlogCategoryPage(PageQueryUtil pageUtil);

    int getTotalCategories();

    /**
     * 添加分类数据
     *
     * @param categoryName 分类名称
     * @param categoryIcon 分类图标
     * @return
     */
    Boolean saveCategory(String categoryName,String categoryIcon);

    /**
     *
     * @param categoryId
     * @param categoryName
     * @param categoryIcon
     * @return
     */
    Boolean updateCategory(Integer categoryId, String categoryName, String categoryIcon);

    /**
     * 批量删除
     * @param ids 分类Id
     * @return
     */
    Boolean deleteBatch(Integer[] ids);

    /**
     * 获取所有分类
     * @return 返回分类集合
     */
    List<BlogCategory> getAllCategories();
}
