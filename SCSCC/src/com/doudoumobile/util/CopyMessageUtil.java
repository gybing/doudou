/**
 * 
 */
package com.doudoumobile.util;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.xmpp.packet.IQ;

import com.doudoumobile.model.NotificationMO;

/**
 * @author chengqiang.liu
 *	将生成的通知ID给notificationMO对象入库 ，方便根据此ID修改回执状态。
 */
public class CopyMessageUtil {
	
	public static void IQ2Message(IQ iq,NotificationMO notificationMO){
		
		Element root = iq.getElement();
		Attribute idAttr=root.attribute("id");
		if(idAttr != null){
			String id = idAttr.getValue();
			notificationMO.setMessageId(id);
		}
	}
}
