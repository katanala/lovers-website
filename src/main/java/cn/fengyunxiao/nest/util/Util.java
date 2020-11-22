package cn.fengyunxiao.nest.util;

import java.util.UUID;

public class Util {

    public static boolean isBlank(String str) {
        return str == null || str.trim().equals("");
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
    }
}
