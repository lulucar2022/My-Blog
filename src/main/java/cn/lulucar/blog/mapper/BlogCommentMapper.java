package cn.lulucar.blog.mapper;



import cn.lulucar.blog.entity.BlogComment;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface BlogCommentMapper {
    int deleteByPrimaryKey(Long commentId);

    int insert(BlogComment record);

    int insertSelective(BlogComment record);

    BlogComment selectByPrimaryKey(Long commentId);

    int updateByPrimaryKeySelective(BlogComment record);

    int updateByPrimaryKey(BlogComment record);

    List<BlogComment> findBlogCommentList(Map map);

    int getTotalBlogComments(Map map);

    int updateStatusOfComment(Integer[] ids);

    int deleteBatch(Integer[] ids);
    
    // 查询所有评论的id （未删除、已审核）
    @Select("select comment_id from tb_blog_comment where is_deleted = 0 and comment_status = 1")
    List<Integer> selectAllCommentIds();
}