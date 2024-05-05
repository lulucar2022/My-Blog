package cn.lulucar.blog.controller.admin;

import cn.lulucar.blog.service.ConfigService;
import cn.lulucar.blog.util.Result;
import cn.lulucar.blog.util.ResultGenerator;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author wenxiaolan
 * @ClassName ConfigurationController
 * @date 2024/5/5 16:43
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class ConfigurationController {
    @Resource
    private ConfigService configService;

    /**
     * 配置管理页面访问
     * @param request http请求
     * @return 跳转到配置管理页面
     */
    @GetMapping("/configurations")
    public String configurationPage(HttpServletRequest request) {
        request.setAttribute("path","configurations");
        request.setAttribute("configurations",configService.getAllConfigs());
        return "admin/configuration";
    }

    /**
     * 更新网站信息
     * @param websiteName 网站名称
     * @param websiteDescription 网站描述
     * @param websiteLogo 网站logo
     * @param websiteIcon 网站标签页图标
     * @return 返回操作结果
     */
    @PostMapping("/configurations/website")
    @ResponseBody
    public Result website(@RequestParam(name = "websiteName", required = false) String websiteName,
                          @RequestParam(name = "websiteDescription", required = false) String websiteDescription,
                          @RequestParam(name = "websiteLogo", required = false) String websiteLogo,
                          @RequestParam(name = "websiteIcon", required = false) String websiteIcon) {
        // 先假设没更新的情况下 updateResult = 0
        // 每个字段只要更新成功就 +1
        // 最后判断更新是否成功
        int updateResult = 0;
        if (StringUtils.hasText(websiteName)) {
            updateResult += configService.updateConfig("websiteName", websiteName);
        }
        if (StringUtils.hasText(websiteDescription)) {
            updateResult += configService.updateConfig("websiteDescription", websiteDescription);
        }
        if (StringUtils.hasText(websiteLogo)) {
            updateResult += configService.updateConfig("websiteLogo", websiteLogo);
        }
        if (StringUtils.hasText(websiteIcon)) {
            updateResult += configService.updateConfig("websiteIcon", websiteIcon);
        }
        return ResultGenerator.genSuccessResult(updateResult > 0);
    }


    /**
     * 更新网站作者信息
     * @param yourAvatar 作者头像
     * @param yourName 作者名称
     * @param yourEmail 作者邮箱
     * @return 返回操作结果
     */
    @PostMapping("/configurations/userInfo")
    @ResponseBody
    public Result userInfo(@RequestParam(value = "yourAvatar", required = false) String yourAvatar,
                           @RequestParam(value = "yourName", required = false) String yourName,
                           @RequestParam(value = "yourEmail", required = false) String yourEmail) {
        int updateResult = 0;
        if (StringUtils.hasText(yourAvatar)) {
            updateResult += configService.updateConfig("yourAvatar", yourAvatar);
        }
        if (StringUtils.hasText(yourName)) {
            updateResult += configService.updateConfig("yourName", yourName);
        }
        if (StringUtils.hasText(yourEmail)) {
            updateResult += configService.updateConfig("yourEmail", yourEmail);
        }
        return ResultGenerator.genSuccessResult(updateResult > 0);
    }

    /**
     * 更新网站底部信息
     * @param footerAbout 底部信息
     * @param footerICP 网站备案信息
     * @param footerCopyRight 网站版权
     * @param footerPoweredBy 网站的作者
     * @param footerPoweredByURL 作者GitHub项目地址
     * @return 返回操作结果
     */
    @PostMapping("/configurations/footer")
    @ResponseBody
    public Result footer(@RequestParam(value = "footerAbout", required = false) String footerAbout,
                         @RequestParam(value = "footerICP", required = false) String footerICP,
                         @RequestParam(value = "footerCopyRight", required = false) String footerCopyRight,
                         @RequestParam(value = "footerPoweredBy", required = false) String footerPoweredBy,
                         @RequestParam(value = "footerPoweredByURL", required = false) String footerPoweredByURL) {
        int updateResult = 0;
        if (StringUtils.hasText(footerAbout)) {
            updateResult += configService.updateConfig("footerAbout", footerAbout);
        }
        if (StringUtils.hasText(footerICP)) {
            updateResult += configService.updateConfig("footerICP", footerICP);
        }
        if (StringUtils.hasText(footerCopyRight)) {
            updateResult += configService.updateConfig("footerCopyRight", footerCopyRight);
        }
        if (StringUtils.hasText(footerPoweredBy)) {
            updateResult += configService.updateConfig("footerPoweredBy", footerPoweredBy);
        }
        if (StringUtils.hasText(footerPoweredByURL)) {
            updateResult += configService.updateConfig("footerPoweredByURL", footerPoweredByURL);
        }
        return ResultGenerator.genSuccessResult(updateResult > 0);
    }
}
