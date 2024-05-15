package cn.lulucar.blog.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BlogListVO implements Serializable {

    private Long blogId;

    private String blogTitle;

    private String blogSubUrl;

    private String blogCoverImage;

    private Integer blogCategoryId;

    private String blogCategoryIcon;

    private String blogCategoryName;

    private String blogContent;

    // 使用自定义的序列化器和反序列化器来处理LocalDateTime
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    // 使用JsonFormat注解来指定日期时间的格式
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
}
