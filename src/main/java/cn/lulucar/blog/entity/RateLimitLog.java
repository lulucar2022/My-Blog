package cn.lulucar.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wxl
 * @date 2025/3/26 15:54
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateLimitLog {
    private Long id;
    private String ip;
    private String userId;
    private String uri;
    private LocalDateTime requestTime;
    
}
