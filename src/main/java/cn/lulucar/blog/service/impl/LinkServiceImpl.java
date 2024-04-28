package cn.lulucar.blog.service.impl;

import cn.lulucar.blog.entity.BlogLink;
import cn.lulucar.blog.mapper.BlogLinkMapper;
import cn.lulucar.blog.service.LinkService;
import cn.lulucar.blog.util.PageQueryUtil;
import cn.lulucar.blog.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wenxiaolan
 * @ClassName LinkServiceImpl
 * @date 2024/4/22 18:37
 */
@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    BlogLinkMapper blogLinkMapper;

    /**
     * 查询友链的分页数据
     *
     * @param pageUtil
     * @return
     */
    @Override
    public PageResult getBlogLinkPage(PageQueryUtil pageUtil) {
        List<BlogLink> linkList = blogLinkMapper.findLinkList(pageUtil);
        int totalCount = blogLinkMapper.getTotalLinks(pageUtil);
        return new PageResult(linkList, totalCount, pageUtil.getLimit(), pageUtil.getPage());
    }

    @Override
    public int getTotalLinks() {
        return blogLinkMapper.getTotalLinks(null);
    }

    @Override
    public Boolean saveLink(BlogLink link) {

        return blogLinkMapper.insertSelective(link) > 0;
    }

    @Override
    public BlogLink selectById(Integer id) {
        return blogLinkMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean updateLink(BlogLink tempLink) {
        return blogLinkMapper.updateByPrimaryKeySelective(tempLink) > 0;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return blogLinkMapper.deleteBatch(ids) > 0;
    }

    /**
     * 返回友链页面所需的所有数据
     *
     * @return
     */
    @Override
    public Map<Byte, List<BlogLink>> getLinksForLinkPage() {
        // 获取所有 友链数据列表
        List<BlogLink> blogLinkList = blogLinkMapper.findLinkList(null);
        // 存在数据
        if (!(CollectionUtils.isEmpty(blogLinkList))) {
            // 筛选数据，根据 type 进行分组
            return blogLinkList.stream().collect(Collectors.groupingBy(BlogLink::getLinkType));
        }
        return null;
    }
}
