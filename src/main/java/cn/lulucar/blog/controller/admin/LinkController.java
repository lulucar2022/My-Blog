package cn.lulucar.blog.controller.admin;

import cn.lulucar.blog.entity.BlogLink;
import cn.lulucar.blog.service.LinkService;
import cn.lulucar.blog.util.PageQueryUtil;
import cn.lulucar.blog.util.Result;
import cn.lulucar.blog.util.ResultGenerator;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author wenxiaolan
 * @ClassName LinkController
 * @date 2024/5/5 15:25
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class LinkController {
    @Resource
    private LinkService linkService;

    /**
     * 友联管理页面访问
     * @param request http请求
     * @return 跳转到友联管理页面
     */
    @GetMapping("/links")
    public String linkPage(HttpServletRequest request) {
        request.setAttribute("path","links");
        return "admin/link";
    }

    /**
     * 查询友联列表
     * @param params 分页参数
     * @return 返回友联列表
     */
    @GetMapping("/links/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        // 参数判断
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))) {
            log.error("分页参数异常，page={}，limit={}",params.get("page"),params.get("limit"));
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(linkService.getBlogLinkPage(pageUtil));
    }

    /**
     * 根据友联Id查询友联详情
     * @param id 友联id
     * @return 返回操作结果
     */
    @GetMapping("/links/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Integer id) {
        BlogLink blogLink = linkService.selectById(id);
        return ResultGenerator.genSuccessResult(blogLink);
    }

    /**
     * 新增友联
     * @param linkType 友联类型（网站 or 个人链接）
     * @param linkName 友联名称
     * @param linkUrl 友联地址
     * @param linkDescription 友联描述
     * @param linkRank 友联排序值
     * @return 返回操作结果
     */
    @PostMapping("/links/save")
    @ResponseBody
    public Result save(@RequestParam("linkType") Integer linkType,
                       @RequestParam("linkName") String linkName,
                       @RequestParam("linkUrl") String linkUrl,
                       @RequestParam("linkDescription") String linkDescription,
                       @RequestParam("linkRank") Integer linkRank) {
        // 参数判断
        if (paramsJudge(linkType, linkName, linkUrl, linkDescription, linkRank))
            return ResultGenerator.genFailResult("参数异常！");
        // 添加友联
        BlogLink blogLink = new BlogLink();
        blogLink.setLinkType(linkType.byteValue());
        blogLink.setLinkRank(linkRank);
        blogLink.setLinkName(linkName);
        blogLink.setLinkUrl(linkUrl);
        blogLink.setLinkDescription(linkDescription);
        return ResultGenerator.genSuccessResult(linkService.saveLink(blogLink));
    }


    /**
     * 修改友联
     * @param linkType 友联类型（网站 or 个人链接）
     * @param linkName 友联名称
     * @param linkUrl 友联地址
     * @param linkDescription 友联描述
     * @param linkRank 友联排序值
     * @return 返回操作结果
     */
    @PostMapping("/links/update")
    @ResponseBody
    public Result update(@RequestParam("linkId") Integer linkId,
                         @RequestParam("linkType") Integer linkType,
                         @RequestParam("linkName") String linkName,
                         @RequestParam("linkUrl") String linkUrl,
                         @RequestParam("linkDescription") String linkDescription,
                         @RequestParam("linkRank") Integer linkRank) {
        BlogLink blogLink = linkService.selectById(linkId);
        if (blogLink == null) {
            log.error("该友联无数据：linkId={}",linkId);
            return ResultGenerator.genFailResult("该友联无数据！");
        }
        // 参数判断
        if (paramsJudge(linkType, linkName, linkUrl, linkDescription, linkRank))
            return ResultGenerator.genFailResult("参数异常！");
        // 修改友联
        blogLink.setLinkType(linkType.byteValue());
        blogLink.setLinkRank(linkRank);
        blogLink.setLinkName(linkName);
        blogLink.setLinkUrl(linkUrl);
        blogLink.setLinkDescription(linkDescription);
        return ResultGenerator.genSuccessResult(linkService.updateLink(blogLink));
    }

    /**
     * 批量删除友联
     * @param ids 友联ids
     * @return 返回操作结果
     */
    @PostMapping("/links/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        // 参数判断 
        if (ids.length < 1) {
            log.error("友联Id数量少于1");
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (linkService.deleteBatch(ids)) {
            log.info("友联删除成功");
            return ResultGenerator.genSuccessResult();
        } else {
            log.error("友联删除失败");
            return ResultGenerator.genFailResult("友联删除失败！");
        }
    }

    /**
     * 判断 link的字段参数是否异常
     * @param linkType 友联类型
     * @param linkName 友联名称
     * @param linkUrl 友联地址
     * @param linkDescription 友联描述
     * @param linkRank 友联排序
     * @return 判断无误返回true
     */
    private boolean paramsJudge(@RequestParam("linkType") Integer linkType, @RequestParam("linkName") String linkName, @RequestParam("linkUrl") String linkUrl, @RequestParam("linkDescription") String linkDescription, @RequestParam("linkRank") Integer linkRank) {
        if (    linkType == null
                || linkType <0
                || linkRank == null
                || linkRank < 0
                || !StringUtils.hasText(linkName)
                || !StringUtils.hasText(linkDescription)
                || !StringUtils.hasText(linkUrl)) {
            log.error("参数异常，linkType={}，linkRank={}，linkName={}，linkUrl={}，linkDescription={}",linkType,linkRank,linkName,linkUrl,linkDescription);
            return true;
        }
        return false;
    }
}
