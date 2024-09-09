package cn.lulucar.blog.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class Constants {
    // public final static String FILE_UPLOAD_DIC = "/root/upload/";//上传文件的默认url前缀，根据部署设置自行修改
    
    // windows 本地开发环境-------文件上传路径>>>> E:/upload/
    // 线上生产环境---------文件上传路径>>>> /root/upload/
    
    public static String path; //上传文件的默认url前缀，根据部署设置自行修改"
    @Value("${file.upload.path}")
    public void setFileUploadPath(String path) {
        Constants.path = path;
    }
    public static String getFileUploadPath() {
        return path;
    }
    
}
