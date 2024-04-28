package cn.lulucar.blog.mapper;



import cn.lulucar.blog.entity.BlogTag;
import cn.lulucar.blog.entity.BlogTagCount;
import cn.lulucar.blog.util.PageQueryUtil;

import java.util.List;

public interface BlogTagMapper {
    int deleteByPrimaryKey(Integer tagId);

    int insert(BlogTag record);

    int insertSelective(BlogTag record);

    BlogTag selectByPrimaryKey(Integer tagId);

    BlogTag selectByTagName(String tagName);

    int updateByPrimaryKeySelective(BlogTag record);

    int updateByPrimaryKey(BlogTag record);

    List<BlogTag> findTagList(PageQueryUtil pageUtil);

    /**
     * 返回被使用最多的20个标签
     * @return
     */
    List<BlogTagCount> getTagCount();

    /**
     * 返回 tag 的总数（未删除的）
     * @param pageUtil
     * @return
     */
    int getTotalTags(PageQueryUtil pageUtil);

    int deleteBatch(Integer[] ids);

    int batchInsertBlogTag(List<BlogTag> tagList);
}