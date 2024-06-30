package cn.lulucar.blog.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    public final static String FILE_UPLOAD_DIC = "/root/upload/";//上传文件的默认url前缀，根据部署设置自行修改
  
}
