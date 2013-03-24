package com.doudoumobile.dao.hibernate;

import org.junit.Test;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.doudoumobile.dao.OfOfflineDao;

public class OfOfflineDaoHibernateTest {

	@Test
	public void testGetOOCountByUsername() {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		OfOfflineDao ooDao = (OfOfflineDao)context.getBean("ofOfflineDao");
		int i = ooDao.getOOCountByUsername("scscc");
		System.out.println(i);
	}

}
