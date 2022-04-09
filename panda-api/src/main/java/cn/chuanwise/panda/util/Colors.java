package cn.chuanwise.panda.util;

import cn.chuanwise.common.util.Preconditions;
import cn.chuanwise.common.util.StaticUtilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 颜色字符工具类
 *
 * @author Chuanwise
 */
public class Colors
    extends StaticUtilities {
    
    public static final String COLOR_HEAD = "§";
    protected static final Pattern HEX_COLOR_PATTERN = Pattern.compile("#([A-Fa-f0-9]{6})");
    
    /**
     * 翻译十六进制颜色字符（#xxxxx）
     *
     * @param message 带十六进制颜色字符的消息
     * @return 翻译后的消息
     */
    public static String translateHexColorCodes(String message) {
        Matcher matcher = HEX_COLOR_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 32);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_HEAD + "x"
                    + COLOR_HEAD + group.charAt(0) + COLOR_HEAD + group.charAt(1)
                    + COLOR_HEAD + group.charAt(2) + COLOR_HEAD + group.charAt(3)
                    + COLOR_HEAD + group.charAt(4) + COLOR_HEAD + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
    }

    protected static final Pattern ORIGINAL_COLOR_PATTERN = Pattern.compile("[&](?<center>.)");
    
    /**
     * 翻译原版颜色字符
     *
     * @param message 带原版颜色字符的消息
     * @return 翻译后的消息
     */
    public static String translateOriginalColorCodes(String message) {
        Matcher matcher = ORIGINAL_COLOR_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer(message);
        while (matcher.find()) {
            buffer.replace(matcher.start(), matcher.end(), COLOR_HEAD + matcher.group("center"));
        }
        return buffer.toString();
    }
    
    /**
     * 翻译十六进制颜色字符和原版颜色字符
     *
     * @param message 带十六进制颜色字符或原版颜色字符的消息
     * @return 翻译后的消息
     */
    public static String translateColorCodes(String message) {
        return translateOriginalColorCodes(translateHexColorCodes(message));
    }
    
    /**
     * 清除所有颜色字符
     *
     * @param message 带颜色字符的消息
     * @return 清除后的消息
     */
    public static String clearColors(String message) {
        return message.replaceAll("[&§].", "");
    }
}
