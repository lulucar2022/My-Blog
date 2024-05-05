package cn.lulucar.blog.controller.admin;

import cn.lulucar.blog.service.TagService;
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
 * @ClassName TagController
 * @date 2024/5/5 13:25
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class TagController {
    @Resource
    private TagService tagService;
    
    @GetMapping("/tags")
    public String tagPage(HttpServletRequest request) {
        request.setAttribute("path","tags");
        return "admin/tag";
    }
    @GetMapping("/tags/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))){
            log.error("分页参数异常，page={}，limit={}",params.get("page"),params.get("limit"));
            return ResultGenerator.genFailResult("分页参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(tagService.getBlogTagPage(pageUtil));
    }
    @PostMapping("/tags/save")
    @ResponseBody
    public Result save(@RequestParam("tagName") String tagName) {
        // 参数判断
        if (!StringUtils.hasText(tagName)) {
            log.error("参数异常，标签名：{}",tagName);
            return ResultGenerator.genFailResult("标签名称参数异常！");
        }
        // 添加
        if (tagService.saveTag(tagName)) {
            log.info("标签添加成功");
            return ResultGenerator.genSuccessResult();
        } else {
            log.error("标签添加失败");
            return ResultGenerator.genFailResult("标签添加失败");
        }
    }
    
    @PostMapping("/tags/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        // 参数判断
        if (ids.length < 1) {
            log.error("标签Id数量小于1");
            return ResultGenerator.genFailResult("参数异常！");
        }
        // 删除标签
        if (tagService.deleteBatch(ids)) {
            log.info("标签删除成功");
            return ResultGenerator.genSuccessResult();
        } else {
            log.error("有博客关联了该标签，标签删除失败");
            return ResultGenerator.genFailResult("有博客关联了该标签，标签删除失败");
        }
    }
}
