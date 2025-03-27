package cn.lulucar.blog.mapper;

import cn.lulucar.blog.entity.RateLimitRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wxl
 * @date 2025/3/26 15:41
 * @description
 */
@Mapper
public interface RateLimitRuleMapper {
    List<RateLimitRule> findAll();
}
