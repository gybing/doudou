package doudou.system;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import doudou.util.DoudouImgUtil;

public class HeadPicProcessor implements Runnable {
	private Logger logger = Logger.getLogger(getClass());
	
	private LinkedBlockingQueue<String> imgQueue;
	private DoudouImgUtil mayayaImgUtil = new DoudouImgUtil(150);
	
	public HeadPicProcessor(LinkedBlockingQueue<String> imgQueue) {
		this.imgQueue = imgQueue;
	}


	@Override
	public void run() {
		try {
			String imgSrcPath = null;			
			while(true){
				imgSrcPath = imgQueue.take();
				mayayaImgUtil.process(imgSrcPath);
				logger.debug(String.format("Image Processor...image path: %s", imgSrcPath));							
			}			
		} catch (InterruptedException e) {
			logger.error("ImageProcessor is going to be terminated for caught of InterruptedException exception",e);
		}
	}
}
