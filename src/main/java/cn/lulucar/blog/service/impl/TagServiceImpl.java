package cn.lulucar.blog.service.impl;

import cn.lulucar.blog.entity.BlogTag;
import cn.lulucar.blog.entity.BlogTagCount;
import cn.lulucar.blog.mapper.BlogTagMapper;
import cn.lulucar.blog.mapper.BlogTagRelationMapper;
import cn.lulucar.blog.service.TagService;
import cn.lulucar.blog.util.PageQueryUtil;
import cn.lulucar.blog.util.PageResult;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wenxiaolan
 * @ClassName TagServiceImpl
 * @date 2024/4/21 20:01
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    BlogTagMapper blogTagMapper;
    @Autowired
    BlogTagRelationMapper blogTagRelationMapper;
    /**
     * 查询标签的分页数据
     *
     * @param pageUtil 分页工具
     * @return 返回分页后的 tag 数据
     */
    @Override
    public PageResult getBlogTagPage(PageQueryUtil pageUtil) {
        List<BlogTag> tagList = blogTagMapper.findTagList(pageUtil);
        int totalCount = blogTagMapper.getTotalTags(pageUtil);
        return new PageResult(tagList, totalCount, pageUtil.getLimit(), pageUtil.getPage());
    }


    @Override
    public int getTotalTags() {
        return blogTagMapper.getTotalTags(null);
    }

    /**
     * 新增 tag
     * @param tagName tag 名称
     * @return 成功返回true
     */
    @Override
    public Boolean saveTag(String tagName) {
        BlogTag blogTag = blogTagMapper.selectByTagName(tagName);
        // 判断 blogTag 是否为空，是空的才能插入
        if (blogTag == null){
            blogTag = new BlogTag();
            blogTag.setTagName(tagName);
            return  (blogTagMapper.insertSelective(blogTag) > 0);
        }
        return false;
    }

    /**
     * 删除blog的tag
     * @param ids tag的id
     * @return 删除成功返回true
     */
    @Override
    public Boolean deleteBatch(Integer[] ids) {
        // 找出有关联blog的 tag，这里的tag具有唯一性
        List<Long> relationIds = blogTagRelationMapper.selectDistinctTagIds(ids);
        if (!CollectionUtils.isEmpty(relationIds)){
            return false;
        }

        return blogTagMapper.deleteBatch(ids) > 0;
    }

    /**
     * 按照顺序返回前20 tag
     * @return
     */
    @Override
    public List<BlogTagCount> getBlogTagCountForIndex() {
        return blogTagMapper.getTagCount();
    }
}
