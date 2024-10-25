package cn.lulucar.blog.service.impl;

import cn.lulucar.blog.vo.BlogDetailVO;
import cn.lulucar.blog.vo.BlogListVO;
import cn.lulucar.blog.vo.SimpleBlogListVO;
import cn.lulucar.blog.entity.Blog;
import cn.lulucar.blog.entity.BlogCategory;
import cn.lulucar.blog.entity.BlogTag;
import cn.lulucar.blog.entity.BlogTagRelation;
import cn.lulucar.blog.mapper.*;
import cn.lulucar.blog.service.BlogService;
import cn.lulucar.blog.util.MarkDownUtil;
import cn.lulucar.blog.util.PageQueryUtil;
import cn.lulucar.blog.util.PageResult;
import cn.lulucar.blog.util.PatternUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wenxiaolan
 * @ClassName BlogServiceImpl
 * @date 2024/4/21 16:16
 */
@Slf4j
@Service
public class BlogServiceImpl implements BlogService {
    private final BlogMapper blogMapper;
    private final BlogCategoryMapper categoryMapper;
    private final BlogTagMapper tagMapper;
    private final BlogTagRelationMapper blogTagRelationMapper;
    private final BlogCommentMapper blogCommentMapper;

    public BlogServiceImpl(BlogMapper blogMapper, BlogCategoryMapper categoryMapper, BlogTagMapper tagMapper, BlogTagRelationMapper blogTagRelationMapper, BlogCommentMapper blogCommentMapper) {
        this.blogMapper = blogMapper;
        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;
        this.blogTagRelationMapper = blogTagRelationMapper;
        this.blogCommentMapper = blogCommentMapper;
    }

    /**
     * 保存博客
     * @param blog Blog类型 博客
     * @return 
     */
    @Override
    @Transactional
    public String saveBlog(Blog blog) {
        // 获取博客的分类
        BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        } else {
            // 设置博客分类名称
            blog.setBlogCategoryName(blogCategory.getCategoryName());
            // 分类的排序值加 1
            blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);
        }
        // 处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            log.error("标签数量超出6个：{}",tags.length);
            return "标签数量限制为6";
        }
        // 保存文章
        if (blogMapper.insertSelective(blog) > 0) {
            // 新增的tag对象
            List<BlogTag> tagListForInsert = new ArrayList<>();
            // 所有tag对象
            List<BlogTag> allTagsList = new ArrayList<>();
            // 处理博客的tags
            for (String tag : tags) {
                BlogTag blogTag = tagMapper.selectByTagName(tag);
                if (blogTag == null) {
                    // 新的tag要加入tagListForInsert
                    BlogTag tempTag = new BlogTag();
                    tempTag.setTagName(tag);
                    tagListForInsert.add(tempTag);
                } else {
                    allTagsList.add(blogTag);
                }
            }
            // 新增tag数据并修改分类的排序值
            if (!CollectionUtils.isEmpty(tagListForInsert)) {
                // 批量添加
                tagMapper.batchInsertBlogTag(tagListForInsert);
            }
            if (blogCategory != null) {
                categoryMapper.updateByPrimaryKeySelective(blogCategory);
            }
            // blog 与 tag 关联
            List<BlogTagRelation> blogTagRelations = new ArrayList<>();
            // 新增关系数据
            allTagsList.addAll(tagListForInsert);
            for (BlogTag blogTag : allTagsList) {
                BlogTagRelation blogTagRelation = new BlogTagRelation();
                blogTagRelation.setBlogId(blog.getBlogId());
                blogTagRelation.setTagId(blogTag.getTagId());
                blogTagRelations.add(blogTagRelation);
            }
            // 批量添加
            if (blogTagRelationMapper.batchInsert(blogTagRelations) > 0) {
                log.info("保存成功");
                return "success";
            }
            
        }
        return "保存失败";
    }

    /**
     * blog分页列表
     * @param pageUtil 分页参数
     * @return 返回blog分页列表
     */
    @Override
    public PageResult getBlogsPage(PageQueryUtil pageUtil) {
        List<Blog> blogList = blogMapper.findBlogList(pageUtil);
        int totalCount = blogMapper.getTotalBlogs(pageUtil);
        return new PageResult(blogList,totalCount, pageUtil.getLimit(), pageUtil.getPage());
    }

    /**
     * 批量删除blog
     * @param ids blog的id
     * @return 成功返回true
     */
    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return blogMapper.deleteBatch(ids)>0;
    }

    /**
     * 获取blog的总数
     * @return 返回blog的总数
     */
    @Override
    public int getTotalBlogs() {
        return blogMapper.getTotalBlogs(null);
    }

    /**
     * 根据id获取详情
     *
     * @param blogId 博客Id
     * @return 返回blog
     */
    @Override
    public Blog getBlogById(Long blogId) {
        return blogMapper.selectByPrimaryKey(blogId);
    }

    /**
     * 后台修改
     *
     * @param blog 博客
     * @return
     */
    @Override
    @Transactional
    public String updateBlog(Blog blog) {
        Blog blogForUpdate = blogMapper.selectByPrimaryKey(blog.getBlogId());
        if (blogForUpdate == null) {
            log.error("blog数据不存在");
            return "数据不存在";
        }
        blogForUpdate.setBlogTitle(blog.getBlogTitle());
        blogForUpdate.setBlogSubUrl(blog.getBlogSubUrl());
        blogForUpdate.setBlogStatus(blog.getBlogStatus());
        blogForUpdate.setBlogContent(blog.getBlogContent());
        blogForUpdate.setBlogCoverImage(blog.getBlogCoverImage());
        blogForUpdate.setEnableComment(blog.getEnableComment());
        // 设置博客分类
        BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blogForUpdate.setBlogCategoryId(0);
            blogForUpdate.setBlogCategoryName("默认分类");
        } else {
            //设置博客分类名称
            blogForUpdate.setBlogCategoryName(blogCategory.getCategoryName());
            blogForUpdate.setBlogCategoryId(blogCategory.getCategoryId());
            //分类的排序值加1
            blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);
        }
        // 处理tag数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            log.error("标签数量超出6：{}",tags.length);
            return "标签数量限制为6";
        }
        blogForUpdate.setBlogTags(blog.getBlogTags());
        // 新增tag对象列表，有就存入数据库，没有就过
        List<BlogTag> tagListForInsert = new ArrayList<>();
        // 所有tag对象列表，更新blog与tag的关联表
        List<BlogTag> allTagsList = new ArrayList<>();
        
        for (String tag : tags) {
            BlogTag blogTag = tagMapper.selectByTagName(tag);
            // blogTag 不存在就添加到 tagListForInsert
            if (blogTag == null) {
                BlogTag tempTag = new BlogTag();
                tempTag.setTagName(tag);
                tagListForInsert.add(tempTag);
            } else {
                allTagsList.add(blogTag);
            }
        }
        // 新增tag对象列表不为空，就存入数据库
        if (!CollectionUtils.isEmpty(tagListForInsert)){
            // 批量插入
            tagMapper.batchInsertBlogTag(tagListForInsert);
        }
        // 关联blog与tag
        List<BlogTagRelation> blogTagRelations = new ArrayList<>();
        // 关联新增tag对象列表与blog
        allTagsList.addAll(tagListForInsert);
        for (BlogTag blogTag : allTagsList) {
            BlogTagRelation relation = new BlogTagRelation();
            relation.setBlogId(blog.getBlogId());
            relation.setTagId(blogTag.getTagId());
            blogTagRelations.add(relation);
        }
        // 修改blog信息，修改分类排序值，删除原关系数据，保存新的关系数据
        if (blogCategory != null) {
            categoryMapper.updateByPrimaryKeySelective(blogCategory);
        }
        blogTagRelationMapper.deleteByBlogId(blog.getBlogId());
        blogTagRelationMapper.batchInsert(blogTagRelations);
        if (blogMapper.updateByPrimaryKeySelective(blogForUpdate) > 0) {
            log.info("修改成功");
            return "success";
        }
        return "修改失败";
    }

    /**
     * 获取首页文章列表
     *
     * @param page
     * @return
     */
    @Override
    public PageResult getBlogsForIndexPage(int page) {
        // 分页参数
        Map<String, Object> params = new HashMap<>();
        params.put("page",page);
        params.put("limit",8);
        params.put("blogStatus",1); // 过滤还没发布成功的数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        List<Blog> blogList = blogMapper.findBlogList(pageUtil);
        List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
        int totalCount = blogMapper.getTotalBlogs(pageUtil);
        return new PageResult(blogListVOS, totalCount, pageUtil.getLimit(), pageUtil.getPage());
    }

    /**
     * 首页侧边栏数据列表
     * 0-点击最多 1-最新发布
     * SimpleBlogListVO 只包含blog的id和title
     * @param type 类型
     * @return 返回博客数据列表
     */
    @Override
    public List<SimpleBlogListVO> getBlogListForIndexPage(int type) {
        List<SimpleBlogListVO> simpleBlogListVOS = new ArrayList<>();
        List<Blog> blogList = blogMapper.findBlogListByType(type, 9);
        if (!CollectionUtils.isEmpty(blogList)) {
            for (Blog blog : blogList) {
                SimpleBlogListVO simpleBlogListVO = new SimpleBlogListVO();
                // 属性复制
                BeanUtils.copyProperties(blog, simpleBlogListVO);
                simpleBlogListVOS.add(simpleBlogListVO);
            }
        }
        return simpleBlogListVOS;
    }

    /**
     * 查询文章详情
     *
     * @param blogId 博客id
     * @return 返回 BlogDetailVO
     */
    @Override
    public BlogDetailVO getBlogDetail(Long blogId) {
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        // blog不为空且状态为已发布
        // 逻辑处理用 getBlogDetailVO 封装
        return getBlogDetailVO(blog);
    }

    /**
     * 根据标签获取文章列表
     *
     * @param tagName 标签名称
     * @param page 页码
     * @return 返回博客列表
     */
    @Override
    public PageResult getBlogsPageByTag(String tagName, int page) {
        // 先验证标签名称的合规性
        if (PatternUtil.validKeyword(tagName)) {
            BlogTag blogTag = tagMapper.selectByTagName(tagName);
            if (blogTag != null && page > 0) {
                Map<String, Object> params = new HashMap<>();
                params.put("page",page);
                params.put("limit",9);
                params.put("tagId",blogTag.getTagId());
                PageQueryUtil pageUtil = new PageQueryUtil(params);
                List<Blog> blogList = blogMapper.getBlogsPageByTagId(pageUtil);
                List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
                int totalCount = blogMapper.getTotalBlogsByTagId(pageUtil);
                return new PageResult(blogListVOS, totalCount, pageUtil.getLimit(), pageUtil.getPage());
            }
        }
        return null;
    }

    /**
     * 根据分类获取文章列表
     *
     * @param categoryName 分类名称
     * @param page 页码
     * @return 返回博客列表
     */
    @Override
    public PageResult getBlogsPageByCategory(String categoryName, int page) {
        // 验证博客分类名称的合规性
        if (PatternUtil.validKeyword(categoryName)){
            BlogCategory blogCategory = categoryMapper.selectByCategoryName(categoryName);
            if ("默认分类".equals(categoryName) && blogCategory == null) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
            }
            if (blogCategory != null && page > 0) {
                Map<String, Object> params = new HashMap<>();
                params.put("page", page);
                params.put("limit", 9);
                params.put("blogCategoryId", blogCategory.getCategoryId());
                params.put("blogStatus", 1); // 过滤发布状态下的数据
                PageQueryUtil pageUtil = new PageQueryUtil(params);
                List<Blog> blogList = blogMapper.findBlogList(pageUtil);
                List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
                int totalCount = blogMapper.getTotalBlogs(pageUtil);
                return new PageResult(blogListVOS,totalCount, pageUtil.getLimit(), pageUtil.getPage());
            }
        }
        return null;
    }

    /**
     * 根据搜索获取文章列表
     *
     * @param keyword 搜索关键字
     * @param page 页码
     * @return 返回博客列表
     */
    @Override
    public PageResult getBlogsPageBySearch(String keyword, int page) {
        if (PatternUtil.validKeyword(keyword) && page > 0) {
            Map<String, Object> params = new HashMap<>();
            params.put("page", page);
            params.put("limit", 9);
            params.put("keyword", keyword);
            params.put("blogStatus", 1); // 过滤发布状态下的数据
            PageQueryUtil pageUtil = new PageQueryUtil(params);
            List<Blog> blogList = blogMapper.findBlogList(pageUtil);
            List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
            int totalCount = blogMapper.getTotalBlogs(pageUtil);
            return new PageResult(blogListVOS, totalCount, pageUtil.getLimit(), pageUtil.getPage());
        }
        return null;
    }

    /**
     * 根据子Url获取文章详情
     * @param subUrl 博客的子Url
     * @return 返回文章详情
     */
    @Override
    public BlogDetailVO getBlogDetailBySubUrl(String subUrl) {
        Blog blog = blogMapper.selectBySubUrl(subUrl);
        // blog不为空且状态为已发布
        return getBlogDetailVO(blog);
    }

    /**
     * 方法抽取 
     * 详细版博客文章
     * 
     * @param blog
     * @return
     */
    private BlogDetailVO getBlogDetailVO(Blog blog) {
        if (blog != null && blog.getBlogStatus() == 1) {
            //增加浏览量
            blog.setBlogViews(blog.getBlogViews() + 1);
            blogMapper.updateByPrimaryKey(blog);
            BlogDetailVO blogDetailVO = new BlogDetailVO();
            BeanUtils.copyProperties(blog, blogDetailVO);
            blogDetailVO.setBlogContent(MarkDownUtil.mdToHtml(blogDetailVO.getBlogContent()));
            BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
            if (blogCategory == null) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
                blogCategory.setCategoryName("默认分类");
                blogCategory.setCategoryIcon("/admin/dist/img/category/00.png");
            }
            //分类信息
            blogDetailVO.setBlogCategoryIcon(blogCategory.getCategoryIcon());
            if (StringUtils.hasText(blog.getBlogTags())) {
                //标签设置
                List<String> tags = Arrays.asList(blog.getBlogTags().split(","));
                blogDetailVO.setBlogTags(tags);
            }
            //设置评论数
            Map<String, Object> params = new HashMap<>();
            params.put("blogId", blog.getBlogId());
            params.put("commentStatus", 1);//过滤审核通过的数据
            blogDetailVO.setCommentCount(blogCommentMapper.getTotalBlogComments(params));
            return blogDetailVO;
        }
        return null;
    }

    /**
     * 用于首页浏览的博客文章
     * @param blogList
     * @return
     */
    private List<BlogListVO> getBlogListVOsByBlogs(List<Blog> blogList) {
        List<BlogListVO> blogListVOS = new ArrayList<>();
        // 判断集合是否为空
        if (!CollectionUtils.isEmpty(blogList)) {
            // 获取所有博客文章的分类id
            List<Integer> categoryIds = blogList.stream().map(Blog::getBlogCategoryId).collect(Collectors.toList());
            // 博客分类Map集合
            Map<Integer, String> blogCategoryMap = new HashMap<>();
            // 博客文章的分类id存在
            if (!CollectionUtils.isEmpty(categoryIds)) {
                // 获取博客分类的数据
                List<BlogCategory> blogCategories = categoryMapper.selectByCategoryIds(categoryIds);
                // 博客分类数据存在
                if (!CollectionUtils.isEmpty(blogCategories)) {
                    blogCategoryMap = blogCategories.stream().collect(Collectors.toMap(BlogCategory::getCategoryId, BlogCategory::getCategoryIcon, (key1, key2) -> key2));
                }
            }
            for (Blog blog : blogList) {
                BlogListVO blogListVO = new BlogListVO();
                // 为博客文章截取摘要
                String summaryContent = MarkDownUtil.mdToHtmlForSummary(blog.getBlogContent());
                blog.setBlogContent(summaryContent);
                // 复制属性
                BeanUtils.copyProperties(blog, blogListVO);
                // 根据博客分类id 进行 分类， 如果博客文章没有匹配到就归为默认分类
                if (blogCategoryMap.containsKey(blog.getBlogCategoryId())) {
                    blogListVO.setBlogCategoryIcon(blogCategoryMap.get(blog.getBlogCategoryId()));
                } else {
                    blogListVO.setBlogCategoryId(0);
                    blogListVO.setBlogCategoryName("默认分类");
                    blogListVO.setBlogCategoryIcon("/admin/dist/img/category/00.png");
                }
                blogListVOS.add(blogListVO);
            }
        }
        return blogListVOS;
    }
}
