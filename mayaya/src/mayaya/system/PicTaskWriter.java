package mayaya.system;

import java.util.concurrent.LinkedBlockingQueue;

import mayaya.service.MayayaService;
import mayaya.service.impl.MayayaServiceImpl;
import mayaya.vo.model.PicPublishTask;

import org.apache.log4j.Logger;

public class PicTaskWriter implements Runnable {

	private Logger logger = Logger.getLogger(getClass());
	private LinkedBlockingQueue<PicPublishTask> queue;
	
	private MayayaService mayayaService;
	
	public PicTaskWriter(LinkedBlockingQueue<PicPublishTask> queue) {
		this.queue = queue;
		mayayaService = MayayaServiceImpl.getInstance();
	}
	
	@Override
	public void run() {
		try {
			PicPublishTask task = null;			
			while(true){
				task = queue.take();
				writeToDB(task);
				logger.debug(String.format("Wrote task...pictureID: %d, to db", task.getPicture().getPictureID()));							
			}			
		} catch (InterruptedException e) {
			logger.error("TaskWriter is going to be terminated for caught of InterruptedException exception",e);
		}
	}
	
	private void writeToDB(PicPublishTask task) {
		mayayaService.publishTask(task);
	}

}
