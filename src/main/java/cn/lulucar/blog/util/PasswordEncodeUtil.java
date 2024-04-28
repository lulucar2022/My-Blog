package cn.lulucar.blog.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author wenxiaolan
 * @ClassName PasswordEncodeUtil
 * @date 2024/4/21 13:07
 */
public class PasswordEncodeUtil {
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public static String encode(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }
}
