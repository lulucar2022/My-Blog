package cn.lulucar.blog.controller.vo;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
@Data
public class SimpleBlogListVO implements Serializable {

    private Long blogId;

    private String blogTitle;
    
}
