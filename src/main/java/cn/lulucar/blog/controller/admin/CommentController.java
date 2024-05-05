package cn.lulucar.blog.controller.admin;

import cn.lulucar.blog.service.CommentService;
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
 * @ClassName CommentController
 * @date 2024/5/5 14:08
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class CommentController {
    @Resource
    private CommentService commentService;
    
    @GetMapping("/comments")
    public String commentPage(HttpServletRequest request) {
        request.setAttribute("path","comments");
        return "admin/comment";
    }
    
    @GetMapping("/comments/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))) {
            log.error("分页参数异常，page={}，limit={}",params.get("page"),params.get("limit"));
            return ResultGenerator.genFailResult("分页参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(commentService.getCommentsPage(pageUtil));
    }
    
    // todo 批量评论审核
    @PostMapping("/comments/checkDone")
    @ResponseBody
    public Result checkDone(@RequestBody Integer[] ids) {
        // 参数判断
        if (ids.length < 1) {
            log.error("评论Id数量少于1");
            return ResultGenerator.genFailResult("参数异常！");
        }
        // 评论审核
        if (commentService.checkDone(ids)) {
            log.info("评论审核通过");
            return ResultGenerator.genSuccessResult();
        } else {
            log.error("评论审核失败");
            return ResultGenerator.genFailResult("评论审核失败");
        }
    }
    
    // todo 回复评论
    @PostMapping("/comments/reply")
    @ResponseBody
    public Result reply(@RequestParam("commentId") Long commentId,
                        @RequestParam("replyBody") String replyBody) {
        // 参数判断
        if (!StringUtils.hasText(replyBody) || commentId == null || commentId<1) {
            log.error("参数异常，replyBody={}，commentId={}",replyBody,commentId);
            return ResultGenerator.genFailResult("参数异常！");
        }
        // 回复评论
        if (commentService.reply(commentId,replyBody)) {
            log.info("评论回复成功");
            return ResultGenerator.genSuccessResult();
        } else {
            log.error("评论回复失败");
            return ResultGenerator.genFailResult("评论回复失败");
        }
    }
    
    // todo 批量删除评论
    @PostMapping("/comments/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        // 参数判断
        if (ids.length < 1) {
            log.error("评论Id数量少于1");
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (commentService.deleteBatch(ids)) {
            log.info("评论删除成功");
            return ResultGenerator.genSuccessResult("评论删除成功");
        } else {
            log.error("评论删除失败");
            return ResultGenerator.genFailResult("评论删除失败");
        }
    }
}
