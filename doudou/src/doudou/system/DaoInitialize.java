package doudou.system;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import doudou.dao.DaoFactory;

public class DaoInitialize implements ServletContextListener {

	public void contextInitialized(ServletContextEvent arg0) {
		DaoFactory.getInstance();

		DoudouBackend.getInstance();
		
		System.out.println("Dao initialize!");
		
	}

	public void contextDestroyed(ServletContextEvent arg0) {
	}

}
