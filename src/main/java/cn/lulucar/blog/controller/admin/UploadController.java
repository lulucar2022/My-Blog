package cn.lulucar.blog.controller.admin;

import cn.lulucar.blog.config.Constants;
import cn.lulucar.blog.util.FileNameGenerator;
import cn.lulucar.blog.util.Result;
import cn.lulucar.blog.util.ResultGenerator;
import cn.lulucar.blog.util.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author wenxiaolan
 * @ClassName UploadController
 * @date 2024/5/3 11:27
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class UploadController {
    
    @PostMapping({"/upload/file"})
    @ResponseBody
    public Result upload(HttpServletRequest request,
                         @RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        log.debug("文件原名称：{}",fileName);
        assert fileName != null;
        String newFileName = FileNameGenerator.generateNewFileName(fileName);
        log.debug("文件新名称：{}",newFileName);
        File fileDirectory = new File(Constants.getFileUploadPath());
        log.debug("文件目录：{}",fileDirectory);
        //创建文件
        File destFile = new File(Constants.getFileUploadPath() + newFileName);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    log.error("文件夹创建失败，路径为：{}",fileDirectory);
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            // 传输文件
            file.transferTo(destFile);
            log.debug("文件传输成功");
            Result resultSuccess = ResultGenerator.genSuccessResult();
            resultSuccess.setData(UrlUtil.getHost(new URI(request.getRequestURL() + "")) + "/upload/" + newFileName);
            log.info("文件上传成功");
            log.info("文件结果：{}",resultSuccess.getData());
            return resultSuccess;
        } catch (IOException | URISyntaxException e) {
            return ResultGenerator.genFailResult("文件上传失败");
        }
    }
}
