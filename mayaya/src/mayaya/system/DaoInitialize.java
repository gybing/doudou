package mayaya.system;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import mayaya.dao.DaoFactory;

public class DaoInitialize implements ServletContextListener, HttpSessionListener {

	public void contextInitialized(ServletContextEvent arg0) {
		DaoFactory.getInstance();

		MayayaBackend.getInstance();
		
		System.out.println("Dao initialize!");
		//ReviewBackend.getInstance().start();
		
	}

	public void contextDestroyed(ServletContextEvent arg0) {
	}

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
     
    }
}
