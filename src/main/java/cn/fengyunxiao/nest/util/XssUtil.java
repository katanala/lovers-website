package cn.fengyunxiao.nest.util;

public class XssUtil {

    // html 英文字符过滤成中文
    public static String replaceHtmlToFull(String chars) {
        if (chars == null || chars.trim().isEmpty()) {
            return null;
        }

        chars = chars.replace("<","＜");
        chars = chars.replace(">","＞");
        chars = chars.replace("&","﹠");
        chars = chars.replace("\"","＂");
        chars = chars.replace("/","／");
        chars = chars.replace("\\","＼");
        return chars;
    }

    // 将 html 标签进行转义
    public static String replaceHtmlToEsc(String chars) {
        if (chars == null) {
            return "";
        }

        chars = chars.replace("<","&lt;");
        chars = chars.replace(">","&gt;");
        return chars;
    }
}
