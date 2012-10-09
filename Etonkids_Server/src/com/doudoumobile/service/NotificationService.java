package com.doudoumobile.service;

import java.util.List;

import com.doudoumobile.model.NotificationMO;

public interface NotificationService {
	
	/**
	 * @param notificationMO
	 */
	public void saveNotification(NotificationMO notificationMO);
	
	/**
	 * @param notificationMO
	 */
	public void updateNotification(NotificationMO notificationMO);
	
	/**
	 * @param id
	 */
	public void deleteNotification(Long id);
	
	/**
	 * @param id
	 * @return NotificationMO
	 */
	public NotificationMO queryNotificationById(Long id);
	
	/**
	 * @param notificationMOs
	 */
	public void createNotifications(List<NotificationMO> notificationMOs);
	
	/**
	 * @param userName	�û���
	 * @param messageId	֪ͨID
	 * @return NotificationMO
	 */
	public NotificationMO queryNotificationByUserName(String userName,String messageId);
	
	/**
	 * @param mo ��ѯ����
	 * @return List<NotificationMO>
	 */
	public List<NotificationMO> queryNotification(NotificationMO mo);
}
