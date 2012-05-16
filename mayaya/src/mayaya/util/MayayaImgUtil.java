package mayaya.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import mayaya.util.tool.ImageUtil;

import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class MayayaImgUtil {
	
	private HashMap<String, Integer> compressSizeList;
	
	private static final Logger logger = Logger.getLogger(ImageUtil.class);
		
	public MayayaImgUtil(int smallSize) {
		compressSizeList = new HashMap<String,Integer>();
		compressSizeList.put("small", smallSize);
	}
	
//	public static void main(String[] args) {
//		//new MayayaImgUtil(500).process("C:/Users/admin/Downloads/Pic_20120316230128.jpg");
//		//new MayayaImgUtil(500).process("d:/test.jpg");
//		new MayayaImgUtil(500).process("C:/Users/admin/Desktop/b.JPG");
//	}
	
	public void process(String srcImageFile) {
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度

			int destLength = srcWidth > srcHeight ? srcHeight : srcWidth;
			int x = (srcWidth - destLength)/2;
			int y = (srcHeight - destLength)/2;
			logger.info(String.format("ImgUtil -----> picUrl: %s,srcWidth %d,srcHeight:%d",srcImageFile,srcWidth,srcHeight));
			System.out.println(String.format("ImgUtil -----> picUrl: %s,srcWidth %d,srcHeight:%d",srcImageFile,srcWidth,srcHeight));
			cropFilter = new CropImageFilter(x, y, destLength, destLength);
			img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(bi.getSource(), cropFilter));
			
			BufferedImage tag = new BufferedImage(destLength, destLength,BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, null); // 绘制截取后的图
			g.dispose();
			
			logger.debug("ImgUtil -----> Cut finished");
			
			// 压缩
			for (String s : compressSizeList.keySet()) {
				int length = compressSizeList.get(s);
				
				Image tmpResult = tag.getScaledInstance(length, length, Image.SCALE_AREA_AVERAGING);
				BufferedImage mBufferedImage = new BufferedImage(length, length,BufferedImage.TYPE_INT_RGB);
				Graphics2D g2 = mBufferedImage.createGraphics();

				g2.drawImage(tmpResult, 0, 0, length, length, Color.white, null);
				g2.dispose();

				float[] kernelData2 = { -0.125f, -0.125f, -0.125f, -0.125f, 2,
						-0.125f, -0.125f, -0.125f, -0.125f };
				Kernel kernel = new Kernel(3, 3, kernelData2);
				ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
				mBufferedImage = cOp.filter(mBufferedImage, null);
				
				String targetImageFile = getPictureSmallURL(srcImageFile,s);
				
				File target = new File(targetImageFile);
				File targetDir = target.getParentFile();
				if (!targetDir.exists())
					targetDir.mkdirs();

				// 输出为文件
				FileOutputStream out = new FileOutputStream(targetImageFile);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(mBufferedImage);
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Content -> "small", "middle"
	public String getPictureSmallURL(String srcPath, String content) {
		StringBuffer result = new StringBuffer(); 
		String[] strs = srcPath.split("[.]");
		for (int i = 0 ; i < strs.length - 1 ; i++) {
			result.append(strs[i] + ".");
		}
		result.deleteCharAt(result.length()-1);
		result.append("_"+content+".");
		result.append(strs[strs.length-1]);
		return result.toString();
	}
}
