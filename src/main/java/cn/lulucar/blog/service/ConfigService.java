package cn.lulucar.blog.service;

import java.util.Map;

public interface ConfigService {
    /**
     * 修改配置项
     *
     * @param configName 配置名称
     * @param configValue 配置值
     * @return
     */
    int updateConfig(String configName, String configValue);

    /**
     * 获取所有的配置项
     *
     * @return 返回所有配置信息
     */
    Map<String,String> getAllConfigs();
}
