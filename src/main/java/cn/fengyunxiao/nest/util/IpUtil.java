package cn.fengyunxiao.nest.util;

import cn.fengyunxiao.nest.entity.Ip;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class IpUtil {
    private final static Logger log = LoggerFactory.getLogger(IpUtil.class);

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    public static Ip getIpAddrByIpIp(String ip) {
        Scanner scanner = null;
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL("http://freeapi.ipip.net/" + ip);
            urlConnection = (HttpURLConnection) url.openConnection();

            inputStream = urlConnection.getInputStream();
            scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
            StringBuilder builder = new StringBuilder();

            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
            }

            String result = builder.toString();
            if (!result.startsWith("[")) {
                return null;
            }

            // ["中国","天津","天津","","电信/联通/移动"]
            String[] split = result.split("\"");
            if (split.length != 11) {
                return null;
            }

            Ip tempip = new Ip();
            tempip.setIp(ip);
            tempip.setRegion(split[3]);
            tempip.setCity(split[5]);
            tempip.setIsp(split[9]);

            return tempip;
        } catch (Exception e) {
            return null;
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("inputStream.close()");
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    public static Ip getIpAddrByAli(String ip) {
        Scanner scanner = null;
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        JsonNode tree;

        try {
            URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);// 使用的IP库是淘宝IP库
            urlConnection = (HttpURLConnection) url.openConnection();

            inputStream = urlConnection.getInputStream();
            scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
            StringBuilder builder = new StringBuilder();

            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
            }

            tree = Jackson.getObjectMapper().readTree(builder.toString());

            if ("0".equals(tree.path("code").asText("0"))) {
                JsonNode data = tree.path("data");
                Ip tempip = new Ip();
                tempip.setIp(ip);
                tempip.setCity(data.path("city").asText(""));
                tempip.setIsp(data.path("isp").asText(""));
                tempip.setRegion(data.path("region").asText(""));
                return tempip;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("inputStream.close()");
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

}
