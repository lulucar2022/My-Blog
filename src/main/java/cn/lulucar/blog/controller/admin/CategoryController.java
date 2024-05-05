package cn.lulucar.blog.controller.admin;

import cn.lulucar.blog.service.CategoryService;
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
 * @ClassName CategoryController
 * @date 2024/5/4 16:01
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Resource
    CategoryService categoryService;

    /**
     * 分类页面访问
     * @param request http请求
     * @return 跳转到分类页面
     */
    @GetMapping("/categories")
    public String categoryPage(HttpServletRequest request) {
        request.setAttribute("path","categories");
        return "admin/category";
    }
    
    @GetMapping("/categories/list")
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> params) {
        // 检查分页参数是否存在
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))) {
            log.error("分页参数异常，page={}，limit={}",params.get("page"),params.get("limit"));
            return ResultGenerator.genFailResult("参数异常");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(categoryService.getBlogCategoryPage(pageUtil));
    }
    
    @PostMapping("/categories/save")
    @ResponseBody
    public Result save(@RequestParam("categoryName") String categoryName,
                       @RequestParam("categoryIcon") String categoryIcon) {
        // 判断参数是否异常（空、空值、参杂空格）
        if (!StringUtils.hasText(categoryName)) {
            log.error("分类名称异常");
            return ResultGenerator.genFailResult("请输入分类名称！");
        }
        if (!StringUtils.hasText(categoryIcon)) {
            log.error("分类图标异常");
            return ResultGenerator.genFailResult("请选择分类图标！");
        }
        if (categoryService.saveCategory(categoryName, categoryIcon)) {
            log.info("分类添加成功");
            return ResultGenerator.genSuccessResult();
        } else {
            log.error("分类添加失败");
            return ResultGenerator.genFailResult("分类添加失败");
        }
    }
    @PostMapping("/categories/update")
    @ResponseBody
    public Result update(@RequestParam("categoryId") Integer categoryId,
                         @RequestParam("categoryName") String categoryName,
                         @RequestParam("categoryIcon") String categoryIcon) {
        // 判断参数是否异常（空、空值、参杂空格）
        if (!StringUtils.hasText(categoryName)) {
            log.error("分类名称异常");
            return ResultGenerator.genFailResult("请输入分类名称！");
        }
        if (!StringUtils.hasText(categoryIcon)) {
            log.error("分类图标异常");
            return ResultGenerator.genFailResult("请选择分类图标！");
        }
        if (categoryService.updateCategory(categoryId,categoryName,categoryIcon)) {
            log.info("分类修改成功");
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("分类修改失败");
        }
    }
    
    @PostMapping("/categories/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        // 判断参数是否异常
        if (ids.length < 1) {
            log.error("分类id的数量少于1个");
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (categoryService.deleteBatch(ids)) {
            log.info("分类删除成功");
            return ResultGenerator.genSuccessResult();
        } else {
            log.error("分类删除失败");
            return ResultGenerator.genFailResult("分类删除失败");
        }
    }
}
