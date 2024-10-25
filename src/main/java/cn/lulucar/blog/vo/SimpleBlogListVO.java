package cn.lulucar.blog.vo;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
@Data
public class SimpleBlogListVO implements Serializable {

    private Long blogId;

    private String blogTitle;
    
}
