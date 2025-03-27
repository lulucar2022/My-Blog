package cn.lulucar.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxl
 * @date 2025/3/26 15:29
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateLimitRule {
    private Long id;
    private String dimension;
    private int capacity;
    private double refillRate;
}
