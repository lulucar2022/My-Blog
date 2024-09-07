package cn.lulucar.blog.controller.admin;

import cn.lulucar.blog.config.MinioConfig;
import cn.lulucar.blog.util.FileNameGenerator;
import cn.lulucar.blog.util.MinioUtils;
import cn.lulucar.blog.util.Result;
import cn.lulucar.blog.util.ResultGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author wenxiaolan
 * @ClassName MinioController
 * @date 2024/9/7 22:42
 * @description Minio 测试
 */
@Slf4j
@RestController
@RequestMapping("/minio")
public class MinioController {
    private final MinioUtils minioUtils;
    private final MinioConfig minioConfig;

    public MinioController(MinioUtils minioUtils, MinioConfig minioConfig) {
        this.minioUtils = minioUtils;
        this.minioConfig = minioConfig;
    }

    /**
     * 上传文件
     * @param file 文件
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            log.info("文件原名称：{}",fileName);
            assert fileName != null;
            String newFileName = FileNameGenerator.generateNewFileName(fileName);
            log.info("文件新名称：{}",newFileName);
            String contentType = file.getContentType();
            log.info("文件类型：{}",contentType);
            minioUtils.uploadFile(minioConfig.getBucketName(),file,newFileName,contentType);
            Result result = ResultGenerator.genSuccessResult(newFileName);
            return result;
        } catch (Exception e) {
            log.error("文件上传失败",e);
            return ResultGenerator.genFailResult("文件上传失败");
        }
    }

    /**
     * 删除
     *
     * @param fileName
     */
    @DeleteMapping("/")
    public Result delete(@RequestParam("fileName") String fileName) {
        minioUtils.removeFile(minioConfig.getBucketName(), fileName);
        Result result = ResultGenerator.genSuccessResult("文件删除成功");
        return result;
    }

    /**
     * 获取文件信息
     *
     * @param fileName
     * @return
     */
    @GetMapping("/info")
    public Result getFileStatusInfo(@RequestParam("fileName") String fileName) {
        Result result = ResultGenerator.genSuccessResult(minioUtils.getFileStatusInfo(minioConfig.getBucketName(), fileName));
        return result;
    }

    /**
     * 获取文件外链
     *
     * @param fileName
     * @return
     */
    @GetMapping("/url")
    public Result getPresignedObjectUrl(@RequestParam("fileName") String fileName) {
        Result result = ResultGenerator.genSuccessResult(minioUtils.getPresignedObjectUrl(minioConfig.getBucketName(), fileName));
        return result;
    }

    /**
     * 文件下载
     *
     * @param fileName
     * @param response
     */
    @GetMapping("/download")
    public Result download(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        try {
            InputStream fileInputStream = minioUtils.getObject(minioConfig.getBucketName(), fileName);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
            Result result = ResultGenerator.genSuccessResult("文件下载成功");
            return result;
        } catch (Exception e) {
            log.error("下载失败");
            return ResultGenerator.genFailResult("文件下载失败");
        }
    }
}
