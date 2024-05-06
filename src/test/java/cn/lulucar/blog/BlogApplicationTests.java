package cn.lulucar.blog;


import cn.lulucar.blog.entity.Blog;
import cn.lulucar.blog.mapper.BlogMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
class BlogApplicationTests {
    @Autowired
    BlogMapper blogMapper;
    @ParameterizedTest
    @ValueSource(ints = {0,1})
    void getBlogListForIndexPage(int type) {
        List<Blog> blogListByType = blogMapper.findBlogListByType(type, 9);
        for (Blog blog : blogListByType) {
            System.out.println(blog);
        }
    }
}
