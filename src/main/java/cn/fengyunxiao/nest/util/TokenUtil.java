package cn.fengyunxiao.nest.util;

import cn.fengyunxiao.nest.config.Config;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class TokenUtil {

    // 传入的 sign = sha1 ( md5( md5(token) + token ) + '157369936' )
    // 157369936 有三个值：当前 10 秒 157369936，前 10 秒 157369936-1，后 10 秒 157369936+1
    public static boolean isRightSign(String sign) {
        Long time = System.currentTimeMillis() / 10000;

        // 当前时间是否正确
        String md5Sign = getMd5(Config.adminLoginToken + time.toString());
        String sign1 = getSha1(md5Sign + time.toString());
        if (sign1.equals(sign)) {
            return true;
        }

        // 前 10 秒
        time = time - 1;
        md5Sign = getMd5(Config.adminLoginToken + time.toString());
        String sign2 = getSha1(md5Sign + time.toString());
        if (sign2.equals(sign)) {
            return true;
        }

        // 后 10 秒
        time = time + 2;
        md5Sign = getMd5(Config.adminLoginToken + time.toString());
        String sign3 = getSha1(md5Sign + time.toString());
        if (sign3.equals(sign)) {
            return true;
        }

        return false;
    }

    public static String getMd5(String dataStr) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes(StandardCharsets.UTF_8));
            byte s[] = m.digest();
            StringBuffer builder = new StringBuffer();
            for (int i = 0; i < s.length; i++) {
                builder.append(Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6));
            }
            return builder.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getSha1(String string) {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
            byte[] byteArray = string.getBytes(StandardCharsets.UTF_8);
            byte[] md5Bytes = sha.digest(byteArray);

            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            return "";
        }
    }

}