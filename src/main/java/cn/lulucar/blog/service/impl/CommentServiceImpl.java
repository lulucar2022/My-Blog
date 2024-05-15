package cn.lulucar.blog.service.impl;

import cn.lulucar.blog.entity.BlogComment;
import cn.lulucar.blog.mapper.BlogCommentMapper;
import cn.lulucar.blog.service.CommentService;
import cn.lulucar.blog.util.PageQueryUtil;
import cn.lulucar.blog.util.PageResult;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wenxiaolan
 * @ClassName CommentServiceImpl
 * @date 2024/4/22 16:11
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
   
    private final BlogCommentMapper blogCommentMapper;

    public CommentServiceImpl(BlogCommentMapper blogCommentMapper) {
        this.blogCommentMapper = blogCommentMapper;
    }

   
    
    /**
     * 添加评论
     *
     * @param blogComment
     * @return
     */
    @Override
    public Boolean addComment(BlogComment blogComment) {
        return blogCommentMapper.insertSelective(blogComment)>0;
    }

    /**
     * 后台管理系统中评论分页功能
     *
     * @param pageUtil
     * @return
     */
    @Override
    public PageResult getCommentsPage(PageQueryUtil pageUtil) {
        List<BlogComment> blogCommentList = blogCommentMapper.findBlogCommentList(pageUtil);
        int totalCount = blogCommentMapper.getTotalBlogComments(pageUtil);
        return new PageResult(blogCommentList,totalCount, pageUtil.getLimit(), pageUtil.getPage());
    }

    @Override
    public int getTotalComments() {
        return blogCommentMapper.getTotalBlogComments(null);
    }

    /**
     * 批量审核
     * 更新comment通过的status
     * @param ids
     * @return
     */
    @Override
    public Boolean checkDone(Integer[] ids) {
        return blogCommentMapper.updateStatusOfComment(ids)>0;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return blogCommentMapper.deleteBatch(ids) > 0;
    }

    /**
     * 添加回复
     *
     * @param commentId 评论Id
     * @param replyBody 被回复的对象
     * @return
     */
    @Override
    public Boolean reply(Long commentId, String replyBody) {
        BlogComment blogComment = blogCommentMapper.selectByPrimaryKey(commentId);
        // 判断 blogComment是否存在和审核状态
        if (blogComment != null && blogComment.getCommentStatus().intValue() == 1) {
            blogComment.setReplyBody(replyBody);
            blogComment.setReplyCreateTime(LocalDateTime.now());
            return blogCommentMapper.updateByPrimaryKeySelective(blogComment) > 0;
        }
        return false;
    }

    /**
     * 根据文章id和分页参数获取文章的评论列表
     *
     * @param blogId 文章Id
     * @param page 页码
     * @return
     */
    @Override
    public PageResult getCommentPageByBlogIdAndPageNum(Long blogId, int page) {
        // page 不可以小于 1
        if (page < 1) {
            return null;
        }
        // 封装 pageQueryUtil 对象
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        // 规定每页 8条数据
        map.put("limit",8);
        map.put("blogId",blogId);
        // 审核通过的数据
        map.put("commentStatus", 1);
        PageQueryUtil pageQueryUtil = new PageQueryUtil(map);
        // blogCommentList 接收 评论数据
        List<BlogComment> blogCommentList = blogCommentMapper.findBlogCommentList(pageQueryUtil);
        if (!(CollectionUtils.isEmpty(blogCommentList))){
            int totalCount = blogCommentMapper.getTotalBlogComments(pageQueryUtil);
            // 把 评论数据列表 再次封装
            return new PageResult(blogCommentList,totalCount, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        }
        return null;
    }

    
}
