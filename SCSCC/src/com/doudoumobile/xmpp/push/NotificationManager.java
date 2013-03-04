package com.doudoumobile.xmpp.push;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.IQ;

import com.doudoumobile.model.NotificationMO;
import com.doudoumobile.model.User;
import com.doudoumobile.service.NotificationService;
import com.doudoumobile.service.ServiceLocator;
import com.doudoumobile.service.UserService;
import com.doudoumobile.util.CopyMessageUtil;
import com.doudoumobile.xmpp.session.ClientSession;
import com.doudoumobile.xmpp.session.SessionManager;

public class NotificationManager {

	private static final String NOTIFICATION_NAMESPACE = "androidpn:iq:notification";

	private final Log log = LogFactory.getLog(getClass());

	private SessionManager sessionManager;
	
	private NotificationService notificationService;
	
	private UserService userService;

	/**
	 * Constructor.
	 */
	public NotificationManager() {
		sessionManager = SessionManager.getInstance();
		notificationService = ServiceLocator.getNotificationService();
		userService = ServiceLocator.getUserService();
	}
	
	

	/**
	 * Broadcasts a newly created notification message to all connected users.
	 * 
	 * @param apiKey
	 *            the API key
	 * @param title
	 *            the title
	 * @param message
	 *            the message details
	 * @param uri
	 *            the uri
	 */
	public void sendBroadcast(String apiKey, String title, String message,
			String uri) {
		log.debug("sendBroadcast()...");
		List<NotificationMO> notificationMOs = new ArrayList<NotificationMO>();
		IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);

		for (ClientSession session : sessionManager.getSessions()) {
			// ����֪ͨ����
			NotificationMO notificationMO = new NotificationMO(apiKey, title,
					message, uri);
			try {
				notificationMO.setUsername(session.getUsername());
				notificationMO.setClientIp(session.getHostAddress());
				notificationMO.setResource(session.getAddress().getResource());
			} catch (Exception e) {
				e.printStackTrace();
			}
			// ����Ϣ��ID��ӵ�֪ͨ����
			CopyMessageUtil.IQ2Message(notificationIQ, notificationMO);

			if (session.getPresence().isAvailable()) {
				notificationMO.setStatus(NotificationMO.STATUS_SEND);
				notificationIQ.setTo(session.getAddress());
				session.deliver(notificationIQ);
			} else {
				notificationMO.setStatus(NotificationMO.STATUS_NOT_SEND);
			}
			//��ÿ��֪ͨ���뼯����
			notificationMOs.add(notificationMO);
		}
		try{
			//�������
			notificationService.createNotifications(notificationMOs);
		}catch(Exception e){
			log.warn(" notifications insert to database failure!!");
		}
		
	}
	
	public void sendAllBroadcast(String apiKey, String title, String message,
			String uri) {
		IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
		List<User> list = userService.getUsers();
		for (User user : list) {
			//this.sendNotifcationToUser(apiKey, user.getUsername(), title, message, uri,notificationIQ);
		}
		
	}

	/**
	 * Sends a newly created notification message to the specific user.
	 * 
	 * @param apiKey
	 *            the API key
	 * @param title
	 *            the title
	 * @param message
	 *            the message details
	 * @param uri
	 *            the uri
	 */
	public void sendNotifcationToUser(String apiKey, String username, long eton_id,
			String title, String message, String uri, IQ notificationIQ) {
		log.debug("sendNotifcationToUser()...");
		ClientSession session = sessionManager.getSession(username);
		NotificationMO notificationMO = new NotificationMO(apiKey, title,
				message, uri);
		notificationMO.setUsername(username);
		notificationMO.setEtonUser(eton_id);
		CopyMessageUtil.IQ2Message(notificationIQ, notificationMO);
		if (session != null && session.getPresence().isAvailable()) {
			notificationIQ.setTo(session.getAddress());
			session.deliver(notificationIQ);
			notificationMO.setStatus(NotificationMO.STATUS_SEND);
			try {
				notificationMO.setClientIp(session.getHostAddress());
				notificationMO.setResource(session.getAddress().getResource());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			notificationMO.setStatus(NotificationMO.STATUS_NOT_SEND);
		}
		try{
			notificationService.saveNotification(notificationMO);
		}catch(Exception e){
			log.warn(" notifications insert to database failure!!");
		}
	}

	/**
	 * Creates a new notification IQ and returns it.
	 */
	private IQ createNotificationIQ(String apiKey, String title,
			String message, String uri) {
		Random random = new Random();
		String id = Integer.toHexString(random.nextInt());
		// String id = String.valueOf(System.currentTimeMillis());

		Element notification = DocumentHelper.createElement(QName.get(
				"notification", NOTIFICATION_NAMESPACE));
		notification.addElement("id").setText(id);
		notification.addElement("apiKey").setText(apiKey);
		notification.addElement("title").setText(title);
		notification.addElement("message").setText(message);
		notification.addElement("uri").setText(uri);

		IQ iq = new IQ();
		iq.setType(IQ.Type.set);
		iq.setChildElement(notification);

		return iq;
	}
	
	public void sendNotifications(String apiKey, String username, long eton_id,
			String title, String message, String uri){
		log.info("send notification()..." + username + " " + title + " " + message);
		IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
		if(username.indexOf(";")!=-1){
			String[] users = username.split(";");
			for (String user : users) {
				this.sendNotifcationToUser(apiKey, user,eton_id, title, message, uri,notificationIQ);
			}
		}else{
			this.sendNotifcationToUser(apiKey, username,eton_id, title, message, uri,notificationIQ);
		}
	}
	
	public void sendOfflineNotification(NotificationMO notificationMO ) {
		log.debug("sendOfflineNotifcation()...");
		IQ notificationIQ = createNotificationIQ(notificationMO.getApiKey(), notificationMO.getTitle(), notificationMO.getMessage(), notificationMO.getUri());
		notificationIQ.setID(notificationMO.getMessageId());
		ClientSession session = sessionManager.getSession(notificationMO.getUsername());
		if (session != null && session.getPresence().isAvailable()) {
			notificationIQ.setTo(session.getAddress());
			session.deliver(notificationIQ);
			try{
				//�޸�֪ͨ״̬Ϊ�ѷ���
				notificationMO.setStatus(NotificationMO.STATUS_SEND);
				//IP
				notificationMO.setClientIp(session.getHostAddress());
				//��Դ
				notificationMO.setResource(session.getAddress().getResource());
				notificationService.updateNotification(notificationMO);
			}catch (Exception e) {
				log.warn(" update notification status failure !");
			}
		}
	}
	
	public void sendEtonNotification(String apiKey, String username,long eton_id,
			String title, String message, String uri, IQ notificationIQ) {
		log.debug("sendNotifcationToUser()...");
		ClientSession session = sessionManager.getSession(username);
		NotificationMO notificationMO = new NotificationMO(apiKey, title,
				message, uri);
		notificationMO.setUsername(username);
		notificationMO.setEtonUser(eton_id);
		CopyMessageUtil.IQ2Message(notificationIQ, notificationMO);
		if (session != null && session.getPresence().isAvailable()) {
			notificationIQ.setTo(session.getAddress());
			session.deliver(notificationIQ);
			notificationMO.setStatus(NotificationMO.STATUS_SEND);
			try {
				notificationMO.setClientIp(session.getHostAddress());
				notificationMO.setResource(session.getAddress().getResource());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			notificationMO.setStatus(NotificationMO.STATUS_NOT_SEND);
		}
		try{
			notificationService.saveNotification(notificationMO);
		}catch(Exception e){
			log.warn(" notifications insert to database failure!!");
		}
		
		
	}
}
