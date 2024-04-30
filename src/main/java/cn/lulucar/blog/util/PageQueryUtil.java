package cn.lulucar.blog.util;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wenxiaolan
 * @ClassName PageQueryUtil
 * @date 2024/4/21 14:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageQueryUtil extends LinkedHashMap<String,Object> {
    //当前页码
    private int page;
    //每页条数
    private int limit;
    // 把分页所需要的参数封装进Map集合中
    public PageQueryUtil(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("start", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
    }
    @Override
    public String toString() {
        return "PageUtil{" +
                "page=" + page +
                ", limit=" + limit +
                '}';
    }

}
