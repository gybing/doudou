package com.doudoumobile.system;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.doudoumobile.dao.DeviceTokenDao;
import com.doudoumobile.dao.OfOfflineDao;
import com.doudoumobile.dao.SCSCCUserDao;
import com.doudoumobile.model.DeviceToken;
import com.doudoumobile.model.OfOffline;
import com.doudoumobile.model.SCSCCUser;

public class MessagePusher implements Runnable{

	DeviceTokenDao dtDao = null;
	OfOfflineDao ooDao = null;
	SCSCCUserDao suDao = null;
	
	@Override
	public void run() {
		while (true) {
			//System.out.println("offline message push thread sleep");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("offline message push thread awake");
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			if (null == ooDao) {
				ooDao = (OfOfflineDao)context.getBean("ofOfflineDao");
			}
			if (null == dtDao) {
				dtDao = (DeviceTokenDao)context.getBean("deviceTokenDao");
			}
			if(null == suDao){
				suDao = (SCSCCUserDao)context.getBean("scsccUserDao");

			}
			
			int badgeNum=0;
			List<OfOffline> offlines = ooDao.getNotSendedOfflines();
			for (OfOffline oo : offlines) {
				String username = oo.getUsername();

				badgeNum = ooDao.getOOCountByUsername(username);
				DeviceToken dt = dtDao.getDeviceTokenByUsername(username);
				if (null != dt) {
					String token = dt.getDeviceTokenId();
					String Stanza = oo.getStanza();
					System.out.println(Stanza);
					//解析Stanza
					String content = null;
					String senderUserName = null;
					//SAXReader reader = new SAXReader(); 
					Document document; 
					try{ 
						document = DocumentHelper.parseText(Stanza); 
						Element root = document.getRootElement(); 
						if(root.element("body") != null){
							content = root.element("body").getText();
						}
						String  from = root.attributeValue("from");
						senderUserName = from.substring(0, from.indexOf("@"));
						
					} catch (DocumentException e1) 
					{ 
					   e1.printStackTrace(); 
					} 

					String senderName = ((SCSCCUser)suDao.getUserById(senderUserName)).getRealName();
					APNSManager.getInstance().pushOffline(token, content, senderName, badgeNum);
					
					
					//update status
					oo.setSended(true);
					ooDao.saveOffline(oo);
				}
				 

			}
			
		}
		
		
	}
	
	

}
