package doudou.system;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import doudou.service.DoudouBackendService;
import doudou.vo.model.PicPublishTask;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.caucho.services.server.Service;

public class PicTaskWriter implements Runnable {

	private Logger logger = Logger.getLogger(getClass());
	private LinkedBlockingQueue<PicPublishTask> queue;

	private DoudouBackendService doudouBackendService = DoudouBackendService.getInstance();
	
	public PicTaskWriter(LinkedBlockingQueue<PicPublishTask> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		
		try {
			PicPublishTask task = null;			
			while(true){
				task = queue.take();
				writeToDB(task);
				logger.debug(String.format("Wrote task...pictureID: %d, to db", task.getPicture().getId()));							
			}			
		} catch (InterruptedException e) {
			logger.error("TaskWriter is going to be terminated for caught of InterruptedException exception",e);
		}
	}
	
	private void writeToDB(PicPublishTask task) {
		//doudouBackendService.publishTask(task);
	}

}
