package cn.lulucar.blog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wenxiaolan
 * @ClassName MarkDownUtilTest
 * @date 2024/5/15 11:13
 */
class MarkDownUtilTest {

    @Test
    void mdToHtml() {
        
        
    }

    @Test
    void mdToHtmlForSummary() {
        String markdownString = "## About me\n" +
                "\n" +
                "lulucar\n" +
                "\n" +
                "希望你能坚持每天都学习一些知识。\n" +
                "明日复明日，明日何其多。不要想当然地以为今天放松一下，还有明天。这样只会越来越懒惰。\n" +
                "每天都要逼自己一把，按照规划进行学习。\n" +
                "## Contact\n" +
                "\n" +
                "- 我的邮箱：wenxiaolan02@outlook.com\n" +
                "\n" +
                "## Quote\n" +
                "\n" +
                "> 学而不思则罔，思而不学则殆。\n" +
                "\n";
        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(markdownString);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        String render = renderer.render(document);
        
        Document doc = Jsoup.parse(render);
        Elements paragraphs = doc.select("p");
        // 使用 StringBuilder 来拼接p标签的文本内容
        StringBuilder sb = new StringBuilder();
        // 截取前3个p标签，如果没有三个，就截取所有p标签
        int maxToSelect = Math.min(paragraphs.size(),3);

        for (int i = 0; i < maxToSelect; i++) {
            Element paragraph = paragraphs.get(i);
            String text = paragraph.text();
            sb.append(text);
            // 可以在这里添加分隔符，比如换行符或空格  
            if (i < maxToSelect - 1) {
                sb.append("\n"); // 或者使用其他分隔符，比如sb.append(" ");  
            }
        }
        System.out.println(sb.toString());
    }
}