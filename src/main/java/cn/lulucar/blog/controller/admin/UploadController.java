package cn.lulucar.blog.controller.admin;

import cn.lulucar.blog.config.Constants;
import cn.lulucar.blog.service.FileUploadService;
import cn.lulucar.blog.util.FileNameGenerator;
import cn.lulucar.blog.util.Result;
import cn.lulucar.blog.util.ResultGenerator;
import cn.lulucar.blog.util.UrlUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

/**
 * @author wenxiaolan
 * @ClassName UploadController
 * @date 2024/5/3 11:27
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class UploadController {
    private final FileUploadService fileUploadService;

    public UploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping({"/upload/file"})
    @ResponseBody
    public DeferredResult<Result> upload(HttpServletRequest request,
                                         @RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        log.info("文件原名称：{}", fileName);
        if (StringUtils.isBlank(fileName)) {
            return new DeferredResult<>(2000L,ResultGenerator.genFailResult("文件名称为空"));
        }

        CompletableFuture<Result> futureResult = fileUploadService.uploadFileAsync(file, fileName, request);

        final DeferredResult<Result> deferredResult = new DeferredResult<>(2000L);
        futureResult.thenAccept(result -> {
            if (result.getResultCode() == 200) {
                deferredResult.setResult(result);
            } else {
                deferredResult.setErrorResult(result);
            }
        });

        return deferredResult;
    }
}
