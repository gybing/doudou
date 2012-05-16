package mayaya.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mayaya.service.DeviceTokenService;
import mayaya.service.UserService;
import mayaya.service.impl.DeviceTokenServiceImpl;
import mayaya.service.impl.UserServiceImpl;
import mayaya.system.MayayaBackend;
import mayaya.util.BaseServlet;
import mayaya.util.tool.SMSHTTPInterface;
import mayaya.vo.DeviceToken;
import mayaya.vo.User;
import mayaya.vo.model.VeriCodeInfo;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class VerifyTelephone
 */
public class VerifyTelephone extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;   
	private DeviceTokenService deviceTokenService;
	private Logger logger = Logger.getLogger(getClass());
    /**
     * @see BaseServlet#BaseServlet()
     */
    public VerifyTelephone() {
        super();
        userService = UserServiceImpl.getInstance();
        deviceTokenService = DeviceTokenServiceImpl.getInstance();
    }
    
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		String phoneNumber = getStringParameter(request,"telephone","15201167380");
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
		String VeriCode;
        
		logger.info("Initialize for user telephone: " + phoneNumber);
		// For Review
		if ("20120223001".equals(phoneNumber)) {
			String veriCode = "0001";
			logger.warn("Attention! it is the back door number! Father");
			String deviceToken = getStringParameter(request,"deviceToken","");
			if (!"".equals(deviceToken)) {
				DeviceToken dt = new DeviceToken();
				dt.setDeviceTokenId(deviceToken);
				dt.setUserId(60);
				deviceTokenService.addDeviceToken(dt);
				logger.info(String.format("Add deviceToken into DB,userId : %d, DeviceToken : %s",60,deviceToken));
			}
			VeriCodeInfo veriCodeInfo = new VeriCodeInfo();
			veriCodeInfo.setTelephone(phoneNumber);
			veriCodeInfo.setUserId(60);
			Date expireTime = getExpireTime();
			logger.info(String.format("phoneNO:%s, veriCode:0001, expireTime: %s", phoneNumber,expireTime));
			veriCodeInfo.setExpireTime(expireTime);
			MayayaBackend.getInstance().addUserAuthData(veriCode, veriCodeInfo);
			VeriCode = "SMS Sended";
			writer.print(VeriCode);
			return;
		}
		if ("19820413001".equals(phoneNumber)) {
			String veriCode = "0101";
			int userId = 97;
			logger.warn("Attention! it is Casper's father!");
			String deviceToken = getStringParameter(request,"deviceToken","");
			if (!"".equals(deviceToken)) {
				DeviceToken dt = new DeviceToken();
				dt.setDeviceTokenId(deviceToken);
				dt.setUserId(userId);
				deviceTokenService.addDeviceToken(dt);
				logger.info(String.format("Add deviceToken into DB,userId : %d, DeviceToken : %s",userId,deviceToken));
			}
			VeriCodeInfo veriCodeInfo = new VeriCodeInfo();
			veriCodeInfo.setTelephone(phoneNumber);
			veriCodeInfo.setUserId(userId);
			Date expireTime = getExpireTime();
			logger.info(String.format("phoneNO:%s, veriCode:0101, expireTime: %s", phoneNumber,expireTime));
			veriCodeInfo.setExpireTime(expireTime);
			MayayaBackend.getInstance().addUserAuthData(veriCode, veriCodeInfo);
			VeriCode = "SMS Sended";
			writer.print(VeriCode);
			return;
		}
		if ("19820413002".equals(phoneNumber)) {
			String veriCode = "0102";
			int userId = 98;
			logger.warn("Attention! it is Casper's brother!");
			String deviceToken = getStringParameter(request,"deviceToken","");
			if (!"".equals(deviceToken)) {
				DeviceToken dt = new DeviceToken();
				dt.setDeviceTokenId(deviceToken);
				dt.setUserId(userId);
				deviceTokenService.addDeviceToken(dt);
				logger.info(String.format("Add deviceToken into DB,userId : %d, DeviceToken : %s",userId,deviceToken));
			}
			VeriCodeInfo veriCodeInfo = new VeriCodeInfo();
			veriCodeInfo.setTelephone(phoneNumber);
			veriCodeInfo.setUserId(userId);
			Date expireTime = getExpireTime();
			logger.info(String.format("phoneNO:%s, veriCode:0102, expireTime: %s", phoneNumber,expireTime));
			veriCodeInfo.setExpireTime(expireTime);
			MayayaBackend.getInstance().addUserAuthData(veriCode, veriCodeInfo);
			VeriCode = "SMS Sended";
			writer.print(VeriCode);
			return;
		}
		if ("19820413003".equals(phoneNumber)) {
			String veriCode = "0103";
			int userId = 99;
			logger.warn("Attention! it is melody's friends!");
			String deviceToken = getStringParameter(request,"deviceToken","");
			if (!"".equals(deviceToken)) {
				DeviceToken dt = new DeviceToken();
				dt.setDeviceTokenId(deviceToken);
				dt.setUserId(userId);
				deviceTokenService.addDeviceToken(dt);
				logger.info(String.format("Add deviceToken into DB,userId : %d, DeviceToken : %s",userId,deviceToken));
			}
			VeriCodeInfo veriCodeInfo = new VeriCodeInfo();
			veriCodeInfo.setTelephone(phoneNumber);
			veriCodeInfo.setUserId(userId);
			Date expireTime = getExpireTime();
			logger.info(String.format("phoneNO:%s, veriCode:0103, expireTime: %s", phoneNumber,expireTime));
			veriCodeInfo.setExpireTime(expireTime);
			MayayaBackend.getInstance().addUserAuthData(veriCode, veriCodeInfo);
			VeriCode = "SMS Sended";
			writer.print(VeriCode);
			return;
		}
		if ("19820413004".equals(phoneNumber)) {
			String veriCode = "0104";
			int userId = 100;
			logger.warn("Attention! it is melody's friends!");
			String deviceToken = getStringParameter(request,"deviceToken","");
			if (!"".equals(deviceToken)) {
				DeviceToken dt = new DeviceToken();
				dt.setDeviceTokenId(deviceToken);
				dt.setUserId(userId);
				deviceTokenService.addDeviceToken(dt);
				logger.info(String.format("Add deviceToken into DB,userId : %d, DeviceToken : %s",userId,deviceToken));
			}
			VeriCodeInfo veriCodeInfo = new VeriCodeInfo();
			veriCodeInfo.setTelephone(phoneNumber);
			veriCodeInfo.setUserId(userId);
			Date expireTime = getExpireTime();
			logger.info(String.format("phoneNO:%s, veriCode:0104, expireTime: %s", phoneNumber,expireTime));
			veriCodeInfo.setExpireTime(expireTime);
			MayayaBackend.getInstance().addUserAuthData(veriCode, veriCodeInfo);
			VeriCode = "SMS Sended";
			writer.print(VeriCode);
			return;
		}
		if ("19820413005".equals(phoneNumber)) {
			String veriCode = "0105";
			int userId = 101;
			logger.warn("Attention! it is  melody's friends!");
			String deviceToken = getStringParameter(request,"deviceToken","");
			if (!"".equals(deviceToken)) {
				DeviceToken dt = new DeviceToken();
				dt.setDeviceTokenId(deviceToken);
				dt.setUserId(userId);
				deviceTokenService.addDeviceToken(dt);
				logger.info(String.format("Add deviceToken into DB,userId : %d, DeviceToken : %s",userId,deviceToken));
			}
			VeriCodeInfo veriCodeInfo = new VeriCodeInfo();
			veriCodeInfo.setTelephone(phoneNumber);
			veriCodeInfo.setUserId(userId);
			Date expireTime = getExpireTime();
			logger.info(String.format("phoneNO:%s, veriCode:0105, expireTime: %s", phoneNumber,expireTime));
			veriCodeInfo.setExpireTime(expireTime);
			MayayaBackend.getInstance().addUserAuthData(veriCode, veriCodeInfo);
			VeriCode = "SMS Sended";
			writer.print(VeriCode);
			return;
		}
		if ("19820413006".equals(phoneNumber)) {
			String veriCode = "0106";
			int userId = 102;
			logger.warn("Attention! it is melody's friends!!");
			String deviceToken = getStringParameter(request,"deviceToken","");
			if (!"".equals(deviceToken)) {
				DeviceToken dt = new DeviceToken();
				dt.setDeviceTokenId(deviceToken);
				dt.setUserId(userId);
				deviceTokenService.addDeviceToken(dt);
				logger.info(String.format("Add deviceToken into DB,userId : %d, DeviceToken : %s",userId,deviceToken));
			}
			VeriCodeInfo veriCodeInfo = new VeriCodeInfo();
			veriCodeInfo.setTelephone(phoneNumber);
			veriCodeInfo.setUserId(userId);
			Date expireTime = getExpireTime();
			logger.info(String.format("phoneNO:%s, veriCode:0106, expireTime: %s", phoneNumber,expireTime));
			veriCodeInfo.setExpireTime(expireTime);
			MayayaBackend.getInstance().addUserAuthData(veriCode, veriCodeInfo);
			VeriCode = "SMS Sended";
			writer.print(VeriCode);
			return;
		}
		if ("19820413007".equals(phoneNumber)) {
			String veriCode = "0107";
			int userId = 103;
			logger.warn("Attention! it is melody's friends!");
			String deviceToken = getStringParameter(request,"deviceToken","");
			if (!"".equals(deviceToken)) {
				DeviceToken dt = new DeviceToken();
				dt.setDeviceTokenId(deviceToken);
				dt.setUserId(userId);
				deviceTokenService.addDeviceToken(dt);
				logger.info(String.format("Add deviceToken into DB,userId : %d, DeviceToken : %s",userId,deviceToken));
			}
			VeriCodeInfo veriCodeInfo = new VeriCodeInfo();
			veriCodeInfo.setTelephone(phoneNumber);
			veriCodeInfo.setUserId(userId);
			Date expireTime = getExpireTime();
			logger.info(String.format("phoneNO:%s, veriCode:0107, expireTime: %s", phoneNumber,expireTime));
			veriCodeInfo.setExpireTime(expireTime);
			MayayaBackend.getInstance().addUserAuthData(veriCode, veriCodeInfo);
			VeriCode = "SMS Sended";
			writer.print(VeriCode);
			return;
		}
		
		// For Review User 2
		if (("20120223002").equals(phoneNumber)) {
			logger.warn("Attention! it is the back door number! Mother");
			String veriCode = "0002";
			String deviceToken = getStringParameter(request,"deviceToken","");
			if (!"".equals(deviceToken)) {
				DeviceToken dt = new DeviceToken();
				dt.setDeviceTokenId(deviceToken);
				dt.setUserId(61);
				deviceTokenService.addDeviceToken(dt);
				logger.info(String.format("Add deviceToken into DB,userId : %d, DeviceToken : %s",61,deviceToken));
			}
			VeriCodeInfo veriCodeInfo = new VeriCodeInfo();
			veriCodeInfo.setTelephone(phoneNumber);
			veriCodeInfo.setUserId(61);
			Date expireTime = getExpireTime();
			logger.info(String.format("phoneNO:%s, veriCode:0001, expireTime: %s", phoneNumber,expireTime));
			veriCodeInfo.setExpireTime(expireTime);
			MayayaBackend.getInstance().addUserAuthData(veriCode, veriCodeInfo);
			VeriCode = "SMS Sended";
			writer.print(VeriCode);
			return;
		}
		
		// For VC or potential Customer
		if (("12345678912").equals(phoneNumber)) {
			logger.warn("Attention! it is the VC number!");
			String veriCode = "0123";
			String deviceToken = getStringParameter(request,"deviceToken","");
			if (!"".equals(deviceToken)) {
				DeviceToken dt = new DeviceToken();
				dt.setDeviceTokenId(deviceToken);
				dt.setUserId(106);
				deviceTokenService.addDeviceToken(dt);
				logger.info(String.format("Add deviceToken into DB,userId : %d, DeviceToken : %s",106,deviceToken));
			}
			VeriCodeInfo veriCodeInfo = new VeriCodeInfo();
			veriCodeInfo.setTelephone(phoneNumber);
			veriCodeInfo.setUserId(106);
			Date expireTime = getExpireTime();
			logger.info(String.format("phoneNO:%s, veriCode:0123, expireTime: %s", phoneNumber,expireTime));
			veriCodeInfo.setExpireTime(expireTime);
			MayayaBackend.getInstance().addUserAuthData(veriCode, veriCodeInfo);
			VeriCode = "SMS Sended";
			writer.print(VeriCode);
			return;
		}
		
		// For VC or potential Customer
		if (("12345678900").equals(phoneNumber)) {
			logger.warn("Attention! it is the VC number2!");
			String veriCode = "0111";
			String deviceToken = getStringParameter(request,"deviceToken","");
			if (!"".equals(deviceToken)) {
				DeviceToken dt = new DeviceToken();
				dt.setDeviceTokenId(deviceToken);
				dt.setUserId(107);
				deviceTokenService.addDeviceToken(dt);
				logger.info(String.format("Add deviceToken into DB,userId : %d, DeviceToken : %s",107,deviceToken));
			}
			VeriCodeInfo veriCodeInfo = new VeriCodeInfo();
			veriCodeInfo.setTelephone(phoneNumber);
			veriCodeInfo.setUserId(107);
			Date expireTime = getExpireTime();
			logger.info(String.format("phoneNO:%s, veriCode:0111, expireTime: %s", phoneNumber,expireTime));
			veriCodeInfo.setExpireTime(expireTime);
			MayayaBackend.getInstance().addUserAuthData(veriCode, veriCodeInfo);
			VeriCode = "SMS Sended";
			writer.print(VeriCode);
			return;
		}
		
		if (!phoneNumber.startsWith("0") && !phoneNumber.startsWith("1") || phoneNumber.length() < 11 ) {
			VeriCode = "Illegal TelephoneNumber";
		}
		else if (phoneNumber.equals("15201167380")) {
			logger.info("GC's PhoneNO Again!");
			VeriCode = "GC's Phone";
		}
		else {
			User user = userService.getUserByTelephone(phoneNumber);
			if (user == null) {
				VeriCode = "Not exist telephone";
			} else {
				String veriCode = getRandomVeriCode();
				// 不重复
				while( MayayaBackend.getInstance().isContainedVeriCode(veriCode)) {
					veriCode = getRandomVeriCode();
				}
				logger.info(String.format("VeriCode For telephone %s : %s", phoneNumber, veriCode));
				int sendResult = SMSHTTPInterface.getInstance().sendSMS(phoneNumber, veriCode);
				if (sendResult == 0) {
					VeriCode = "SMS Sended";
				}
				else {
					VeriCode = "SMS Failure";
				}
				String deviceToken = getStringParameter(request,"deviceToken","");
				if (!"".equals(deviceToken)) {
					DeviceToken dt = new DeviceToken();
					dt.setDeviceTokenId(deviceToken);
					dt.setUserId(user.getUserId());
					deviceTokenService.addDeviceToken(dt);
					logger.info(String.format("Add deviceToken into DB,userId : %d, DeviceToken : %s",user.getUserId(),deviceToken));
				}
				VeriCodeInfo veriCodeInfo = new VeriCodeInfo();
				veriCodeInfo.setTelephone(phoneNumber);
				veriCodeInfo.setUserId(user.getUserId());
				Date expireTime = getExpireTime();
				logger.info(String.format("phoneNO:%s, veriCode:%s, expireTime: %s", phoneNumber,veriCode,expireTime));
				veriCodeInfo.setExpireTime(expireTime);
				MayayaBackend.getInstance().addUserAuthData(veriCode, veriCodeInfo);
			}
		}
		writer.print(VeriCode);
		
    }
	
	// 取得一个1000-9999的随机数
	private String getRandomVeriCode(){
		String s = "";
		int intCount = 0;
		intCount = (new Random()).nextInt(9999);
		if (intCount < 1000) {
			intCount += 1000;
		}
		s = intCount + "";
		return s;
	}
	
	private Date getExpireTime() {
		Date now = new Date();
		System.out.println("now = " + now);
		long expire = now.getTime() + 10 * 60 * 1000;
		Date expireDate = new Date(expire);
		System.out.println("expireTime = " + expireDate);
		return expireDate;
	}
}
