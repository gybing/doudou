package doudou.util;

import doudou.util.tool.Property;

public class DoudouConfig {

    private static DoudouConfig cfg = new DoudouConfig();
    private Property cmnProp;

    private DoudouConfig() {
        cmnProp = new Property("doudou.properties");
    }

    public static DoudouConfig getConfig() {
        return cfg;
    }

    public int getThreadPoolSize() {
        return cmnProp.getValueAsInt("ThreadPoolSize");
    }

    public int getPushTaskQueueSize() {
        return cmnProp.getValueAsInt("PushTaskQueueSize");
    }
    
//    public String getEmaySerialNo() {
//    	return cmnProp.getValueAsString("EmaySerialNo");
//    }
//    
//    public String getEmayPwd() {
//    	return cmnProp.getValueAsString("EmayPwd");
//    }
    
    public String getAPNSCertificatePath() {
    	return cmnProp.getValueAsString("APNSCertificatePath");
    }
    
    public String getUploadImgPath() {
    	return getServerPath() + getUploadPath();
    }
    
    public String getICSFilePath() {
    	return cmnProp.getValueAsString("ICSFilePath");
    }
    
    public boolean getEmailFuncSwitch() {
    	return cmnProp.getValueAsBool("EmailFuncSwitch");
    }
    
    public String getServerPath() {
    	return cmnProp.getValueAsString("ServerPath");
    }
    
    public String getUploadPath() {
    	return cmnProp.getValueAsString("UploadPath");
    }
   
}
