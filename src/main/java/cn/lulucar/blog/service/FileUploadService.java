package cn.lulucar.blog.service;

import cn.lulucar.blog.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

/**
 * @author wenxiaolan
 * @ClassName FileUploadService
 * @date 2024/10/25 22:31
 * @description
 */
public interface FileUploadService {
    /**
     * 异步上传文件
     *
     * @return
     */
    CompletableFuture<Result> uploadFileAsync(MultipartFile file, String newFileName, HttpServletRequest request);

    /**
     * 生成新的文件名
     * @param fileName 原始文件名
     * @return 新的文件名
     */
    String generateNewFileName(String fileName);
}
