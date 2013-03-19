package com.doudoumobile.system;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScsccBackend {
	
	private static ScsccBackend instance = new ScsccBackend();
	
	public static long CONTACT_LAST_MODIFIED = System.currentTimeMillis();
	
	private ExecutorService exec = null;
	
	private ScsccBackend() {
		exec = Executors.newFixedThreadPool(1);
		
	}
	
	public void start() {
		exec.execute(new MessagePusher());
	}
	
	public static ScsccBackend getInstance() {
		return instance;
	}
}
