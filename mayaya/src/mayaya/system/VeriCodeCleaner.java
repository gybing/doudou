package mayaya.system;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

import mayaya.vo.model.VeriCodeInfo;

public class VeriCodeCleaner implements Runnable{
	private ConcurrentHashMap<String, VeriCodeInfo> userAuthMap;
	private Logger logger = Logger.getLogger(getClass());
	
	public VeriCodeCleaner(ConcurrentHashMap<String, VeriCodeInfo> userAuthMap) {
		this.userAuthMap = userAuthMap;
	}
	
	
	/*
	 * 每隔一分钟执行一次，清理过期的验证码
	 * 
	 * */
	@Override
	public void run() {
		Date now;
		while (true) {
			try {
				logger.info("VeriCodeCleaner wake up and work!");
				now = new Date();
				for (String veriCode : userAuthMap.keySet()) {
					VeriCodeInfo info = userAuthMap.get(veriCode);
					if (now.after(info.getExpireTime())) {
						userAuthMap.remove(veriCode);
						logger.info(String.format("Clean veriCode : %s, useId: %d, tele: %s", veriCode,info.getUserId(),info.getTelephone()));
					}
				}
				logger.info("VeriCodeCleaner fell asleep!");
				TimeUnit.MINUTES.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e.getMessage(),e);
			}
		}
	}
	
	
}
