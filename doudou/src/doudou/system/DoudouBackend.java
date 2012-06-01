package doudou.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import doudou.dao.DaoFactory;
import doudou.dao.UserDao;
import doudou.util.Constants;
import doudou.util.DoudouConfig;
import doudou.util.dao.DatabaseDao;
import doudou.util.tool.Base64;
import doudou.util.tool.EmailManager;
import doudou.vo.User;
import doudou.vo.model.MsgPublishTask;
import doudou.vo.model.EmailTask;
import doudou.vo.model.EvtPublishTask;
import doudou.vo.model.PicPublishTask;
import doudou.vo.model.APNSPushVO;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class DoudouBackend {
	private static DoudouBackend instance = new DoudouBackend();
	private DoudouConfig doudouConfig = DoudouConfig.getConfig();
	public static int zeroCount = 0;
	private ExecutorService exec = null;
	
	private DatabaseDao myDatabaseDao;
	private UserDao userDao;
	
	private LinkedBlockingQueue<PicPublishTask> picTaskQueue;
	private LinkedBlockingQueue<EvtPublishTask> evtTaskQueue;
	private LinkedBlockingQueue<MsgPublishTask> messageTaskQueue;
	
	private LinkedBlockingQueue<String> imgQueue;
	private LinkedBlockingQueue<String> headPicQueue;
	private LinkedBlockingQueue<APNSPushVO> apnsPushQueue;
	private LinkedBlockingQueue<EmailTask> emailQueue;
	
	private Logger logger = Logger.getLogger(getClass());
	
	private DoudouBackend() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		userDao = myDatabaseDao.getEntityDao(UserDao.class);
		
		start();
	}
	
	private void start() {
		if (exec == null) {
			
			picTaskQueue = new LinkedBlockingQueue<PicPublishTask>();
			evtTaskQueue = new LinkedBlockingQueue<EvtPublishTask>();
			messageTaskQueue = new LinkedBlockingQueue<MsgPublishTask>();
			apnsPushQueue = new LinkedBlockingQueue<APNSPushVO>();
			imgQueue = new LinkedBlockingQueue<String>();
			headPicQueue = new LinkedBlockingQueue<String>();
			emailQueue = new LinkedBlockingQueue<EmailTask>();
			
			exec = Executors.newFixedThreadPool(7);
			exec.execute(new PicTaskWriter(picTaskQueue));
			exec.execute(new EvtTaskWriter(evtTaskQueue));
			exec.execute(new MessageTaskWriter(messageTaskQueue));
			exec.execute(new ImageProcessor(imgQueue));
			exec.execute(new HeadPicProcessor(headPicQueue));
			exec.execute(new APNSManager(apnsPushQueue));
			if (doudouConfig.getEmailFuncSwitch()) {
				exec.execute(new EmailManager(emailQueue));
			}
		}
			
	}
	
	public static DoudouBackend getInstance() {
		return instance;
	}
	
	public void publishTask(PicPublishTask task) {
		try {
			picTaskQueue.put(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void publishTask(MsgPublishTask task) {
		try {
			messageTaskQueue.put(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void publishTask(EvtPublishTask task) {
		try {
			evtTaskQueue.put(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void addImageToQueue(String fileName) {
		try {
			imgQueue.put(fileName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void addHeadPicToQueue(String fileName) {
		try {
			headPicQueue.put(fileName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public synchronized int getUserByCookie(String cookie) {
		try{
			String decodedString = Base64.decode(cookie).toString();
			String[] decodedArray = decodedString.split("/");
			if (decodedArray.length == 3 && Constants.DOUDOU_TICKET.equals(decodedArray[0])) {
				return Integer.parseInt(decodedArray[1]);
			} else {
				return -1;
			}
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}
	
	
	public void addPushVO(APNSPushVO pushVO) {
		try {
			apnsPushQueue.put(pushVO);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void addEmailTask(EmailTask task) {
		try {
			emailQueue.put(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
