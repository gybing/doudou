package doudou.system;

import java.util.concurrent.LinkedBlockingQueue;

import doudou.util.DoudouImgUtil;

import org.apache.log4j.Logger;

public class ImageProcessor implements Runnable {
	private Logger logger = Logger.getLogger(getClass());
	
	private LinkedBlockingQueue<String> imgQueue;
	private DoudouImgUtil doudouImgUtil = new DoudouImgUtil(350);
	
	public ImageProcessor(LinkedBlockingQueue<String> imgQueue) {
		this.imgQueue = imgQueue;
	}


	@Override
	public void run() {
		try {
			String imgSrcPath = null;			
			while(true){
				imgSrcPath = imgQueue.take();
				doudouImgUtil.process(imgSrcPath);
				logger.debug(String.format("Image Processor...image path: %s", imgSrcPath));							
			}			
		} catch (InterruptedException e) {
			logger.error("ImageProcessor is going to be terminated for caught of InterruptedException exception",e);
		}
	}
}
