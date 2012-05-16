package mayaya.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import mayaya.service.UserService;
import mayaya.service.impl.UserServiceImpl;
import mayaya.util.MayayaConfig;
import mayaya.util.tool.Base64;
import mayaya.util.tool.EmailManager;
import mayaya.vo.User;
import mayaya.vo.model.AnnouncePubTask;
import mayaya.vo.model.EmailTask;
import mayaya.vo.model.EvtPublishTask;
import mayaya.vo.model.PicPublishTask;
import mayaya.vo.model.PushVO;
import mayaya.vo.model.VeriCodeInfo;

import org.apache.log4j.Logger;

public class MayayaBackend {
	private static MayayaBackend instance = new MayayaBackend();
	private MayayaConfig mayayaConfig = MayayaConfig.getConfig();
	private ExecutorService exec = null;
	private ScheduledThreadPoolExecutor ses = null;
	private Map<String, Integer> tokenMap;
	
	private ConcurrentHashMap<String, VeriCodeInfo> userAuthMap;
	
	private UserService userService;
	
	private LinkedBlockingQueue<PicPublishTask> picTaskQueue;
	private LinkedBlockingQueue<EvtPublishTask> evtTaskQueue;
	private LinkedBlockingQueue<AnnouncePubTask> announceTaskQueue;
	
	private LinkedBlockingQueue<String> imgQueue;
	private LinkedBlockingQueue<String> headPicQueue;
	private LinkedBlockingQueue<PushVO> pushQueue;
	private LinkedBlockingQueue<EmailTask> emailQueue;
	
	private Logger logger = Logger.getLogger(getClass());
	
	private MayayaBackend() {
		userService = UserServiceImpl.getInstance();
		start();
	}
	
	private void start() {
		if (exec == null) {
			loadTokens();
			
			userAuthMap = new ConcurrentHashMap<String, VeriCodeInfo>();
			picTaskQueue = new LinkedBlockingQueue<PicPublishTask>();
			evtTaskQueue = new LinkedBlockingQueue<EvtPublishTask>();
			announceTaskQueue = new LinkedBlockingQueue<AnnouncePubTask>();
			pushQueue = new LinkedBlockingQueue<PushVO>();
			imgQueue = new LinkedBlockingQueue<String>();
			headPicQueue = new LinkedBlockingQueue<String>();
			emailQueue = new LinkedBlockingQueue<EmailTask>();
			
			// thread pool
			exec = Executors.newFixedThreadPool(8);
			exec.execute(new PicTaskWriter(picTaskQueue));
			exec.execute(new EvtTaskWriter(evtTaskQueue));
			exec.execute(new AnnounceTaskWriter(announceTaskQueue));
			exec.execute(new ImageProcessor(imgQueue));
			exec.execute(new HeadPicProcessor(headPicQueue));
			exec.execute(new APNSManager(pushQueue));
			exec.execute(new VeriCodeCleaner(userAuthMap));

			if (mayayaConfig.getEmailEnabled()) {
				exec.execute(new EmailManager(emailQueue));
			}
			
			
//			//Periodically load the inactive Device list
//            Runnable inactiveDeviceFetchThread = new Runnable() {
//				public void run() {			 
//					APNSManager.loadInactiveDevices();
//				}
//			}; 
//			
//			// Scheduled thread pool
//			ses = new ScheduledThreadPoolExecutor(2);
//			ses.scheduleAtFixedRate(
//					inactiveDeviceFetchThread, 10000,
//					mayayaConfig.getInactiveDeviceFetchInterval() * 1000,
//					TimeUnit.MILLISECONDS);
			
		}
			
	}
	
	public void loadTokens() {
		tokenMap = new HashMap<String, Integer>();
		List<User> userList = userService.getAllUsers();
		for (User user : userList) {
			String tokenString = user.getUserId() + user.getLogin();
			String token = Base64.encode(tokenString.getBytes());
			System.out.println("Token for User : " + user.getLogin() + " ------> " + token);
			tokenMap.put(token, user.getUserId());
		}
	}
	
	public int verify(String token) {
		if (tokenMap.containsKey(token)) {
			return tokenMap.get(token);
		} else {
			return -1;
		}
	}
	
	public static MayayaBackend getInstance() {
		return instance;
	}
	
	public void publishTask(PicPublishTask task) {
		try {
			picTaskQueue.put(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void publishTask(AnnouncePubTask task) {
		try {
			announceTaskQueue.put(task);
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
	
	public synchronized void addUserAuthData(String veriCode, VeriCodeInfo veriCodeInfo) {
		logger.info(String.format("AddUserAuthData veriCode : %s, tele: %s", veriCode,veriCodeInfo.getTelephone()));
		userAuthMap.put(veriCode, veriCodeInfo);
	}
	
	public synchronized VeriCodeInfo getUserByVeriCode(String veriCode) {
		return userAuthMap.get(veriCode);
	}
	
//	public synchronized String getVeriCodeByUserId(String telephone) {
//		if (userAuthMap.containsValue(telephone)) {
//			for (String veriCode : userAuthMap.keySet()) {
//				if (userAuthMap.get(veriCode).equals(telephone)) {
//					return veriCode;
//				}
//			}
//		} 
//		return null;
//	}
	
	public synchronized boolean isContainedVeriCode(String veriCode) {
		return userAuthMap.containsKey(veriCode);
	}
	
	public void addPushVO(PushVO pushVO) {
		try {
			pushQueue.put(pushVO);
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
