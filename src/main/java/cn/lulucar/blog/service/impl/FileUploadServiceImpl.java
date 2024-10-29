package cn.lulucar.blog.service.impl;

import cn.lulucar.blog.config.Constants;
import cn.lulucar.blog.service.FileUploadService;
import cn.lulucar.blog.util.FileNameGenerator;
import cn.lulucar.blog.util.Result;
import cn.lulucar.blog.util.ResultGenerator;
import cn.lulucar.blog.util.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

/**
 * @author wenxiaolan
 * @ClassName FileUploadServiceImpl
 * @date 2024/10/25 22:25
 * @description
 */
@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Async("asyncTaskExecutor")
    public CompletableFuture<Result> uploadFileAsync(MultipartFile file, String newFileName, HttpServletRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            File fileDirectory = new File(Constants.getFileUploadPath());
            log.info("文件目录：{}", fileDirectory.getAbsolutePath());

            // 创建文件
            File destFile = new File(Constants.getFileUploadPath() + newFileName);

            if (!fileDirectory.exists() && !fileDirectory.mkdirs()) {
                log.error("文件夹创建失败，路径为：{}", fileDirectory);
                throw new RuntimeException("文件夹创建失败,路径为：" + fileDirectory);
            }

            // 传输文件
            try {
                file.transferTo(destFile);
            } catch (IOException e) {
                log.error("文件上传失败", e);
                throw new RuntimeException("文件上传失败", e);
            }

            Result resultSuccess = ResultGenerator.genSuccessResult();
            try {
                resultSuccess.setData(UrlUtil.getHost(new URI(request.getRequestURL() + "")) + "/upload/" + newFileName);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            log.info("文件上传成功");
            log.info("文件结果：{}", resultSuccess.getData());
            return resultSuccess;
        });
    }

    public String generateNewFileName(String fileName) {
        // 根据需要生成新的文件名
        return FileNameGenerator.generateNewFileName(fileName);
        
    }
}
