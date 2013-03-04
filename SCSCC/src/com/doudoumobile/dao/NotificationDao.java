package com.doudoumobile.dao;

import java.util.List;

import com.doudoumobile.model.NotificationMO;
import com.doudoumobile.model.ReportVO;

/**
 * @author chengqiang.liu
 *
 */
public interface NotificationDao {
	
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
	 * �鿴֪ͨ
	 * @param id
	 * @return NotificationMO
	 */
	public NotificationMO queryNotificationById(Long id);
	
	/**
	 * ֪
	 * @param userName	
	 * @param messageId	
	 * @return List<NotificationMO
	 */
	public List<NotificationMO> queryNotificationByUserName(String userName,String messageId);

	/**
	 * @param status	
	 * @param messageId ID
	 * @return
	 */
	public int queryCountByStatus(String status,String messageId);
	
	/**
	 * @param mo 
	 * @return List<ReportVO>
	 */
	public List<ReportVO> queryReportVO(NotificationMO mo);
	
	/**
	 * @param mo 
	 * @return List<NotificationMO>
	 */
	public List<NotificationMO> queryNotification(NotificationMO mo);
	
	public NotificationMO getLastRemoteWipeNotification(String username);

}
