package cn.lulucar.blog.service.impl;

import cn.lulucar.blog.entity.BlogConfig;
import cn.lulucar.blog.mapper.BlogConfigMapper;
import cn.lulucar.blog.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wenxiaolan
 * @ClassName ConfigServiceImpl
 * @date 2024/5/5 16:57
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private BlogConfigMapper configMapper;

    public static final String websiteName = "personal blog";
    public static final String websiteDescription = "personal blog是SpringBoot2+Thymeleaf+Mybatis建造的个人博客网站.SpringBoot实战博客源码.个人博客搭建";
    public static final String websiteLogo = "/admin/dist/img/logo.png";
    public static final String websiteIcon = "/admin/dist/img/favicon.png";

    public static final String yourAvatar = "/admin/dist/img/13.png";
    public static final String yourEmail = "2449207463@qq.com";
    public static final String yourName = "十三";

    public static final String footerAbout = "your personal blog. have fun.";
    public static final String footerICP = "浙ICP备 xxxxxx-x号";
    public static final String footerCopyRight = "@2018 十三";
    public static final String footerPoweredBy = "personal blog";
    public static final String footerPoweredByURL = "##";
    
    /**
     * 修改配置项
     *
     * @param configName  配置名称
     * @param configValue 配置值
     * @return 0表示更新失败，其它正整数表示更新成功
     */
    @Override
    public int updateConfig(String configName, String configValue) {
        BlogConfig blogConfig = configMapper.selectByPrimaryKey(configName);
        if (blogConfig != null) {
            blogConfig.setConfigValue(configValue);
            blogConfig.setUpdateTime(LocalDateTime.now());
            return configMapper.updateByPrimaryKeySelective(blogConfig);
        }
        return 0;
    }

    /**
     * 获取所有的配置项
     *
     * @return 返回所有配置信息
     */
    @Override
    public Map<String, String> getAllConfigs() {
        // 把所有配置项装入集合
        List<BlogConfig> blogConfigs = configMapper.selectAll();
        // 转为Map集合
        Map<String, String> configMap = blogConfigs.stream().collect(Collectors.toMap(BlogConfig::getConfigName, BlogConfig::getConfigValue));
        
        return configMap;
    }
}
