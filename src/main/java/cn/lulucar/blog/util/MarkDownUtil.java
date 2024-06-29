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
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class MarkDownUtil {
    /**
     * 转换md格式为html
     *
     * @param markdownString md文本
     * @return 返回HTML文本
     */
    public static String mdToHtml(String markdownString) {
        if (!StringUtils.hasText(markdownString)) {
            return "";
        }
        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(markdownString);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        return renderer.render(document);
    }

    /**
     * 截取博客文章作摘要
     * @param markdownString
     * @return
     */
    public static String mdToHtmlForSummary(String markdownString) {
        String html = mdToHtml(markdownString);
        Document document = Jsoup.parse(html);
        Elements paragraphs = document.select("p");
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
        return sb.toString();
    }

    
}
