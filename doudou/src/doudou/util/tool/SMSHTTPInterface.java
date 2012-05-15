package doudou.util.tool;

import org.apache.log4j.Logger;

public class SMSHTTPInterface {
	
	private static String softwareSerialNo = "3SDK-EMY-0130-MJTMR";//软件序列号,请通过亿美销售人员获取
	private static String key = "mayaya";//序列号首次激活时自己设定
	private static String password = "193004";// 密码,请通过亿美销售人员获取
	
	private static Logger logger = Logger.getLogger("SMSHTTPInterface");
	
	private static SMSHTTPInterface instance = new SMSHTTPInterface();
	
	public static SMSHTTPInterface getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		//SMSUtil.sendContent("15201167380","987654");
	}
	
}
