package doudou.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import doudou.dao.DaoFactory;
import doudou.dao.UserDao;
import doudou.util.DoudouConfig;
import doudou.util.dao.DatabaseDao;
import doudou.util.tool.Base64;
import doudou.util.tool.EmailManager;
import doudou.vo.User;
import doudou.vo.model.MessagePubTask;
import doudou.vo.model.EmailTask;
import doudou.vo.model.EvtPublishTask;
import doudou.vo.model.PicPublishTask;
import doudou.vo.model.PushVO;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class DoudouBackend {
	private static DoudouBackend instance = new DoudouBackend();
	private DoudouConfig doudouConfig = DoudouConfig.getConfig();
	public static int zeroCount = 0;
	private ExecutorService exec = null;
	private Map<String, Integer> tokenMap;
	private Map<String,Integer> userAuthMap;
	
	private DatabaseDao myDatabaseDao;
	private UserDao userDao;
	
	private LinkedBlockingQueue<PicPublishTask> picTaskQueue;
	private LinkedBlockingQueue<EvtPublishTask> evtTaskQueue;
	private LinkedBlockingQueue<MessagePubTask> messageTaskQueue;
	
	private LinkedBlockingQueue<String> imgQueue;
	private LinkedBlockingQueue<String> headPicQueue;
	private LinkedBlockingQueue<PushVO> apnsPushQueue;
	private LinkedBlockingQueue<EmailTask> emailQueue;
	
	private Logger logger = Logger.getLogger(getClass());
	
	private DoudouBackend() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		userDao = myDatabaseDao.getEntityDao(UserDao.class);
		
		start();
	}
	
	private void start() {
		if (exec == null) {
			
			userAuthMap = new HashMap<String,Integer>();
			picTaskQueue = new LinkedBlockingQueue<PicPublishTask>();
			evtTaskQueue = new LinkedBlockingQueue<EvtPublishTask>();
			messageTaskQueue = new LinkedBlockingQueue<MessagePubTask>();
			apnsPushQueue = new LinkedBlockingQueue<PushVO>();
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
	
	public int verify(String token) {
		if (tokenMap.containsKey(token)) {
			return tokenMap.get(token);
		} else {
			return -1;
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
	
	public void publishTask(MessagePubTask task) {
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
	
	public synchronized void addUserAuthData(String veriCode, int userId) {
		logger.info(String.format("AddUserAuthData veriCode : %s, userId: %d", veriCode,userId));
		userAuthMap.put(veriCode, userId);
	}
	
	public synchronized int getUserByVeriCode(String veriCode) {
		return userAuthMap.get(veriCode);
	}
	
	public synchronized String getVeriCodeByUserId(int userId) {
		if (userAuthMap.containsValue(userId)) {
			for (String veriCode : userAuthMap.keySet()) {
				if (userAuthMap.get(veriCode).intValue() == userId) {
					return veriCode;
				}
			}
		} 
		return null;
	}
	
	public synchronized boolean isContainedVeriCode(String veriCode) {
		return userAuthMap.containsKey(veriCode);
	}
	
	public void addPushVO(PushVO pushVO) {
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
