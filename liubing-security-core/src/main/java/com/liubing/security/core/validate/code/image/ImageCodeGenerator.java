package com.liubing.security.core.validate.code.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.liubing.security.core.properties.ImageCodeProperties;
import com.liubing.security.core.properties.SecurityProperties;
import com.liubing.security.core.validate.code.ValidateCodeGenerator;

public class ImageCodeGenerator implements ValidateCodeGenerator {

	/**
	 * 系统配置
	 */
	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public ImageCode generate(ServletWebRequest request) {
		ImageCodeProperties imageCodeProperties = securityProperties.getCode().getImage();
		int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", imageCodeProperties.getWidth());
		int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height",
				imageCodeProperties.getHeight());
		int length = ServletRequestUtils.getIntParameter(request.getRequest(), "length",
				imageCodeProperties.getLength());
		int expireIn = ServletRequestUtils.getIntParameter(request.getRequest(), "expireIn",
				imageCodeProperties.getExpireIn());

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		Random random = new Random();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		String sRand = "";
		for (int i = 0; i < length; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 16);
		}

		g.dispose();

		return new ImageCode(image, sRand, expireIn);
	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

}
