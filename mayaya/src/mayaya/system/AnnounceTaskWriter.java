package mayaya.system;

import java.util.concurrent.LinkedBlockingQueue;

import mayaya.service.MayayaService;
import mayaya.service.impl.MayayaServiceImpl;
import mayaya.vo.model.AnnouncePubTask;
import mayaya.vo.model.EvtPublishTask;
import mayaya.vo.model.PicPublishTask;

import org.apache.log4j.Logger;

public class AnnounceTaskWriter implements Runnable{

	private Logger logger = Logger.getLogger(getClass());
	private LinkedBlockingQueue<AnnouncePubTask> queue;
	
	private MayayaService mayayaService;
	
	public AnnounceTaskWriter(LinkedBlockingQueue<AnnouncePubTask> queue) {
		this.queue = queue;
		mayayaService = MayayaServiceImpl.getInstance();
	}
	
	@Override
	public void run() {
		try {
			AnnouncePubTask task = null;			
			while(true){
				task = queue.take();
				writeToDB(task);
				logger.debug(String.format("Wrote task...eventID: %d, to db", task.getAnnouncement().getAnnouncementId()));							
			}			
		} catch (InterruptedException e) {
			logger.error("TaskWriter is going to be terminated for caught of InterruptedException exception",e);
		}
	}
	
	private void writeToDB(AnnouncePubTask task) {
		mayayaService.publishTask(task);
	}

}
