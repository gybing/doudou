package doudou.system;

import java.util.concurrent.LinkedBlockingQueue;

import doudou.service.DoudouBackendService;
import doudou.vo.model.MsgPublishTask;

import org.apache.log4j.Logger;

public class MessageTaskWriter implements Runnable{

	private Logger logger = Logger.getLogger(getClass());
	private LinkedBlockingQueue<MsgPublishTask> queue;
	private DoudouBackendService doudouBackendService = DoudouBackendService.getInstance();
	
	public MessageTaskWriter(LinkedBlockingQueue<MsgPublishTask> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		try {
			MsgPublishTask task = null;			
			while(true){
				task = queue.take();
				writeToDB(task);
				logger.debug(String.format("Wrote task...eventID: %d, to db", task.getMessage().getId()));							
			}			
		} catch (InterruptedException e) {
			logger.error("TaskWriter is going to be terminated for caught of InterruptedException exception",e);
		}
	}
	
	private void writeToDB(MsgPublishTask task) {
		//doudouBackendService.publishTask(task);
	}

}
