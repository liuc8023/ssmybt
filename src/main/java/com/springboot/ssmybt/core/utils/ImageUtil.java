
/**
* 文件名：ImageUtil.java
*
* 版本信息：
* 日期：2018年3月6日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
*
* 项目名称：ssmybt
* 类名称：ImageUtil
* 类描述：验证码生成工具
* 创建人：liuc
* 创建时间：2018年3月6日 上午10:49:45
* 修改人：liuc
* 修改时间：2018年3月6日 上午10:49:45
* 修改备注：
* @version
*
*/
public final class ImageUtil {
	private InputStream image;// 图像
	private static final int SIZE = 4;
	private String str;// 验证码
	private static final int WIDTH = 80;
	private static final int HEIGHT = 30;

	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 上午10:51:00
	* @Description: 获取一个验证码类的实例
	* @return ImageUtil    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static ImageUtil Instance() {
		return new ImageUtil();
	}

	public static Map<String, BufferedImage> createImage() {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Map<String, BufferedImage> map = null;
		int randomNum = new Random().nextInt(3);
		if (randomNum == 0) {
			map = initNumVerificationCode(image);
		} else if (randomNum == 1) {
			map = initLetterAndNumVerificationCode(image);
		} else {
			map = initChinesePlusNumVerificationCode(image);
		}
		return map;
	}

	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 上午10:51:17
	* @Description: 设置第一种验证码的属性
	* @return Map<String,BufferedImage>    返回类型
	* @param @param image
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static Map<String, BufferedImage> initNumVerificationCode(BufferedImage image) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random(); // 生成随机类
		Graphics g = initImage(image, random);
		for (int i = 0; i < SIZE; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sb.append(rand); /* 赋值验证码 */
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 13 * i + 6, 16);
		}
		// 图象生效
		g.dispose();
		Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
		map.put(sb.toString(), image);
		return map;
	}

	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 上午10:51:34
	* @Description: 设置第二种验证码属性
	* @return Map<String,BufferedImage>    返回类型
	* @param @param image
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static Map<String, BufferedImage> initLetterAndNumVerificationCode(BufferedImage image) {

		Random random = new Random(); // 生成随机类
		Graphics g = initImage(image, random);
		String[] letter = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < SIZE; i++) {
			String tempRand = "";
			if (random.nextBoolean()) {
				tempRand = String.valueOf(random.nextInt(10));
			} else {
				tempRand = letter[random.nextInt(25)];
				if (random.nextBoolean()) {// 随机将该字母变成小写
					tempRand = tempRand.toLowerCase();
				}
			}
			sb.append(tempRand);/* 赋值验证码 */
			g.setColor(new Color(20 + random.nextInt(10), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(tempRand, 13 * i + 6, 16);
		}
		// 图象生效
		g.dispose();
		Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
		map.put(sb.toString(), image);
		return map;

	}

	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 上午10:51:49
	* @Description: 设置第三种验证码属性 即：肆+？=24
	* @return Map<String,BufferedImage>    返回类型
	* @param @param image
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static Map<String, BufferedImage> initChinesePlusNumVerificationCode(BufferedImage image) {
		String[] cn = { "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾" };
		StringBuffer sb = new StringBuffer();
		Random random = new Random(); // 生成随机类
		Graphics g = initImage(image, random);
		int x = random.nextInt(10) + 1;
		int y = random.nextInt(30);
		sb.append(String.valueOf(y));/* 赋值验证码 */
		g.setFont(new Font("楷体", Font.PLAIN, 14));// 设定字体
		g.setColor(new Color(20 + random.nextInt(10), 20 + random.nextInt(110), 20 + random.nextInt(110)));

		g.drawString(cn[x - 1], 1 * 1 + 6, 16);
		g.drawString("+", 22, 16);
		g.drawString("？", 35, 16);
		g.drawString("=", 48, 16);
		g.drawString(String.valueOf(x + y), 61, 16);
		// 图象生效
		g.dispose();
		Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
		map.put(sb.toString(), image);
		return map;
	}

	@SuppressWarnings("unused")
	private void setImage(InputStream image) {
		this.image = image;
	}

	public static Graphics initImage(BufferedImage image, Random random) {
		Graphics g = image.getGraphics(); // 获取图形上下文
		g.setColor(getRandColor(200, 250));// 设定背景色
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 14));// 设定字体
		g.setColor(getRandColor(160, 200)); // 随机产生165条干扰线，使图象中的认证码不易被其它程序探测到
		for (int i = 0; i < 165; i++) {
			int x = random.nextInt(WIDTH);
			int y = random.nextInt(HEIGHT);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		return g;
	}

	public static InputStream drawImage(BufferedImage image) {
		ByteArrayInputStream input = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
			ImageIO.write(image, "JPEG", imageOut);
			imageOut.close();
			input = new ByteArrayInputStream(output.toByteArray());
		} catch (Exception e) {
			System.out.println("验证码图片产生出现错误：" + e.toString());
		}
		return input;
	}

	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 上午10:52:08
	* @Description: 给定范围获得随机颜色
	* @return Color    返回类型
	* @param @param fc
	* @param @param bc
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 上午10:52:22
	* @Description: 获取验证码的字符串值
	* @return String    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public String getVerificationCodeValue() {
		return this.getStr();
	}

	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 上午10:52:36
	* @Description: 取得验证码图片
	* @return InputStream    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public InputStream getImage() {
		return this.image;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
}
