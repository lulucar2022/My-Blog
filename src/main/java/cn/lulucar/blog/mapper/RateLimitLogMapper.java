package cn.lulucar.blog.mapper;

import cn.lulucar.blog.entity.RateLimitLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wxl
 * @date 2025/3/26 15:55
 * @description
 */
@Mapper
public interface RateLimitLogMapper {
    void insert(RateLimitLog log);
}
