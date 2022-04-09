package cn.chuanwise.panda.test;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
    
    @Test
    void testPattern() {
        final Pattern pattern = Pattern.compile("[A-Za-z ]+");
        final String input = "草方块（Grass Block）是一种在主世界地表大量生成的方块（Block）。";
        final Matcher matcher = pattern.matcher(input);
    
        while (matcher.find()) {
            final int start = matcher.start();
            final int end = matcher.end();
            final String keyword = input.substring(start, end);
            System.out.println("关键字：" + keyword + ", 位于 " + start + " - " + end);
        }
    }
}
