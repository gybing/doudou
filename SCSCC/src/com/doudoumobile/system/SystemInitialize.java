package com.doudoumobile.system;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SystemInitialize implements ServletContextListener, HttpSessionListener {

	public void contextInitialized(ServletContextEvent event) {

		ScsccBackend.getInstance().start();
		
		System.out.println("offline message pusher initialized!");
		
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