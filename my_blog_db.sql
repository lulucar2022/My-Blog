/*
 Navicat Premium Data Transfer

 Source Server         : 华为云
 Source Server Type    : MySQL
 Source Server Version : 80037 (8.0.37-0ubuntu0.22.04.3)
 Source Host           : localhost:3306
 Source Schema         : my_blog_db

 Target Server Type    : MySQL
 Target Server Version : 80037 (8.0.37-0ubuntu0.22.04.3)
 File Encoding         : 65001

 Date: 26/03/2025 15:21:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for generator_test
-- ----------------------------
DROP TABLE IF EXISTS `generator_test`;
CREATE TABLE `generator_test`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `test` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '测试字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of generator_test
-- ----------------------------

-- ----------------------------
-- Table structure for jdbc_test
-- ----------------------------
DROP TABLE IF EXISTS `jdbc_test`;
CREATE TABLE `jdbc_test`  (
  `type` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类型',
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '名称'
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of jdbc_test
-- ----------------------------
INSERT INTO `jdbc_test` VALUES ('com.zaxxer.hikari.HikariDataSource', 'hikari数据源');
INSERT INTO `jdbc_test` VALUES ('org.apache.commons.dbcp2.BasicDataSource', 'dbcp2数据源');
INSERT INTO `jdbc_test` VALUES ('test', '测试类');
INSERT INTO `jdbc_test` VALUES ('类别2', '测试类2');

-- ----------------------------
-- Table structure for tb_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin_user`;
CREATE TABLE `tb_admin_user`  (
  `admin_user_id` int NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `login_user_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '管理员登陆名称',
  `login_password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '管理员登陆密码',
  `nick_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '管理员显示昵称',
  `locked` tinyint NULL DEFAULT 0 COMMENT '是否锁定 0未锁定 1已锁定无法登陆',
  PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_admin_user
-- ----------------------------
INSERT INTO `tb_admin_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '十三', 0);
INSERT INTO `tb_admin_user` VALUES (2, 'root', '$2a$10$ofq7bzN.eyxnn9caOYRCF.SfJrOW9WH1i7hbMcGdfQyzhqUJze0D6', 'lulucar', 0);
INSERT INTO `tb_admin_user` VALUES (3, 'lulucar', '$2a$10$Gvzh76OBkNzo99iaN9YzCOmUbwf7fSejPO5f43nm8U/dzlFcTVJDK', '牛爷爷', 0);

-- ----------------------------
-- Table structure for tb_blog
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog`;
CREATE TABLE `tb_blog`  (
  `blog_id` bigint NOT NULL AUTO_INCREMENT COMMENT '博客表主键id',
  `blog_title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '博客标题',
  `blog_sub_url` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '博客自定义路径url',
  `blog_cover_image` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '博客封面图',
  `blog_content` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '博客内容',
  `blog_category_id` int NOT NULL COMMENT '博客分类id',
  `blog_category_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '博客分类(冗余字段)',
  `blog_tags` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '博客标签',
  `blog_status` tinyint NOT NULL DEFAULT 0 COMMENT '0-草稿 1-发布',
  `blog_views` bigint NOT NULL DEFAULT 0 COMMENT '阅读量',
  `enable_comment` tinyint NOT NULL DEFAULT 0 COMMENT '0-允许评论 1-不允许评论',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`blog_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog
-- ----------------------------
INSERT INTO `tb_blog` VALUES (1, '我是lulucar', 'about', 'http://1.94.5.131:8888/upload/222_20240630_230019.jpg', '## About me\n\nlulucar\n\n希望你能坚持每天都学习一些知识。\n明日复明日，明日何其多。不要想当然地以为今天放松一下，还有明天。这样只会越来越懒惰。\n每天都要逼自己一把，按照规划进行学习。\n## Contact\n\n- 我的邮箱：wenxiaolan02@outlook.com\n\n## Quote\n\n> 学而不思则罔，思而不学则殆。\n\n', 24, '日常随笔', '个人感想', 1, 243, 0, 1, '2017-03-12 00:31:15', '2024-06-30 23:29:34');
INSERT INTO `tb_blog` VALUES (2, '文章总目录', '', '/admin/dist/img/rand/13.jpg', '<h2 id=\"springboot2\">23 个实验带你轻松玩转 Spring Boot</h2>\n\n- [**开篇词：《23 个实验带你轻松玩转 Spring Boot》导读**](https://www.shiyanlou.com/courses/1274)\n- [第02课：Spring Boot 项目开发环境搭建](https://www.shiyanlou.com/courses/1274)\n- [第03课：快速构建 Spring Boot 应用](https://www.shiyanlou.com/courses/1274)\n- [第04课：Spring Boot 基础功能开发](https://www.shiyanlou.com/courses/1274)\n- [第05课：Spring Boot 项目开发之 web 项目开发讲解](https://www.shiyanlou.com/courses/1274)\n- [第06课：Spring Boot 整合 JSP 开发 web 项目](https://www.shiyanlou.com/courses/1274)\n- [第07课：模板引擎介绍及 Spring Boot 整合 Thymeleaf](https://www.shiyanlou.com/courses/1274)\n- [第08课：Thymeleaf 语法详解](https://www.shiyanlou.com/courses/1274)\n- [第09课：FreeMarker 模板引擎整合使用教程](https://www.shiyanlou.com/courses/1274)\n- [第10课：Spring Boot 处理文件上传及路径回显](https://www.shiyanlou.com/courses/1274)\n- [第11课：Spring Boot 自动配置数据源及操作数据库](https://www.shiyanlou.com/courses/1274)\n- [第12课：Spring Boot 整合 Druid 数据源](https://www.shiyanlou.com/courses/1274)\n- [第13课：Spring Boot 整合 MyBatis 操作数据库](https://www.shiyanlou.com/courses/1274)\n- [第14课：Spring Boot 中的事务处理](https://www.shiyanlou.com/courses/1274)\n- [第15课：Spring Boot 整合 Redis 操作缓存模块](https://www.shiyanlou.com/courses/1274)\n- [第16课：Spring Boot 项目开发之实现定时任务](https://www.shiyanlou.com/courses/1274)\n- [第17课：Spring Boot 自定义错误页面](https://www.shiyanlou.com/courses/1274)\n- [第18课：Spring Boot 集成 Swagger 生成接口文档](https://www.shiyanlou.com/courses/1274)\n- [第19课：Spring Boot 项目打包部署介绍](https://www.shiyanlou.com/courses/1274)\n- [第20课：Spring Boot Admin 介绍及整合使用](https://www.shiyanlou.com/courses/1274)\n- [第21课：Spring Boot 资讯管理信息系统开发实战(一)](https://www.shiyanlou.com/courses/1274)\n- [第22课：Spring Boot 资讯管理信息系统开发实战(二)](https://www.shiyanlou.com/courses/1274)\n- [第23课：Spring Boot 资讯管理信息系统开发实战(三)](https://www.shiyanlou.com/courses/1274)\n- [第24课：Spring Boot 资讯管理信息系统开发实战(四)](https://www.shiyanlou.com/courses/1274)\n\n<h2 id=\"springboot1\">Spring Boot 入门及前后端分离项目实践</h2>\n\n* [开篇词：SpringBoot入门及前后端分离项目实践导读](https://www.shiyanlou.com/courses/1244)\n* [第02课：快速认识 Spring Boot 技术栈](https://www.shiyanlou.com/courses/1244)\n* [第03课：开发环境搭建](https://www.shiyanlou.com/courses/1244)\n* [第04课：快速构建 Spring Boot 应用](https://www.shiyanlou.com/courses/1244)\n* [第05课：Spring Boot 之基础 web 功能开发](https://www.shiyanlou.com/courses/1244)\n* [第06课：Spring Boot 之数据库连接操作](https://www.shiyanlou.com/courses/1244)\n* [第07课：Spring Boot 整合 MyBatis 操作数据库](https://www.shiyanlou.com/courses/1244)\n* [第08课：Spring Boot 处理文件上传及路径回显](https://www.shiyanlou.com/courses/1244)\n* [第09课：Spring Boot 项目实践之前后端分离详解](https://www.shiyanlou.com/courses/1244)\n* [第10课：Spring Boot 项目实践之 API 设计](https://www.shiyanlou.com/courses/1244)\n* [第11课：Spring Boot 项目实践之登录模块实现](https://www.shiyanlou.com/courses/1244)\n* [第12课：Spring Boot 项目实践之分页功能实现](https://www.shiyanlou.com/courses/1244)\n* [第13课：Spring Boot 项目实践之jqgrid分页整合](https://www.shiyanlou.com/courses/1244)\n* [第14课：Spring Boot 项目实践之编辑功能实现](https://www.shiyanlou.com/courses/1244)\n* [第15课：Spring Boot 项目实践之用户管理模块实现](https://www.shiyanlou.com/courses/1244)\n* [第16课：Spring Boot 项目实践之图片管理模块](https://www.shiyanlou.com/courses/1244)\n* [第17课：Spring Boot 项目实践之富文本编辑器介绍及整合](https://www.shiyanlou.com/courses/1244)\n* [第18课：Spring Boot 项目实践之信息管理模块实现](https://www.shiyanlou.com/courses/1244)\n\n<h2 id=\"ssm4\">从零开始搭建一个精美且实用的管理后台</h2>\n\n- [SSM 搭建精美实用的管理系统](http://gitbook.cn/m/mazi/comp/column?columnId=5b4dae389bcda53d07056bc9&sceneId=22778a708b0f11e8974b497483da0812)\n- [导读：自己动手实现 JavaWeb 后台管理系统](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4db47e9bcda53d07056f5f)\n- [第01课：Spring MVC+ Spring + Mybatis “三大框架”介绍](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4db5b89bcda53d070590de)\n- [第02课：前期准备工作及基础环境搭建](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4eb8e701d18a561f341b72)\n- [第03课：三大框架的整合](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4ee11c01d18a561f342c0f)\n- [第04课：Tomcat 8 安装部署及功能改造](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1335dbb1436093a6ca17)\n- [第05课：产品设计之搭建精美实用的后台管理系统](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1b35dbb1436093a6cc7a)\n- [第06课：前端选型 AdminLTE3](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1b70dbb1436093a6cc87)\n- [第07课：登录模块的系统设计和实现](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1b80dbb1436093a6cc8e)\n- [第08课：使用 JqGrid 插件实现分页功能](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1b92dbb1436093a6cc93)\n- [第09课：弹框组件整合——完善添加和修改功能](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1bbddbb1436093a6cc9b)\n- [第10课：图片管理模块](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1bd0dbb1436093a6cca1)\n- [第11课：多图上传与大文件分片上传、断点续传](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1bdedbb1436093a6cca2)\n- [第12课：文件导入导出功能](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1beddbb1436093a6cca8)\n- [第13课：富文本信息管理模块](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c00dbb1436093a6ccae)\n- [第14课：SweetAlert 插件整合及搜索功能实现](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c11dbb1436093a6ccb1)\n- [第15课：项目发布——Linux 命令及发布流程](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c21dbb1436093a6ccb6)\n- [第16课：项目优化篇之日志输出](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c30dbb1436093a6ccbb)\n- [第17课：项目优化之单元测试](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c3fdbb1436093a6ccc1)\n- [第18课：项目优化之数据库连接池](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c50dbb1436093a6ccca)\n- [第19课：项目优化之 Druid 整合](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c65dbb1436093a6cccd)\n- [第20课：项目优化之缓存整合](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c7cdbb1436093a6ccd6)\n- [第21课：网站架构演进及 Nginx 介绍](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c8bdbb1436093a6ccda)\n- [第22课：Nginx + Tomcat 集群搭建](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c9ddbb1436093a6cce2)\n- [第23课：Nginx 动静分离](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1cb7dbb1436093a6cceb)\n\n<h2 id=\"ssm3\">SSM整合进阶篇</h2>\n\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（一）设计一套好的RESTful API](http://www.cnblogs.com/han-1034683568/p/7196345.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（二）RESTful API实战笔记(接口设计及Java后端实现)](http://www.cnblogs.com/han-1034683568/p/7300547.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（三）使用ajax方法实现form表单的提交](http://www.cnblogs.com/han-1034683568/p/7199168.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（四）RESTful实战(前端代码修改)](http://www.cnblogs.com/han-1034683568/p/7552007.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（五）记录一下从懵懂到理解RESTful的过程](http://www.cnblogs.com/han-1034683568/p/7569870.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（六）一定要RESTful吗？](http://www.cnblogs.com/han-1034683568/p/7663641.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（七）一次线上Mysql数据库崩溃事故的记录](http://www.cnblogs.com/han-1034683568/p/7787659.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（八）线上Mysql数据库崩溃事故的原因和处理](http://www.cnblogs.com/han-1034683568/p/7822237.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（九）Linux下安装redis及redis的常用命令和操作](http://www.cnblogs.com/han-1034683568/p/7862188.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（十）easyUI整合KindEditor详细教程](http://www.cnblogs.com/han-1034683568/p/7930542.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（十一）redis密码设置、安全设置](http://www.cnblogs.com/han-1034683568/p/7978577.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（十二）Spring集成Redis缓存](http://www.cnblogs.com/han-1034683568/p/7994231.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（十三）MyBatis+MySQL返回插入的主键id](http://www.cnblogs.com/han-1034683568/p/8305122.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（十四）Redis正确的使用姿势](http://www.cnblogs.com/han-1034683568/p/8406497.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（十五）阶段总结](http://www.cnblogs.com/han-1034683568/p/9069008.html)\n\n<h2 id=\"idea\">Intellij IDEA相关笔记</h2>\n\n- [如何查看IntelliJ IDEA的版本信息](http://www.cnblogs.com/han-1034683568/p/9135192.html)\n- [Plugin \'Lombok Plugin\' is incompatible with this installation](http://www.cnblogs.com/han-1034683568/p/9135074.html)\n- [IDEA安装Lombok插件失败的解决方案](http://www.cnblogs.com/han-1034683568/p/9134980.html)\n- [Intellij IDEA debug模式下项目启动慢/无法启动的事件解决过程记录](http://www.cnblogs.com/han-1034683568/p/8603588.html)\n- [Intellij IDEA查看所有断点](http://www.cnblogs.com/han-1034683568/p/8603110.html)\n- [IntelliJ IDEA 报错：Error:java: 未结束的字符串文字](http://www.cnblogs.com/han-1034683568/p/6439723.html)\n- [IntelliJ IDEA 常用快捷键mac版](http://www.cnblogs.com/han-1034683568/p/6492342.html)\n\n<h2 id=\"diary\">日常手记</h2>\n\n- [开启mac上印象笔记的代码块](http://www.cnblogs.com/han-1034683568/p/9021263.html)\n- [程序员，你怎么这么忙？](http://www.cnblogs.com/han-1034683568/p/8968959.html)\n- [新购阿里云服务器ECS创建之后无法ssh连接的问题处理](http://www.cnblogs.com/han-1034683568/p/8856560.html)\n- [CentOS 7.2:Failed to start IPv4 firewall with iptables](http://www.cnblogs.com/han-1034683568/p/8854613.html)\n- [JDK8 stream toMap() java.lang.IllegalStateException: Duplicate key异常解决(key重复)](http://www.cnblogs.com/han-1034683568/p/8624447.html)\n- [我在博客园的这一年小记](http://www.cnblogs.com/han-1034683568/p/8443428.html)\n- [记录一下我的2017年阅读书单](http://www.cnblogs.com/han-1034683568/p/8432261.html)\n- [2017总结](http://www.cnblogs.com/han-1034683568/p/8337394.html)\n- [微信公众号问题：{\"errcode\":40125,\"errmsg\":\"invalid appsecret, view more at http:\\/\\/t.cn\\/LOEdzVq, hints: [ req_id: kL8J90219sg58 ]\"}](http://www.cnblogs.com/han-1034683568/p/8286777.html)\n- [git删除本地分支](http://www.cnblogs.com/han-1034683568/p/8241369.html)\n- [阿里巴巴Java开发规约插件p3c详细教程及使用感受](http://www.cnblogs.com/han-1034683568/p/7682594.html)\n- [阿里官方Java代码规范标准《阿里巴巴Java开发手册 终极版 v1.3.0》下载](http://www.cnblogs.com/han-1034683568/p/7680354.html)\n- [程序员视角：鹿晗公布恋情是如何把微博搞炸的？](http://www.cnblogs.com/han-1034683568/p/7642213.html)\n- [could not resolve host: github.com 问题解决办法](http://www.cnblogs.com/han-1034683568/p/6457894.html)\n- [使用git恢复未提交的误删数据](http://www.cnblogs.com/han-1034683568/p/6444937.html)\n- [springboot开启access_log日志输出](http://www.cnblogs.com/han-1034683568/p/6963144.html)\n- [Error: Cannot find module \'gulp-clone\'问题的解决](http://www.cnblogs.com/han-1034683568/p/6479288.html)\n- [Markdown语法讲解及MWeb使用教程](http://www.cnblogs.com/han-1034683568/p/6556348.html)\n- [javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)](http://www.cnblogs.com/han-1034683568/p/7009096.html)\n- [org.springframework.data.redis.serializer.SerializationException: Cannot serialize;](http://www.cnblogs.com/han-1034683568/p/7994322.html)\n\n<h2 id=\"13blog\">My Blog</h2>\n\n- [Docker+SpringBoot+Mybatis+thymeleaf的Java博客系统开源啦](http://www.cnblogs.com/han-1034683568/p/6840493.html)\n- [My-Blog搭建过程：如何让一个网站从零到可以上线访问](http://www.cnblogs.com/han-1034683568/p/6885545.html)\n- [将数据的初始化放到docker中的整个工作过程(问题记录)](http://www.cnblogs.com/han-1034683568/p/6941313.html)\n- [利用Dockerfile构建mysql镜像并实现数据的初始化及权限设置](http://www.cnblogs.com/han-1034683568/p/6941337.html)\n- [解决Docker容器时区及时间不同步的问题](http://www.cnblogs.com/han-1034683568/p/6957132.html)\n- [Java开源博客My-Blog之docker容器组件化修改](http://www.cnblogs.com/han-1034683568/p/7102765.html)\n- [运行shell脚本时报错\"\\[\\[ : not found\"解决方法](http://www.cnblogs.com/han-1034683568/p/7211392.html)\n- [shell脚本中字符串的常见操作及\"command not found\"报错处理(附源码)](http://www.cnblogs.com/han-1034683568/p/7217047.html)\n- [Java开源博客My-Blog之mysql容器重复初始化的严重bug修复过程](http://www.cnblogs.com/han-1034683568/p/7231895.html)\n- [Mybatis-Generator生成Mapper文件中if test=\"criteria.valid\"的问题解答](http://www.cnblogs.com/han-1034683568/p/7281474.html)\n- [Springboot与Thymeleaf模板引擎整合基础教程](http://www.cnblogs.com/han-1034683568/p/7520012.html)\n- [thymeleaf模板引擎调用java类中的方法](http://www.cnblogs.com/han-1034683568/p/7527564.html)\n\n<h2 id=\"message-attack\">短信接口攻击事件</h2>\n\n- [短信发送接口被恶意访问的网络攻击事件(一)紧张的遭遇战险胜](http://www.cnblogs.com/han-1034683568/p/6973269.html)\n- [短信发送接口被恶意访问的网络攻击事件(二)肉搏战-阻止恶意请求](http://www.cnblogs.com/han-1034683568/p/7001785.html)\n- [短信发送接口被恶意访问的网络攻击事件(三)定位恶意IP的日志分析脚本](http://www.cnblogs.com/han-1034683568/p/7040417.html)\n- [短信发送接口被恶意访问的网络攻击事件(四)完结篇--搭建WAF清理战场](http://www.cnblogs.com/han-1034683568/p/7090409.html)\n\n<h2 id=\"read\">读书笔记</h2>\n\n- [《实战java高并发程序设计》源码整理及读书笔记](http://www.cnblogs.com/han-1034683568/p/6918160.html)\n- [《大型网站技术架构:核心原理与案例分析》读书笔记](http://www.cnblogs.com/han-1034683568/p/7597564.html)\n- [大型网站技术架构(二)--大型网站架构演化](http://www.cnblogs.com/han-1034683568/p/8423447.html)\n- [大型网站技术架构(三)--架构模式](http://www.cnblogs.com/han-1034683568/p/8677349.html)\n- [大型网站技术架构(四)--核心架构要素](http://www.cnblogs.com/han-1034683568/p/9049758.html)\n\n<h2 id=\"ssm2\">SSM整合优化篇</h2>\n\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（一）System.out.print与Log](http://www.cnblogs.com/han-1034683568/p/6637914.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（二）Log4j讲解与整合](http://www.cnblogs.com/han-1034683568/p/6641808.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（三）代码测试](http://www.cnblogs.com/han-1034683568/p/6642306.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（四）单元测试实例](http://www.cnblogs.com/han-1034683568/p/6649077.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（五）结合MockMvc进行服务端的单元测试](http://www.cnblogs.com/han-1034683568/p/6653620.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（六）easyUI与富文本编辑器UEditor整合](http://www.cnblogs.com/han-1034683568/p/6664660.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（七）图片上传功能](http://www.cnblogs.com/han-1034683568/p/6692150.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（八）代码优化整理小记及个人吐槽](http://www.cnblogs.com/han-1034683568/p/6706158.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（九）数据层优化-jdbc连接池简述、druid简介](http://www.cnblogs.com/han-1034683568/p/6719298.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（十）数据层优化-整合druid](http://www.cnblogs.com/han-1034683568/p/6725191.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（十一）数据层优化-druid监控及慢sql记录](http://www.cnblogs.com/han-1034683568/p/6730869.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（十二）数据层优化-explain关键字及慢sql优化](http://www.cnblogs.com/han-1034683568/p/6758578.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（十三）数据层优化-表规范、索引优化](http://www.cnblogs.com/han-1034683568/p/6768807.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（十四）谈谈写博客的原因和项目优化](http://www.cnblogs.com/han-1034683568/p/6782019.html)\n\n<h2 id=\"ssm1\">SSM整合基础篇</h2>\n\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（一）项目简述及技术选型介绍](http://www.cnblogs.com/han-1034683568/p/6440090.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（二）牛刀小试](http://www.cnblogs.com/han-1034683568/p/6440157.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（三）搭建步骤](http://www.cnblogs.com/han-1034683568/p/6476827.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（四）代码简化](http://www.cnblogs.com/han-1034683568/p/6476852.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（五）讲一下maven](http://www.cnblogs.com/han-1034683568/p/6486117.html)\n- [Maven构建项目速度太慢的解决办法](http://www.cnblogs.com/han-1034683568/p/6498637.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（六）maven整合SSM](http://www.cnblogs.com/han-1034683568/p/6507186.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（七）JDBC url的连接参数](http://www.cnblogs.com/han-1034683568/p/6512215.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（八）mysql中文查询bug修复](http://www.cnblogs.com/han-1034683568/p/6517344.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（九）版本控制](http://www.cnblogs.com/han-1034683568/p/6540079.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（十）SVN搭建](http://www.cnblogs.com/han-1034683568/p/6545751.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（十一）SVN服务器进阶](http://www.cnblogs.com/han-1034683568/p/6551498.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（十二）阶段总结](http://www.cnblogs.com/han-1034683568/p/6562092.html)', 24, '日常随笔', '目录', 1, 15, 0, 1, '2019-04-24 15:42:23', '2019-04-24 15:42:23');
INSERT INTO `tb_blog` VALUES (3, 'Spring+SpringMVC+MyBatis整合系列(easyUI、AdminLte3)', '', '/admin/dist/img/rand/36.jpg', '## 实战篇（付费教程）\n\n- [SSM 搭建精美实用的管理系统](http://gitbook.cn/m/mazi/comp/column?columnId=5b4dae389bcda53d07056bc9&sceneId=22778a708b0f11e8974b497483da0812)\n- [导读：自己动手实现 JavaWeb 后台管理系统](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4db47e9bcda53d07056f5f)\n- [第01课：Spring MVC+ Spring + Mybatis “三大框架”介绍](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4db5b89bcda53d070590de)\n- [第02课：前期准备工作及基础环境搭建](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4eb8e701d18a561f341b72)\n- [第03课：三大框架的整合](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4ee11c01d18a561f342c0f)\n- [第04课：Tomcat 8 安装部署及功能改造](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1335dbb1436093a6ca17)\n- [第05课：产品设计之搭建精美实用的后台管理系统](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1b35dbb1436093a6cc7a)\n- [第06课：前端选型 AdminLTE3](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1b70dbb1436093a6cc87)\n- [第07课：登录模块的系统设计和实现](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1b80dbb1436093a6cc8e)\n- [第08课：使用 JqGrid 插件实现分页功能](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1b92dbb1436093a6cc93)\n- [第09课：弹框组件整合——完善添加和修改功能](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1bbddbb1436093a6cc9b)\n- [第10课：图片管理模块](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1bd0dbb1436093a6cca1)\n- [第11课：多图上传与大文件分片上传、断点续传](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1bdedbb1436093a6cca2)\n- [第12课：文件导入导出功能](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1beddbb1436093a6cca8)\n- [第13课：富文本信息管理模块](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c00dbb1436093a6ccae)\n- [第14课：SweetAlert 插件整合及搜索功能实现](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c11dbb1436093a6ccb1)\n- [第15课：项目发布——Linux 命令及发布流程](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c21dbb1436093a6ccb6)\n- [第16课：项目优化篇之日志输出](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c30dbb1436093a6ccbb)\n- [第17课：项目优化之单元测试](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c3fdbb1436093a6ccc1)\n- [第18课：项目优化之数据库连接池](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c50dbb1436093a6ccca)\n- [第19课：项目优化之 Druid 整合](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c65dbb1436093a6cccd)\n- [第20课：项目优化之缓存整合](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c7cdbb1436093a6ccd6)\n- [第21课：网站架构演进及 Nginx 介绍](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c8bdbb1436093a6ccda)\n- [第22课：Nginx + Tomcat 集群搭建](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1c9ddbb1436093a6cce2)\n- [第23课：Nginx 动静分离](https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9/topic/5b4f1cb7dbb1436093a6cceb)\n\n## 进阶篇（免费开源）\n\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（一）设计一套好的RESTful API](http://www.cnblogs.com/han-1034683568/p/7196345.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（二）RESTful API实战笔记(接口设计及Java后端实现)](http://www.cnblogs.com/han-1034683568/p/7300547.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（三）使用ajax方法实现form表单的提交](http://www.cnblogs.com/han-1034683568/p/7199168.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（四）RESTful实战(前端代码修改)](http://www.cnblogs.com/han-1034683568/p/7552007.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（五）记录一下从懵懂到理解RESTful的过程](http://www.cnblogs.com/han-1034683568/p/7569870.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（六）一定要RESTful吗？](http://www.cnblogs.com/han-1034683568/p/7663641.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（七）一次线上Mysql数据库崩溃事故的记录](http://www.cnblogs.com/han-1034683568/p/7787659.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（八）线上Mysql数据库崩溃事故的原因和处理](http://www.cnblogs.com/han-1034683568/p/7822237.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（九）Linux下安装redis及redis的常用命令和操作](http://www.cnblogs.com/han-1034683568/p/7862188.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（十）easyUI整合KindEditor详细教程](http://www.cnblogs.com/han-1034683568/p/7930542.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（十一）redis密码设置、安全设置](http://www.cnblogs.com/han-1034683568/p/7978577.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（十二）Spring集成Redis缓存](http://www.cnblogs.com/han-1034683568/p/7994231.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（十三）MyBatis+MySQL返回插入的主键id](http://www.cnblogs.com/han-1034683568/p/8305122.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（十四）Redis正确的使用姿势](http://www.cnblogs.com/han-1034683568/p/8406497.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合进阶篇（十五）阶段总结](http://www.cnblogs.com/han-1034683568/p/9069008.html)\n\n\n## 优化篇（免费开源）\n\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（一）System.out.print与Log](http://www.cnblogs.com/han-1034683568/p/6637914.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（二）Log4j讲解与整合](http://www.cnblogs.com/han-1034683568/p/6641808.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（三）代码测试](http://www.cnblogs.com/han-1034683568/p/6642306.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（四）单元测试实例](http://www.cnblogs.com/han-1034683568/p/6649077.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（五）结合MockMvc进行服务端的单元测试](http://www.cnblogs.com/han-1034683568/p/6653620.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（六）easyUI与富文本编辑器UEditor整合](http://www.cnblogs.com/han-1034683568/p/6664660.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（七）图片上传功能](http://www.cnblogs.com/han-1034683568/p/6692150.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（八）代码优化整理小记及个人吐槽](http://www.cnblogs.com/han-1034683568/p/6706158.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（九）数据层优化-jdbc连接池简述、druid简介](http://www.cnblogs.com/han-1034683568/p/6719298.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（十）数据层优化-整合druid](http://www.cnblogs.com/han-1034683568/p/6725191.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（十一）数据层优化-druid监控及慢sql记录](http://www.cnblogs.com/han-1034683568/p/6730869.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（十二）数据层优化-explain关键字及慢sql优化](http://www.cnblogs.com/han-1034683568/p/6758578.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（十三）数据层优化-表规范、索引优化](http://www.cnblogs.com/han-1034683568/p/6768807.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合优化篇（十四）谈谈写博客的原因和项目优化](http://www.cnblogs.com/han-1034683568/p/6782019.html)\n\n\n## 基础篇（免费开源）\n\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（一）项目简介](http://www.cnblogs.com/han-1034683568/p/6440090.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（二）牛刀小试](http://www.cnblogs.com/han-1034683568/p/6440157.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（三）搭建步骤](http://www.cnblogs.com/han-1034683568/p/6476827.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（四）代码简化](http://www.cnblogs.com/han-1034683568/p/6476852.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（五）讲一下maven](http://www.cnblogs.com/han-1034683568/p/6486117.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（六）maven整合SSM](http://www.cnblogs.com/han-1034683568/p/6507186.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（七）JDBC url的连接参数](http://www.cnblogs.com/han-1034683568/p/6512215.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（八）mysql中文查询bug修复](http://www.cnblogs.com/han-1034683568/p/6517344.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（九）版本控制](http://www.cnblogs.com/han-1034683568/p/6540079.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（十）SVN搭建](http://www.cnblogs.com/han-1034683568/p/6545751.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（十一）SVN服务器进阶](http://www.cnblogs.com/han-1034683568/p/6551498.html)\n- [Spring+SpringMVC+MyBatis+easyUI整合基础篇（十二）阶段总结](http://www.cnblogs.com/han-1034683568/p/6562092.html)\n\n\n推荐一下自己的达人课，感兴趣的朋友可以看一下：[SSM搭建精美实用的管理系统](http://gitbook.cn/m/mazi/comp/column?columnId=5b4dae389bcda53d07056bc9&sceneId=22778a708b0f11e8974b497483da0812)', 22, 'SSM整合进阶篇', 'Spring,SpringMVC,MyBatis,easyUI,AdminLte3', 1, 56, 0, 1, '2019-04-24 15:46:15', '2019-04-24 15:46:15');
INSERT INTO `tb_blog` VALUES (4, 'SpringBoot系列教程', '', '/admin/dist/img/rand/29.jpg', '<h2 id=\"springboot2\">23 个实验带你轻松玩转 Spring Boot</h2>\n\n- [**开篇词：《23 个实验带你轻松玩转 Spring Boot》导读**](https://www.shiyanlou.com/courses/1274)\n- [第02课：Spring Boot 项目开发环境搭建](https://www.shiyanlou.com/courses/1274)\n- [第03课：快速构建 Spring Boot 应用](https://www.shiyanlou.com/courses/1274)\n- [第04课：Spring Boot 基础功能开发](https://www.shiyanlou.com/courses/1274)\n- [第05课：Spring Boot 项目开发之 web 项目开发讲解](https://www.shiyanlou.com/courses/1274)\n- [第06课：Spring Boot 整合 JSP 开发 web 项目](https://www.shiyanlou.com/courses/1274)\n- [第07课：模板引擎介绍及 Spring Boot 整合 Thymeleaf](https://www.shiyanlou.com/courses/1274)\n- [第08课：Thymeleaf 语法详解](https://www.shiyanlou.com/courses/1274)\n- [第09课：FreeMarker 模板引擎整合使用教程](https://www.shiyanlou.com/courses/1274)\n- [第10课：Spring Boot 处理文件上传及路径回显](https://www.shiyanlou.com/courses/1274)\n- [第11课：Spring Boot 自动配置数据源及操作数据库](https://www.shiyanlou.com/courses/1274)\n- [第12课：Spring Boot 整合 Druid 数据源](https://www.shiyanlou.com/courses/1274)\n- [第13课：Spring Boot 整合 MyBatis 操作数据库](https://www.shiyanlou.com/courses/1274)\n- [第14课：Spring Boot 中的事务处理](https://www.shiyanlou.com/courses/1274)\n- [第15课：Spring Boot 整合 Redis 操作缓存模块](https://www.shiyanlou.com/courses/1274)\n- [第16课：Spring Boot 项目开发之实现定时任务](https://www.shiyanlou.com/courses/1274)\n- [第17课：Spring Boot 自定义错误页面](https://www.shiyanlou.com/courses/1274)\n- [第18课：Spring Boot 集成 Swagger 生成接口文档](https://www.shiyanlou.com/courses/1274)\n- [第19课：Spring Boot 项目打包部署介绍](https://www.shiyanlou.com/courses/1274)\n- [第20课：Spring Boot Admin 介绍及整合使用](https://www.shiyanlou.com/courses/1274)\n- [第21课：Spring Boot 资讯管理信息系统开发实战(一)](https://www.shiyanlou.com/courses/1274)\n- [第22课：Spring Boot 资讯管理信息系统开发实战(二)](https://www.shiyanlou.com/courses/1274)\n- [第23课：Spring Boot 资讯管理信息系统开发实战(三)](https://www.shiyanlou.com/courses/1274)\n- [第24课：Spring Boot 资讯管理信息系统开发实战(四)](https://www.shiyanlou.com/courses/1274)\n\n<h2 id=\"springboot1\">Spring Boot 入门及前后端分离项目实践</h2>\n\n* [开篇词：SpringBoot入门及前后端分离项目实践导读](https://www.shiyanlou.com/courses/1244)\n* [第02课：快速认识 Spring Boot 技术栈](https://www.shiyanlou.com/courses/1244)\n* [第03课：开发环境搭建](https://www.shiyanlou.com/courses/1244)\n* [第04课：快速构建 Spring Boot 应用](https://www.shiyanlou.com/courses/1244)\n* [第05课：Spring Boot 之基础 web 功能开发](https://www.shiyanlou.com/courses/1244)\n* [第06课：Spring Boot 之数据库连接操作](https://www.shiyanlou.com/courses/1244)\n* [第07课：Spring Boot 整合 MyBatis 操作数据库](https://www.shiyanlou.com/courses/1244)\n* [第08课：Spring Boot 处理文件上传及路径回显](https://www.shiyanlou.com/courses/1244)\n* [第09课：Spring Boot 项目实践之前后端分离详解](https://www.shiyanlou.com/courses/1244)\n* [第10课：Spring Boot 项目实践之 API 设计](https://www.shiyanlou.com/courses/1244)\n* [第11课：Spring Boot 项目实践之登录模块实现](https://www.shiyanlou.com/courses/1244)\n* [第12课：Spring Boot 项目实践之分页功能实现](https://www.shiyanlou.com/courses/1244)\n* [第13课：Spring Boot 项目实践之jqgrid分页整合](https://www.shiyanlou.com/courses/1244)\n* [第14课：Spring Boot 项目实践之编辑功能实现](https://www.shiyanlou.com/courses/1244)\n* [第15课：Spring Boot 项目实践之用户管理模块实现](https://www.shiyanlou.com/courses/1244)\n* [第16课：Spring Boot 项目实践之图片管理模块](https://www.shiyanlou.com/courses/1244)\n* [第17课：Spring Boot 项目实践之富文本编辑器介绍及整合](https://www.shiyanlou.com/courses/1244)\n* [第18课：Spring Boot 项目实践之信息管理模块实现](https://www.shiyanlou.com/courses/1244)', 24, '日常随笔', 'SpringBoot,入门教程,实战教程,spring-boot企业级开发', 1, 18, 0, 1, '2019-05-13 09:58:54', '2019-05-13 09:58:54');
INSERT INTO `tb_blog` VALUES (5, '新增文章', '', 'http://127.0.0.1:28083/admin/dist/img/rand/15.jpg', '# 1级标题\n文章内容\n这是修改内容。', 24, '日常随笔', '标签,标签2', 1, 0, 0, 1, '2024-05-03 09:27:15', '2024-05-03 09:27:15');
INSERT INTO `tb_blog` VALUES (6, '示例文章', '', 'http://127.0.0.1:28083/upload/222_20240503_153941.jpg', '# 1级标题\n## 2级标题\n### 3级标题\n#### 4级标题\n##### 5级标题\n###### 6级标题\n这里是正文！\n**加粗**\n*斜体*\n~~删除线~~\n> 引用\n- 无序\n1. 有序\n\n------------\n分割线\n`行内代码`\n```java\n代码块\n```\n\n2024-05-03 15:54:18 星期五\n今天：2024-05-07 18:47:13 星期二\n[========]\n\n', 24, '日常随笔', '标签', 1, 51, 0, 1, '2024-05-03 10:37:56', '2024-07-16 22:31:39');
INSERT INTO `tb_blog` VALUES (7, '不可评论的文章', '', 'http://127.0.0.1:28083/admin/dist/img/rand/16.jpg', '# 这是不可以评论的文章\n测试访客是否能评论', 26, 'Docker', '不可评论的文章', 1, 1, 1, 1, '2024-05-07 18:48:26', '2024-05-07 18:48:26');
INSERT INTO `tb_blog` VALUES (8, '项目介绍', 'about', 'http://1.94.5.131:8888/upload/%E7%96%BE%E9%A3%8E%E5%89%91%E8%B1%AA%E9%B8%AD_20240630_233405.png', '# 项目介绍\nMy-Blog 是一款个人博客项目。\n## 技术栈\nSpringBoot + Mybatis + Thymeleaf + SpringSecurity + Logback + Commonmark + Jsoup\n### 项目主要功能\n文章列表展示、添加文章、批量删除文章、修改文章、添加标签、修改标签、删除标签、发布评论、审核评论、回复评论、上传文件与图片、用户登录注册。', 24, '日常随笔', '项目,介绍', 1, 562, 0, 0, '2024-06-30 23:34:08', '2024-06-30 23:34:08');
INSERT INTO `tb_blog` VALUES (9, '微机原理期末复习', '', 'http://1.94.5.131:8888/admin/dist/img/rand/20.jpg', '# 复习大纲\n\n## 一、简答题\n\n### 指令系统，指令的组成\n\n- 处理器所能识别的所有指令的集合\n- 指令格式：零操作数、单操作数、双操作数、多操作数\n- 指令 = 操作码 + 操作数\n\n### 程序计数器\n\n- 程序计数器是一个特殊的寄存器，用于存储CPU当前正在执行的指令的内存地址，或者下一个要执行的指令的地址。随着指令的执行，程序计数器会自动递增，以指向下一条要执行的指令。\n\n### 8086指令的操作数寻址方式\n\n1. **立即数寻址（Immediate Addressing）**\n\n立即数寻址方式是指操作数是指令本身的一部分，直接包含在指令中。立即数通常用于赋值或初始化操作。\n\n- **特点**：操作数是一个常数，不需要访问内存。\n\n- **示例**：\n\n    MOV AX, 1234h  ; 将立即数1234h加载到AX寄存器中\n    ADD BX, 5      ; 将立即数5加到BX寄存器的当前值上\n\n2. **寄存器寻址（Register Addressing）**\n\n寄存器寻址方式是指**操作数存储在CPU的寄存器**中。指令直接指定寄存器作为操作数。\n\n- **特点**：操作数在寄存器内，访问速度快，不需要内存访问。\n\n- **示例**：\n\n     \n      MOV CX, DX     ; 将DX寄存器的值复制到CX寄存器\n      ADD AX, BX     ; 将BX寄存器的值加到AX寄存器的当前值上\n      \n\n3. **直接寻址（Direct Addressing）**\n\n直接寻址方式是指指令中包含了**操作数的内存地址**，操作数存储在该地址处。\n\n- **特点**：操作数的内存地址直接提供在指令中，适合访问固定地址的数据。\n\n- **示例**：\n\n  \n  MOV AX, [1234h] ; 将内存地址1234h的值加载到AX寄存器中\n  MOV [5678h], CX ; 将CX寄存器的值存储到内存地址5678h\n\n\n4. 寄存器间接寻址（Register Indirect Addressing）\n\n寄存器间接寻址方式是指令中指定的寄存器（如BX、SI、DI、BP）包含的是操作数的内存地址。\n\n- **特点**：寄存器提供内存地址，指向操作数在内存中的位置。\n\n- **示例**：\n\n\n  MOV AX, [BX] ; 将BX寄存器指向的内存地址处的值加载到AX寄存器中\n  MOV [SI], DX ; 将DX寄存器的值存储到SI寄存器指向的内存地址处\n\n\n5. **基址寻址（Base Addressing）**\n\n基址寻址方式是指令中使用基址寄存器（BX或BP）和一个偏移量（或位移量）来确定内存中的操作数地址。\n\n- **特点**：地址是基址寄存器和偏移量的和，通常用于数据段和堆栈段的内存访问。\n\n- **示例**：\n\n\n  MOV AX, [BX+04h] ; 将BX寄存器基址加上偏移量04h处的内存值加载到AX寄存器中\n  MOV [BP+08h], CX ; 将CX寄存器的值存储到BP寄存器基址加上偏移量08h处的内存\n\n\n6. **变址寻址（Indexed Addressing）**\n\n变址寻址方式是指令中使用变址寄存器（SI或DI）和一个偏移量来确定内存中的操作数地址。\n\n- **特点**：地址是变址寄存器和偏移量的和，常用于数组或表格的访问。\n\n- **示例**：\n\n\n  MOV AX, [SI+02h] ; 将SI寄存器基址加上偏移量02h处的内存值加载到AX寄存器中\n  MOV [DI+03h], BX ; 将BX寄存器的值存储到DI寄存器基址加上偏移量03h处的内存\n\n\n7. **基址加变址寻址（Base plus Index Addressing）**\n\n基址加变址寻址方式是指令中使用一个基址寄存器（BX或BP）和一个变址寄存器（SI或DI）来确定操作数的内存地址。\n\n- **特点**：地址是基址寄存器和变址寄存器的和，适用于复杂数据结构的访问。\n\n- **示例**：\n\n\n  MOV AX, [BX+SI] ; 将内存地址由BX和SI寄存器确定的值加载到AX寄存器中\n  MOV [BP+DI], DX ; 将DX寄存器的值存储到内存地址由BP和DI寄存器和确定的位置\n\n\n8. **基址加变址加偏移寻址（Base plus Index plus Displacement Addressing）**\n\n基址加变址加偏移寻址方式是指指令中使用一个基址寄存器、一个变址寄存器和一个偏移量共同来确定操作数的内存地址。\n\n- **特点**：地址是基址寄存器、变址寄存器和偏移量的和，适合访问复杂的数据结构，如多维数组。\n\n- **示例**：\n\n  MOV AX, [BX+SI+10h] ; 将内存地址由BX、SI寄存器和偏移量10h共同确定的值加载到AX寄存器中\n  MOV [BP+DI+20h], CX ; 将CX寄存器的值存储到内存地址由BP、DI寄存器和偏移量20h共同确定的位置\n\n\n9. **相对基址寻址（Relative Base Addressing）**\n\n相对基址寻址方式使用指令指针（IP）加上一个偏移量来确定目标地址。这种寻址方式通常用于控制转移指令（如跳转、调用）。\n\n- **特点**：操作数地址是相对于当前指令的地址，加上一个偏移量。\n\n- **示例**：\n\n\n  JMP LABEL        ; 跳转到标号LABEL（相对于当前IP的地址）\n  CALL [BX+SI+10h] ; 调用由BX和SI寄存器基址加上偏移量10h确定的内存地址\n\n\n10. **变址加偏移量寻址（Index Plus Displacement）**\n\n**变址加偏移量寻址** 是一种通过变址寄存器的值和一个固定的偏移量来确定内存地址的方式。\n\n- **变址寄存器（Index Register）**：用来存储一个相对于某个基准地址的偏移量。8086中的变址寄存器包括 `SI`（Source Index）和 `DI`（Destination Index）。\n- **偏移量（Displacement）**：一个直接在指令中指定的数值，用来与变址寄存器的值相加来计算实际的内存地址。\n\n***表：8086操作数寻址方式概览***\n\n| 寻址方式                                                  | 描述                                                       | 示例                  |\n| --------------------------------------------------------- | ---------------------------------------------------------- | --------------------- |\n| 立即数寻址（Immediate）                                   | 操作数直接嵌入在指令中                                     | `MOV AX, 1234h`       |\n| 寄存器寻址（Register）                                    | 操作数存储在寄存器中                                       | `MOV BX, AX`          |\n| 直接寻址（Direct）                                        | 操作数的内存地址直接包含在指令中                           | `MOV AX, [1234h]`     |\n| 寄存器间接寻址（Register Indirect）                       | 操作数地址存储在寄存器中                                   | `MOV AX, [BX]`        |\n| 基址寻址（Base）                                          | 使用基址寄存器加上偏移量确定操作数的内存地址               | `MOV AX, [BX+10h]`    |\n| 变址寻址（Indexed）                                       | 使用变址寄存器加上偏移量确定操作数的内存地址               | `MOV AX, [SI+20h]`    |\n| 基址加变址寻址（Base plus Index）                         | 使用基址寄存器和变址寄存器的和确定操作数的内存地址         | `MOV AX, [BX+SI]`     |\n| 基址加变址加偏移寻址（Base plus Index plus Displacement） | 使用基址寄存器、变址寄存器和偏移量的和确定操作数的内存地址 | `MOV AX, [BP+DI+30h]` |\n| 相对基址寻址（Relative Base）                             | 使用指令指针加上偏移量确定目标地址                         | `JMP LABEL`           |\n| 变址加偏移量寻址（Index Plus Displacement）               | 变址寄存器的值和一个固定的偏移量来确定内存地址             | MOV CX,DATA[DI]       |\n\n### 8086CPU从功能来说分为哪两部分，各负责完成什么任务？\n\n- 8086 CPU内部由**执行单元(EU)**和**总线接口单元(BIU)**组成。EU是CPU的计算核心，负责执行指令，进行算术运算、逻辑运算、移位操作等计算任务。BIU是CPU与外部系统之间的接口，负责执行指令预取、数据传输和地址计算等任务。\n\n### 辅助进位标志（AF）\n\n- 在进行算术运算的时候，如果低字节中低4位产生进位或者借位的时候，则置1，否则置0。即当两个字节相加时，如果从第3位向第4位形成了进位，则AF=1\n\n### 位移量和基地址\n\n- **基地址**：表示一个段的首地址，每个段最大为**64KB**。\n- **位移量**：在段内相对于段起始地址的偏移量。\n\n### MOV指令\n\n`MOV	DOPD,	SOPD`\n\n- 其有两个操作数，**左边是目标操作数**，**右边是源操作数**。\n\n-  MOV指令是通用的传送指令\n- CPU内部寄存器之间的数据传送。\n- 立即数传送至 CPU 内部通用寄存器。\n- 实现用立即数给存储单元赋值。\n\n|   MOV操作数    |     MOV码举例     |\n| :------------: | :---------------: |\n| 存储器，寄存器 | MOV COUNT[DI], CX |\n| 存储器，累加器 | MOV ARRAY[SI], AL |\n| 寄存器，寄存器 |    MOV AX, CX     |\n\n### 根据所处的位置不同，总线的分类\n\n- **片内总线**：位于**微处理器芯片内部**，用于**ALU**于各种寄存器和其它功能单元之间的相互连接\n- **片总线**：用于各芯片之间的连接。\n- **内总线**：用于微型计算机系统各插件板之间的连接\n- **外总线**：用于系统间的连接。\n\n### 高速缓存及优点\n\n- 为了解决 CPU 和主存读写速度不匹配的问题 \n- 增加容量小但速度快的**Cache**\n- Cache 采用存取速度快的 **SRAM** 构成。\n- **优点**：有利于提高系统效率，提高 CPU 利用率， 减少主存访问频率，提高访问速度，降低总线负载\n\n### 直接数据通道传送（DMA）\n\n- 由于中断传送仍由CPU 通过程序传送，中间需要多条指令，运行速度太慢，故采取硬件在外设与内存间直接进行数据交换，即DMA方式。\n- **DMA信号最小模式**：CPU 通过HOLD 引脚接收DMA 控制器的总线请求；在HLDA 引脚上发出对总线请求的允许信号。\n\n## 二、分析题\n\n### 通用寄存器及段寄存器\n\n#### 通用寄存器\n\n**AX、BX、CX、DX、SI、DI、BP、SP** \n\n**数据寄存器** —算术和逻辑操作\n\n- **AX（累加器寄存器，Accumulator Register）**\n\n  - AX是8086中最主要的寄存器，许多算术和I/O操作都默认使用它。\n\n  - 它可以被分成两个8位的寄存器：AH（高8位）和AL（低8位）。\n\n  - 典型用途：算术运算、数据传送、I/O操作。\n\n- **BX（基址寄存器，Base Register）**\n  - BX常用于存储内存地址的基地址。\n\n  - 也可以分为两个8位的寄存器：BH（高8位）和BL（低8位）。\n\n  - 典型用途：内存访问和数据传送。\n\n- **CX（计数器寄存器，Count Register）**\n\n  - CX用于计数，特别是在循环和字符串操作中。\n\n  - 可以分为两个8位的寄存器：CH（高8位）和CL（低8位）。\n\n  - 典型用途：循环计数、字符串操作（如REP指令）。\n\n- **DX（数据寄存器，Data Register）**\n\n  - DX常用于存储数据和I/O地址，特别是在涉及扩展精度的操作中。\n\n  - 也可以分为两个8位的寄存器：DH（高8位）和DL（低8位）。\n\n  - 典型用途：扩展算术操作、I/O操作。\n\n**指针和变址寄存器** —用于内存地址计算和访问\n\n- **SP（堆栈指针寄存器，Stack Pointer）**\n\n  - **SP**指向堆栈顶，用于堆栈操作。\n\n  - 它与**SS**（堆栈段寄存器）一起使用来访问堆栈。\n\n- **BP（基址指针寄存器，Base Pointer）**\n\n  - **BP**常用于基于基址的内存访问，特别是在访问堆栈帧中的数据时。\n\n  - 典型用途：参数传递和局部变量访问。\n\n- **SI（源变址寄存器，Source Index）**\n\n  - **SI**通常用于指向内存中的源数据地址。一般指向**DS**寄存器段中的数据指针、串操作的源地址\n\n  - 典型用途：字符串操作中的源地址指针。\n\n- **DI（目的变址寄存器，Destination Index）**\n  - DI通常用于指向内存中的目的数据地址。一般指向**ES**寄存器段中的数据（目标）的指针、串操作的目标指针\n\n  - 典型用途：字符串操作中的目的地址指针。\n\n#### 段寄存器\n\n![](http://1.94.5.131:8888/upload/image_20240701_235646.png)\n\n- **CS（代码段寄存器，Code Segment Register）**：\n  - 存储代码段的基址，指示当前正在执行的代码所在的段。\n  - 与IP（指令指针）寄存器一起使用来定位下一条指令的地址。\n- **DS（数据段寄存器，Data Segment Register）**：\n  - 存储数据段的基址，指示程序数据所在的段。\n  - 大多数数据操作默认使用DS寄存器。\n- **SS（堆栈段寄存器，Stack Segment Register）**：\n  - 存储堆栈段的基址，指示堆栈的起始地址。\n  - 堆栈操作使用SS和SP（堆栈指针）寄存器共同确定堆栈地址。\n- **ES（附加段寄存器，Extra Segment Register）**：\n  - 存储附加段的基址，用于指示额外的数据段。\n  - 一些字符串操作指令使用ES寄存器作为目的地址段。\n\n### 20位物理地址\n\n![](http://1.94.5.131:8888/upload/image_20240701_235707.png)\n\n比如 0BAC:0100\n\n0BAC是基地址，0100是偏移地址\n\n0BAC 4位的十六进制 表示 16位的2进制数据 转换后为：\n\n0000 1011 1010 1100  \n\n必须要转换成 20位（也就是5位的16进制） 才能在20位地址总线中传递 才能达到 1G的数据访问范围\n\n怎么转换成 20位 能让数据传递到内存中找到物理数据了\n\n内存的物理地址 =基地址*16+偏移地址\n\n内存的物理地址 =0BAC*16+0100=0BAC0+0100=0BBC0H\n\n0BBC0 就是5位的十六进制  就是 4*5=20位了 可以传递到内存了\n\n实际传递二进制就是：\n\n0000 1011 1011 1100 0000\n\n所以有了**段地址×16+偏移地址，**也就是**段地址×10H＋偏移地址**，这个逻辑地址与物理地址的转换公式。\n\n### IA-32处理器中位移指令\n\n- 指令集类型：CISC\n- 寄存器配置\n  - 8个32位通用寄存器\n  - 6个段寄存器\n  - 1个标志寄存器\n\n### 指令周期与总线周期。\n\n- 执行一条指令所需要的时间称为**指令周期**。8086中，指令周期不等长。\n- 指令周期分为一个个的总线周期。\n- **CPU**从**存储器**或I/O端口读写一个字节和字的时间就是一个总线周期。\n\n![](http://1.94.5.131:8888/upload/image_20240701_235728.png)\n\n### 多级存储体系结构\n\n- 高速缓冲存储器（**Cache**）、主存储器（内存储器）、辅助（外）存储器以及网络存储器。\n\n![](http://1.94.5.131:8888/upload/image_20240701_235741.png)\n\n  \n\n### 中断优先权\n\n计算机对多个中断请求的响应次序。\n\n- 需要优先级判定的原因\n  - 多个中断源同时发出请求\n  - 不同中断源的急切程度不一致\n- 中断优先权判别技术\n  - 软件采用查询技术，当CPU响应中断后，就用软件查询以确定具体外设情况。\n  - 硬件采用硬件编码器和比较器的优先权排队电路，编码电路为每个中断进行编号，比较电路对编号的大小进行比较，用编号大小对应优先权的高低。常用的排队电路为链式优先权排队电路\n\n### A/D转换器与微处理器的接口\n\n- A/D转换芯片与微处理器接口时需要\n  - 数据信息的传送\n  - 控制信息和状态信息的联系\n- 工作过程如下：\n  - CPU送出控制信号至A/D转换器启动端，使得A/D转换器开始转换\n  - CPU查询到转换完成后，执行输入指令将A/D转换的结果读入\n\n## 三、综合题\n\n### 指令中的源操作数和目的操作数的寻址方式，参考课后题\n\n![](http://1.94.5.131:8888/upload/image_20240701_235754.png)\n\n答案：\n\n| 序号 | 源操作数                 | 目的操作数          |\n| ---- | ------------------------ | ------------------- |\n| 1    | 300 立即数寻址           | SI 寄存器寻址       |\n| 2    | DATA[DI] 变址加偏移寻址  | CX 寄存器寻址       |\n| 3    | [BX] [SI] 基址加变址寻址 | AX 寄存器寻址       |\n| 4    | CX 寄存器寻址            | AX 寄存器寻址       |\n| 5    | AX 寄存器寻址            | [BP] 寄存器间接寻址 |\n| 6    | 堆栈操作                 |                     |\n\n\n\n### 十进制调整指令：在加法后进行十进制调整DAA；在减法后进行十进制调整DAS。', 24, '日常随笔', '微机原理,期末复习', 1, 267, 0, 0, '2024-07-01 23:58:14', '2024-07-01 23:58:14');

-- ----------------------------
-- Table structure for tb_blog_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_category`;
CREATE TABLE `tb_blog_category`  (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '分类表主键',
  `category_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '分类的名称',
  `category_icon` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '分类的图标',
  `category_rank` int NOT NULL DEFAULT 1 COMMENT '分类的排序值 被使用的越多数值越大',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog_category
-- ----------------------------
INSERT INTO `tb_blog_category` VALUES (20, 'About', '/admin/dist/img/category/10.png', 13, 0, '2018-11-12 00:28:49');
INSERT INTO `tb_blog_category` VALUES (22, 'SSM整合进阶篇', '/admin/dist/img/category/02.png', 19, 1, '2018-11-12 10:42:25');
INSERT INTO `tb_blog_category` VALUES (24, '日常随笔', '/admin/dist/img/category/06.png', 38, 0, '2018-11-12 10:43:21');
INSERT INTO `tb_blog_category` VALUES (25, 'Apple', '/admin/dist/img/category/01.png', 1, 1, '2024-05-04 16:30:59');
INSERT INTO `tb_blog_category` VALUES (26, 'Docker', '/admin/dist/img/category/11.png', 2, 0, '2024-05-05 13:46:04');

-- ----------------------------
-- Table structure for tb_blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_comment`;
CREATE TABLE `tb_blog_comment`  (
  `comment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `blog_id` bigint NOT NULL DEFAULT 0 COMMENT '关联的blog主键',
  `commentator` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '评论者名称',
  `email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '评论人的邮箱',
  `website_url` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '网址',
  `comment_body` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '评论内容',
  `comment_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论提交时间',
  `commentator_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '评论时的ip地址',
  `reply_body` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '回复内容',
  `reply_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
  `comment_status` tinyint NOT NULL DEFAULT 0 COMMENT '是否审核通过 0-未审核 1-审核通过',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog_comment
-- ----------------------------
INSERT INTO `tb_blog_comment` VALUES (22, 4, 'adaada', 'wenxiaolan02@outlook.com', '', '这是测试评论', '2024-05-05 14:41:26', '', '', '2024-05-05 14:41:51', 1, 1);
INSERT INTO `tb_blog_comment` VALUES (26, 4, '十三', '224683568@qq.com', '', '第一条评论', '2019-05-13 10:12:19', '', '这是回复内容  ，你好', '2024-05-05 14:37:34', 1, 1);
INSERT INTO `tb_blog_comment` VALUES (27, 6, '露露car', '2271707817@qq.com', '', '这是第一条评论', '2024-05-07 17:34:27', '', '这是第一条回复', '2024-05-07 17:35:41', 1, 1);
INSERT INTO `tb_blog_comment` VALUES (28, 6, '访客1', '111@qq.com', '', '这是第二条评论', '2024-05-07 18:38:33', '', '这是第二条评论', '2024-05-07 18:39:34', 1, 1);

-- ----------------------------
-- Table structure for tb_blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_tag`;
CREATE TABLE `tb_blog_tag`  (
  `tag_id` int NOT NULL AUTO_INCREMENT COMMENT '标签表主键id',
  `tag_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '标签名称',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 143 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog_tag
-- ----------------------------
INSERT INTO `tb_blog_tag` VALUES (57, '世界上有一个很可爱的人', 0, '2018-11-12 00:31:15');
INSERT INTO `tb_blog_tag` VALUES (58, '现在这个人就在看这句话', 0, '2018-11-12 00:31:15');
INSERT INTO `tb_blog_tag` VALUES (66, 'Spring', 0, '2018-11-12 10:55:14');
INSERT INTO `tb_blog_tag` VALUES (67, 'SpringMVC', 0, '2018-11-12 10:55:14');
INSERT INTO `tb_blog_tag` VALUES (68, 'MyBatis', 0, '2018-11-12 10:55:14');
INSERT INTO `tb_blog_tag` VALUES (69, 'easyUI', 1, '2018-11-12 10:55:14');
INSERT INTO `tb_blog_tag` VALUES (127, '目录', 1, '2019-04-24 15:41:39');
INSERT INTO `tb_blog_tag` VALUES (128, 'AdminLte3', 1, '2019-04-24 15:46:16');
INSERT INTO `tb_blog_tag` VALUES (130, 'SpringBoot', 0, '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag` VALUES (131, '入门教程', 0, '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag` VALUES (132, '实战教程', 1, '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag` VALUES (133, 'spring-boot企业级开发', 1, '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag` VALUES (134, '标签', 1, '2024-05-03 09:27:15');
INSERT INTO `tb_blog_tag` VALUES (135, '标签2', 1, '2024-05-03 09:27:53');
INSERT INTO `tb_blog_tag` VALUES (136, 'Thymeleaf', 1, '2024-05-05 13:54:07');
INSERT INTO `tb_blog_tag` VALUES (137, '不可评论的文章', 1, '2024-05-07 18:48:26');
INSERT INTO `tb_blog_tag` VALUES (138, '个人感想', 0, '2024-05-12 11:34:41');
INSERT INTO `tb_blog_tag` VALUES (139, '项目', 0, '2024-06-30 23:34:08');
INSERT INTO `tb_blog_tag` VALUES (140, '介绍', 0, '2024-06-30 23:34:08');
INSERT INTO `tb_blog_tag` VALUES (141, '微机原理', 0, '2024-07-01 23:58:14');
INSERT INTO `tb_blog_tag` VALUES (142, '期末复习', 0, '2024-07-01 23:58:14');

-- ----------------------------
-- Table structure for tb_blog_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_tag_relation`;
CREATE TABLE `tb_blog_tag_relation`  (
  `relation_id` bigint NOT NULL AUTO_INCREMENT COMMENT '关系表id',
  `blog_id` bigint NOT NULL COMMENT '博客id',
  `tag_id` int NOT NULL COMMENT '标签id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`relation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 307 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog_tag_relation
-- ----------------------------
INSERT INTO `tb_blog_tag_relation` VALUES (270, 4, 130, '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag_relation` VALUES (271, 4, 131, '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag_relation` VALUES (274, 3, 66, '2019-05-13 10:07:27');
INSERT INTO `tb_blog_tag_relation` VALUES (275, 3, 67, '2019-05-13 10:07:27');
INSERT INTO `tb_blog_tag_relation` VALUES (276, 3, 68, '2019-05-13 10:07:27');
INSERT INTO `tb_blog_tag_relation` VALUES (302, 1, 138, '2024-06-30 23:29:21');
INSERT INTO `tb_blog_tag_relation` VALUES (303, 8, 139, '2024-06-30 23:34:08');
INSERT INTO `tb_blog_tag_relation` VALUES (304, 8, 140, '2024-06-30 23:34:08');
INSERT INTO `tb_blog_tag_relation` VALUES (305, 9, 141, '2024-07-01 23:58:14');
INSERT INTO `tb_blog_tag_relation` VALUES (306, 9, 142, '2024-07-01 23:58:14');

-- ----------------------------
-- Table structure for tb_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_config`;
CREATE TABLE `tb_config`  (
  `config_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '配置项的名称',
  `config_value` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '配置项的值',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`config_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_config
-- ----------------------------
INSERT INTO `tb_config` VALUES ('footerAbout', 'your personal blog. have fun.', '2018-11-11 20:33:23', '2024-07-04 19:03:06');
INSERT INTO `tb_config` VALUES ('footerCopyRight', '2024 lulucar', '2018-11-11 20:33:31', '2024-07-04 19:03:06');
INSERT INTO `tb_config` VALUES ('footerICP', '蜀ICP备2024086428号-1', '2018-11-11 20:33:27', '2024-07-04 19:03:06');
INSERT INTO `tb_config` VALUES ('footerPoweredBy', 'https://github.com/luluacr2022', '2018-11-11 20:33:36', '2024-07-04 19:03:06');
INSERT INTO `tb_config` VALUES ('footerPoweredByURL', 'https://github.com/lulucar2022', '2018-11-11 20:33:39', '2024-07-04 19:03:06');
INSERT INTO `tb_config` VALUES ('websiteDescription', 'blog 由 SpringBoot3 + Mybatis + Thymeleaf 搭建的个人网站', '2018-11-11 20:33:04', '2024-05-05 17:48:28');
INSERT INTO `tb_config` VALUES ('websiteIcon', '/admin/dist/img/favicon.png', '2018-11-11 20:33:11', '2024-05-05 17:48:28');
INSERT INTO `tb_config` VALUES ('websiteLogo', '/admin/dist/img/logo.png', '2018-11-11 20:33:08', '2024-05-05 17:48:28');
INSERT INTO `tb_config` VALUES ('websiteName', 'blog of lulucar', '2018-11-11 20:33:01', '2024-05-05 17:48:28');
INSERT INTO `tb_config` VALUES ('yourAvatar', '/admin/dist/img/wechat.png', '2018-11-11 20:33:14', '2024-05-05 17:38:32');
INSERT INTO `tb_config` VALUES ('yourEmail', 'wenxiaolan02@outlook.com', '2018-11-11 20:33:17', '2024-05-05 17:38:32');
INSERT INTO `tb_config` VALUES ('yourName', 'lulucar', '2018-11-11 20:33:20', '2024-05-05 17:38:32');

-- ----------------------------
-- Table structure for tb_link
-- ----------------------------
DROP TABLE IF EXISTS `tb_link`;
CREATE TABLE `tb_link`  (
  `link_id` int NOT NULL AUTO_INCREMENT COMMENT '友链表主键id',
  `link_type` tinyint NOT NULL DEFAULT 0 COMMENT '友链类别 0-友链 1-推荐 2-个人网站',
  `link_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '网站名称',
  `link_url` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '网站链接',
  `link_description` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '网站描述',
  `link_rank` int NOT NULL DEFAULT 0 COMMENT '用于列表排序',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`link_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_link
-- ----------------------------
INSERT INTO `tb_link` VALUES (1, 0, 'tqr', 'rqwe', 'rqw', 0, 1, '2018-10-22 18:57:52');
INSERT INTO `tb_link` VALUES (2, 2, '十三的GitHub', 'https://github.com/ZHENFENG13', '十三分享代码的地方', 1, 1, '2018-10-22 19:41:04');
INSERT INTO `tb_link` VALUES (3, 2, '十三的博客', 'http://13blog.site', '个人独立博客13blog', 14, 1, '2018-10-22 19:53:34');
INSERT INTO `tb_link` VALUES (4, 1, 'CSDN 图文课', 'https://gitchat.csdn.net', 'IT优质内容平台', 6, 1, '2018-10-22 19:55:55');
INSERT INTO `tb_link` VALUES (5, 2, '十三的博客园', 'https://www.cnblogs.com/han-1034683568', '最开始写博客的地方', 17, 1, '2018-10-22 19:56:14');
INSERT INTO `tb_link` VALUES (6, 1, 'CSDN', 'https://www.csdn.net/', 'CSDN-专业IT技术社区官网', 4, 0, '2018-10-22 19:56:47');
INSERT INTO `tb_link` VALUES (7, 0, '梁桂钊的博客', 'http://blog.720ui.com', '后端攻城狮', 1, 1, '2018-10-22 20:01:38');
INSERT INTO `tb_link` VALUES (8, 0, '猿天地', 'http://cxytiandi.com', '一个综合性的网站,以程序猿用户为主,提供各种开发相关的内容', 12, 1, '2018-10-22 20:02:41');
INSERT INTO `tb_link` VALUES (9, 0, 'Giraffe Home', 'https://yemengying.com/', 'Giraffe Home', 0, 1, '2018-10-22 20:27:04');
INSERT INTO `tb_link` VALUES (10, 0, '纯洁的微笑', 'http://www.ityouknow.com', '分享技术，分享生活', 3, 1, '2018-10-22 20:27:16');
INSERT INTO `tb_link` VALUES (11, 0, 'afsdf', 'http://localhost:28080/admin/links', 'fad', 0, 1, '2018-10-22 20:27:26');
INSERT INTO `tb_link` VALUES (12, 1, 'afsdf', 'http://localhost', 'fad1', 0, 1, '2018-10-24 14:04:18');
INSERT INTO `tb_link` VALUES (13, 0, '郭赵晖', 'http://guozh.net/', '老郭三分地', 0, 1, '2019-04-24 15:30:19');
INSERT INTO `tb_link` VALUES (14, 0, 'dalaoyang', 'https://www.dalaoyang.cn/', 'dalaoyang', 0, 1, '2019-04-24 15:31:50');
INSERT INTO `tb_link` VALUES (15, 0, 'mushblog', 'https://www.sansani.cn', '穆世明博客', 0, 1, '2019-04-24 15:32:19');
INSERT INTO `tb_link` VALUES (16, 1, '实验楼', 'https://www.shiyanlou.com/', '一家专注于IT技术的在线实训平台', 17, 1, '2019-04-24 16:03:48');
INSERT INTO `tb_link` VALUES (17, 2, '《SSM 搭建精美实用的管理系统》', 'https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9', 'Spring+SpringMVC+MyBatis实战课程', 18, 1, '2019-04-24 16:06:52');
INSERT INTO `tb_link` VALUES (18, 2, '《Spring Boot 入门及前后端分离项目实践》', 'https://www.shiyanlou.com/courses/1244', 'SpringBoot实战课程', 19, 1, '2019-04-24 16:07:27');
INSERT INTO `tb_link` VALUES (19, 2, '《玩转Spring Boot 系列》', 'https://www.shiyanlou.com/courses/1274', 'SpringBoot实战课程', 20, 1, '2019-04-24 16:10:30');
INSERT INTO `tb_link` VALUES (20, 1, '博客园', 'https://www.cnblogs.com', '最有价值中文博客网站', 0, 1, '2024-05-05 16:31:14');

-- ----------------------------
-- Table structure for tb_test
-- ----------------------------
DROP TABLE IF EXISTS `tb_test`;
CREATE TABLE `tb_test`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `test_info` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '测试内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_test
-- ----------------------------
INSERT INTO `tb_test` VALUES (1, 'SpringBoot-MyBatis测试');

SET FOREIGN_KEY_CHECKS = 1;
