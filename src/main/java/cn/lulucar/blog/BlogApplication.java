package cn.lulucar.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = "cn.lulucar.blog.mapper")
@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(BlogApplication.class);
        springApplication.setBannerMode(Banner.Mode.CONSOLE);
        springApplication.run(args);
    }

}
