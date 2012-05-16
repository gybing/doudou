package mayaya.util.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class SMSHTTPInterface {
	
	private static String softwareSerialNo = "3SDK-EMY-0130-MJTMR";//软件序列号,请通过亿美销售人员获取
	private static String key = "mayaya";//序列号首次激活时自己设定
	
	private Logger logger = Logger.getLogger("SMSHTTPInterface");
	
	private static SMSHTTPInterface instance = new SMSHTTPInterface();
	
	public static SMSHTTPInterface getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		
		SMSHTTPInterface.getInstance().sendSMS("15201167380","123456");
	}
	
	public int sendSMS(String phone, String content) {
		String url = "http://sdkhttp.eucp.b2m.cn/sdkproxy/sendsms.action";
		String cdkey = softwareSerialNo;
		String password = key;
		String message = "Input " + content +" to activate your account in DouDou 【兜兜】";
		String addserial = "";
		
		try {
			message = URLEncoder.encode(message,"UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String queryUrl =url + "?cdkey=" + cdkey + "&password=" + password + "&phone=" + phone + "&message="
		+ message + "&addserial=" + addserial;
		System.out.println(queryUrl);
		int result = -1;
		int count = 0;
		while (count < 5 && (result = sendGet(queryUrl)) != 0) {
			count++;
		}
		return result;
	}
	
	public int sendGet(String url) {
		int result = -1;
		BufferedReader in = null;
		try {
			String urlName = url;
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 建立实际的连接
			conn.connect();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				if (null != line && line.startsWith("<error>")) {
					result = Integer.parseInt(String.valueOf(line.charAt(7)));
					logger.info("SMSHTTPInterface send SMS result = " + result);
					break;
				}
			}
		} catch (Exception e) {
			logger.error("发送GET请求出现异常！" + e);
			e.printStackTrace();
			return -1;
		}
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
}