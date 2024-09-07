package cn.lulucar.blog.config;

import cn.lulucar.blog.util.MinioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author wenxiaolan
 * @ClassName InitConfig
 * @date 2024/9/7 21:00
 * @description 项目启动初始化配置
 * 
 */
@Slf4j
@Component
public class InitConfig implements InitializingBean {
    private final MinioUtils minioUtils;
    private final MinioConfig minioConfig;
    public InitConfig(MinioUtils minioUtils, MinioConfig minioConfig) {
        this.minioUtils = minioUtils;
        this.minioConfig = minioConfig;
    }

    /**
     * @throws Exception 
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 项目启动创建 Bucket，不存在则进行创建
        minioUtils.createBucket(minioConfig.getBucketName());
    }
}
