package cn.lulucar.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author wenxiaolan
 * @ClassName RedisConfiguration
 * @date 2024/7/1 11:55
 * @description
 */
@Configuration
public class RedisConfiguration {
    /**
     * @param redisConnectionFactory    Redis 自己配置好了连接工厂
     * 两种连接工厂 Lettuce 和 Jedis
     * 默认使用 lettuce
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //创建一个createObjectMapperConfig对象
        CreateObjectMapperConfig createObjectMapperConfig = new CreateObjectMapperConfig();
        // ObjectMapper 转译
        ObjectMapper objectMapper = createObjectMapperConfig.createObjectMapper();

        // Json 序列化配置
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);

        // 设置连接工厂
        template.setConnectionFactory(redisConnectionFactory);
        // 把对象转为 JSON 格式
        template.setDefaultSerializer(objectJackson2JsonRedisSerializer);
        // key设置StringRedisSerializer序列化
        template.setKeySerializer(new StringRedisSerializer());
        // value设置GenericJackson2JsonRedisSerializer序列化
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        // Hash key设置string，value设置json
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        return template;
    }
}
