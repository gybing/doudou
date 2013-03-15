package com.doudoumobile.system;

import java.util.List;
import java.util.Map;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.doudoumobile.dao.DeviceTokenDao;
import com.doudoumobile.dao.OfOfflineDao;
import com.doudoumobile.model.OfOffline;

public class MessagePusher implements Runnable{

	DeviceTokenDao dtDao = null;
	OfOfflineDao ooDao = null;
	
	@Override
	public void run() {
		while (true) {
			System.out.println("offline message push thread sleep");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("offline message push thread awake");
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			if (null == ooDao) {
				ooDao = (OfOfflineDao)context.getBean("ofOfflineDao");
			}
			if (null == dtDao) {
				dtDao = (DeviceTokenDao)context.getBean("deviceTokenDao");
			}
			List<OfOffline> offlines = ooDao.getNotSendedOfflines();
			for (OfOffline oo : offlines) {
				// apns
				// dtDao
				// update status
			}
			
		}
		
		
	}

}
