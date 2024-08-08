package cn.lulucar.blog.service.redisService;

import cn.lulucar.blog.mapper.BlogCommentMapper;
import cn.lulucar.blog.mapper.BlogMapper;
import cn.lulucar.blog.util.RedisUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wenxiaolan
 * @ClassName redisService
 * @date 2024/8/8 3:10
 * @description
 */
@Service
@Slf4j
public class redisService {
    private static final String BLOG_KEY_PREFIX = "blog:";
    private static final String BLOG_ID_LIST_KEY = "blog:id:list";
    private static final String COMMENT_ID_LIST_KEY = "comment:id:list";
    private static final String COMMENT_KEY_PREFIX = "comment:";
    private final RedisUtils redisUtils;
    private final BlogMapper blogMapper;
    private final BlogCommentMapper blogCommentMapper;
    public redisService(RedisUtils redisUtils, BlogMapper blogMapper, BlogCommentMapper blogCommentMapper) {
        this.redisUtils = redisUtils;
        this.blogMapper = blogMapper;
        this.blogCommentMapper = blogCommentMapper;
    }

    /**
     * 在 Spring Bean 初始化完成后自动执行的方法。
     */
    @PostConstruct
    public void initArticleIdsCache() {
        // =========== blog
        setBlogIds();
        setBlogList();
        // =========== comment
        setCommentIds();
        setCommentList();
    }
    // =================== blog ===================

    /**
     * 将所有博客的 ID 存储到 Redis 中。
     */
    public void setBlogIds() {
        List<Integer> allBlogIds = blogMapper.getAllBlogIds();
        try {
            // 不存在则添加
            if (!redisUtils.hasKey(BLOG_ID_LIST_KEY)){
                redisUtils.rPushAll(BLOG_ID_LIST_KEY, allBlogIds);
            }
        } catch (Exception e) {
            log.error("Redis 运行出错，{}",e.getMessage());
        }
    }

    /**
     * 将所有博客对象存储到 Redis 中。
     */
    public void setBlogList() {
        try {
            List<Object> list = redisUtils.lRange(BLOG_ID_LIST_KEY, 0, -1);
            List<Integer> blogIds = list.stream().map(obj -> (Integer) obj).toList();
            blogIds.forEach(id -> {
                if (!redisUtils.hasKey(BLOG_KEY_PREFIX+id)) {
                    redisUtils.set(BLOG_KEY_PREFIX+id,blogMapper.selectByPrimaryKey(Long.valueOf(id)));
                }
            });
        } catch (Exception e) {
            log.error("Redis 运行出错，{}",e.getMessage());
        }
    }
    
    // ===================== comment ===================
    /**
     * 将所有评论的 ID 存储到 Redis 中。
     */
    public void setCommentIds() {
        List<Integer> allCommentIds = blogCommentMapper.selectAllCommentIds();
        try{
            // 不存在则添加
            if (!redisUtils.hasKey(COMMENT_ID_LIST_KEY)){
                redisUtils.rPushAll(COMMENT_ID_LIST_KEY, allCommentIds);
            }
        }catch (Exception e) {
            log.error("Redis 运行出错，{}",e.getMessage());
        }
    }

    /**
     * 将所有评论对象存储到 Redis 中。
     */
    public void setCommentList() {
        try {
            List<Object> list = redisUtils.lRange(COMMENT_ID_LIST_KEY, 0, -1);
            List<Integer> commentIds = list.stream().map(obj -> (Integer) obj).toList();
            commentIds.forEach(id -> {
                if (!redisUtils.hasKey(COMMENT_KEY_PREFIX + id))
                    redisUtils.set(COMMENT_KEY_PREFIX + id, blogCommentMapper.selectByPrimaryKey(Long.valueOf(id)));
            });
        } catch (Exception e) {
            log.error("Redis 运行出错，{}",e.getMessage());
        }
    }
}
