package doudou.system;

import java.util.concurrent.LinkedBlockingQueue;

import doudou.service.DoudouBackendService;
import doudou.vo.model.MessagePubTask;

import org.apache.log4j.Logger;

public class MessageTaskWriter implements Runnable{

	private Logger logger = Logger.getLogger(getClass());
	private LinkedBlockingQueue<MessagePubTask> queue;
	private DoudouBackendService doudouBackendService = DoudouBackendService.getInstance();
	
	public MessageTaskWriter(LinkedBlockingQueue<MessagePubTask> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		try {
			MessagePubTask task = null;			
			while(true){
				task = queue.take();
				writeToDB(task);
				logger.debug(String.format("Wrote task...eventID: %d, to db", task.getMessage().getId()));							
			}			
		} catch (InterruptedException e) {
			logger.error("TaskWriter is going to be terminated for caught of InterruptedException exception",e);
		}
	}
	
	private void writeToDB(MessagePubTask task) {
		//doudouBackendService.publishTask(task);
	}

}
