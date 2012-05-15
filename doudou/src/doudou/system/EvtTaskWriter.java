package doudou.system;

import java.util.concurrent.LinkedBlockingQueue;

import doudou.service.DoudouBackendService;
import doudou.vo.model.EvtPublishTask;

import org.apache.log4j.Logger;

public class EvtTaskWriter implements Runnable{

	private Logger logger = Logger.getLogger(getClass());
	private LinkedBlockingQueue<EvtPublishTask> queue;
	private DoudouBackendService doudouBackendService = DoudouBackendService.getInstance();
	
	public EvtTaskWriter(LinkedBlockingQueue<EvtPublishTask> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		try {
			EvtPublishTask task = null;			
			while(true){
				task = queue.take();
				writeToDB(task);
				logger.debug(String.format("Wrote task...eventID: %d, to db", task.getEvent().getId()));							
			}			
		} catch (InterruptedException e) {
			logger.error("TaskWriter is going to be terminated for caught of InterruptedException exception",e);
		}
	}
	
	private void writeToDB(EvtPublishTask task) {
		//doudouBackendService.publishTask(task);
	}

}
