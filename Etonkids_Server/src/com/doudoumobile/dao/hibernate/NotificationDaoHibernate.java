/**
 * 
 */
package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.NotificationDao;
import com.doudoumobile.model.NotificationMO;
import com.doudoumobile.model.ReportVO;
import com.doudoumobile.model.User;
import com.doudoumobile.service.UserNotFoundException;

/**
 * @author chengqiang.liu
 * 
 */
public class NotificationDaoHibernate extends HibernateDaoSupport implements
		NotificationDao {

	public void deleteNotification(Long id) {
		getHibernateTemplate().delete(queryNotificationById(id));
	}

	public NotificationMO queryNotificationById(Long id) {
		NotificationMO notificationMO = (NotificationMO) getHibernateTemplate()
				.get(NotificationMO.class, id);
		return notificationMO;
	}

	public void saveNotification(NotificationMO notificationMO) {
		getHibernateTemplate().saveOrUpdate(notificationMO);
		getHibernateTemplate().flush();
	}

	public void updateNotification(NotificationMO notificationMO) {
		getHibernateTemplate().update(notificationMO);
		getHibernateTemplate().flush();
	}

	@SuppressWarnings("unchecked")
	public List<NotificationMO> queryNotificationByUserName(String userName,
			String messageId) {
		Object[] params = new Object[] {userName, messageId};
		return getHibernateTemplate()
				.find(
						"from NotificationMO n where n.username=? and n.messageId=? order by n.createTime desc",
						params);
	}

	public int queryCountByStatus(String status, String messageId) {
		return 0;
	}

	@SuppressWarnings("unchecked")
	public List<NotificationMO> queryNotification(NotificationMO mo) {
		return getHibernateTemplate().findByExample(mo);
	}

	public List<ReportVO> queryReportVO(NotificationMO mo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NotificationMO getLastRemoteWipeNotification(String username) {
		List result = getHibernateTemplate().find("from NotificationMO n where n.username='" + username + "' and title='remoteWipe' order by n.createTime desc limit 0 ,1");
		if (result == null || result.isEmpty()) {
            return null;
        } else {
            return (NotificationMO) result.get(0);
        }
	}

}
