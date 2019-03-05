package cn.fengyunxiao.nest.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import javax.servlet.http.HttpServletRequest;
import cn.fengyunxiao.nest.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.JsonNode;
import cn.fengyunxiao.nest.entity.Ip;

public class IpUtil {
	private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);
	
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
			scanner = new Scanner(inputStream, "UTF-8");
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
			tempip.setRegion( split[3] );
			tempip.setCity( split[5] );
			tempip.setIsp( split[9] );

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
					logger.error("inputStream.close()");
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
			scanner = new Scanner(inputStream, "UTF-8");
			StringBuilder builder = new StringBuilder();

			while (scanner.hasNextLine()) {
				builder.append(scanner.nextLine());
			}
			
			tree = Jackson.getObjectMapper().readTree(builder.toString());
			
			if ("0".equals(tree.path("code").asText("0"))) {
				JsonNode data = tree.path("config");
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
					logger.error("inputStream.close()");
				}
			}
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
	}
	
	// 判断ip是否8分钟内记录过
	public static boolean isSafeIp(String ip) {
		// 从临时黑名单删除5分钟内没有操作过的Ip，这些是安全ip
		long nowTime = System.currentTimeMillis();
		Iterator<Entry<String, Long>> iterator = Config.RECORD_IP_ADDR.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Long> next = iterator.next();
			Long oldtime = next.getValue();
			if (nowTime - oldtime > 480*1000) {
				iterator.remove();
			}
		}
		
		// 检测当前的ip
		Long oldtime = Config.RECORD_IP_ADDR.get(ip);
		// 如果没有记录过Ip，表示没有操作过，放行。否则是3s内操作过，阻止。
		if (oldtime == null) {
			Config.RECORD_IP_ADDR.put(ip, System.currentTimeMillis());
			return true;
		} else {
			return false;
		}
	}
}
