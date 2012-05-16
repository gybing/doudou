package mayaya.util.tool;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageCutter {

	public static void abscut(String srcImageFile, int x, int y, int destWidth,
			int destHeight, int finalWidth, int finalHeight) {
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度

			System.out.println("srcWidth= " + srcWidth + "\tsrcHeight= "
					+ srcHeight);
			if (srcWidth >= destWidth && srcHeight >= destHeight) {
				Image image = bi.getScaledInstance(finalWidth, finalHeight,
						Image.SCALE_DEFAULT);// 获取缩放后的图片大小
				cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
				img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(destWidth, destHeight,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null); // 绘制截取后的图
				g.dispose();
				// 输出为文件
				ImageIO.write(tag, "JPEG", new File(srcImageFile));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		abscut("d:/picture/Test.jpg", 100, 50, 100, 100, 300, 600);
		System.out.println("切割成功");
	}
}
