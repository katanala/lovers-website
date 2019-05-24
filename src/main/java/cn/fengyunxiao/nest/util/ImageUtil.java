package cn.fengyunxiao.nest.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import javax.imageio.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.fengyunxiao.nest.config.Config;
import com.aliyun.oss.OSSClient;

public class ImageUtil {

	/**
	 * 压缩图片
	 * @param inputStream 原文件
	 * @param file         压缩后保存位置
	 * @param maxSize      最大边长
	 * @param quality      压缩质量
	 */
	public static void zipImage(InputStream inputStream, File file, float maxSize, float quality)
			throws IOException {
		BufferedImage srcFile = ImageIO.read(inputStream);
		int width = srcFile.getWidth();
		int height = srcFile.getHeight();

		// 按比例缩放图标，不能超过 maxSize
		if (width > maxSize || height > maxSize) {
			if (width > height) {
				float bit = maxSize / width;
				width = (int)(width * bit);
				height = (int)(height * bit);
			} else {
				float bit = maxSize / height;
				width = (int)(width * bit);
				height = (int)(height * bit);
			}
		}
		
		/** 宽,高设定 */
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(srcFile, 0, 0, width, height, null);

		/** 压缩质量 */
		ImageWriter imageWriter = ImageIO.getImageWritersByFormatName("jpeg").next();
		ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
		imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		imageWriteParam.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
		imageWriteParam.setCompressionQuality(quality);
		ColorModel colorModel = srcFile.getColorModel();
		imageWriteParam.setDestinationType(new ImageTypeSpecifier(colorModel,colorModel.createCompatibleSampleModel(16, 16)));

		/** 存放位置 */
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		imageWriter.reset();
		imageWriter.setOutput(ImageIO.createImageOutputStream(fileOutputStream));
		imageWriter.write(null, new IIOImage(image, null, null), imageWriteParam);

		fileOutputStream.flush();
		fileOutputStream.close();
	}

	public static String getSuffix(String name) {
		if (name == null) {
			return null;
		}
		int lastDot = name.lastIndexOf(".");

		if (lastDot != -1) { // 有后缀名，例如 .txt .jpg
			return name.substring(lastDot);
		}

		return null;
	}

	/**
	 * 图片随机名：根据传过来的 calendar，获取时间相关的随机名
	 *
	 * 月日时分秒 + 随机2位数
	 */
	public static String getNameByTime() {
		Random random = new Random(System.currentTimeMillis());

		DateFormat dateFormat = new SimpleDateFormat("yyMMdd_HHmmss_SSS");
		String format = dateFormat.format(System.currentTimeMillis());

		return format + random.nextInt(9);
	}

	public static void uploadAliOss(String name, InputStream input) {
		OSSClient ossClient = new OSSClient(Config.OSS_PROTOCOL+Config.OSS_ENDPOINT,
				Config.OSS_ACCESS_KEY_ID, Config.OSS_ACCESS_KEY_SECRET);
		// 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
		ossClient.putObject(Config.OSS_BUCKET_NAME, name, input);
		ossClient.shutdown();
	}

	public static boolean aliOssExist(String name) {
		OSSClient ossClient = new OSSClient(Config.OSS_PROTOCOL+Config.OSS_ENDPOINT,
				Config.OSS_ACCESS_KEY_ID, Config.OSS_ACCESS_KEY_SECRET);
		return ossClient.doesObjectExist(Config.OSS_BUCKET_NAME, name);
	}

	public static void aliOssDelete(String name) {
		OSSClient ossClient = new OSSClient(Config.OSS_PROTOCOL+Config.OSS_ENDPOINT,
				Config.OSS_ACCESS_KEY_ID, Config.OSS_ACCESS_KEY_SECRET);
		ossClient.deleteObject(Config.OSS_BUCKET_NAME, name);
	}

	public static String getAliOssUrl(String fileName) {
		return Config.OSS_URL_PREFIX + fileName;
	}

	public static void getVeri(HttpServletRequest request, HttpServletResponse response, String veriname) throws IOException {
		int width = 120;
		int height = 30;

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		// backgroud
		g.setColor(new Color(249, 249, 249));
		g.fillRect(0, 0, width, height);

		// setBorder(g);
		g.setColor(new Color(210, 209, 205));
		g.drawRect(1, 1, width - 2, height - 2);

		//drawRandomLine(g);
		g.setColor(Color.GRAY);
		for (int i = 0; i < 5; i++) {
			int x1 = new Random().nextInt(width);
			int y1 = new Random().nextInt(height);
			int x2 = new Random().nextInt(width);
			int y2 = new Random().nextInt(height);
			g.drawLine(x1, y1, x2, y2);
		}

		String random = drawRandomNum((Graphics2D) g);
		request.getSession().setAttribute(veriname, random);
		response.setContentType("image/jpeg");
		response.setDateHeader("expries", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		ImageIO.write(bi, "jpg", response.getOutputStream());
	}

	private static String drawRandomNum(Graphics2D g) {
		StringBuilder sb = new StringBuilder();
		g.setFont(new Font("cmr10", Font.BOLD, 20));
		String base = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
		int x = 5;
		for (int i = 0; i < 4; i++) {
			int R = new Random().nextInt(256);
			int G = new Random().nextInt(256);
			int B = new Random().nextInt(256);
			g.setColor(new Color(R, G, B));
			int degree = new Random().nextInt() % 30;
			String ch = base.charAt(new Random().nextInt(base.length())) + "";
			sb.append(ch);
			g.rotate(degree * Math.PI / 180, x, 20);
			g.drawString(ch, x, 20);
			g.rotate(-degree * Math.PI / 180, x, 20);
			x += 30;
		}
		return sb.toString();
	}
}
