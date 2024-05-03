package cn.lulucar.blog.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wenxiaolan
 * @ClassName FileNameGenerator
 * @date 2024/5/3 9:37
 */
public class FileNameGenerator {
    public static String generateNewFileName(String originalFileName) {
        // 去除原始文件后缀
        String fileNameWithoutExtension = originalFileName.substring(0,originalFileName.indexOf("."));
        // 文件后缀
        String suffixName = originalFileName.substring(originalFileName.indexOf("."));
        // 生成时间戳
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        
        // 将文件名（不带后缀）与时间戳合并

        return fileNameWithoutExtension + "_" + timestamp + suffixName;
    }
}
