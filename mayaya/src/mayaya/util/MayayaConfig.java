package mayaya.util;

import mayaya.util.tool.Property;

public class MayayaConfig {

    private static MayayaConfig cfg = new MayayaConfig();
    private Property cmnProp;

    private MayayaConfig() {
        cmnProp = new Property("mayaya.properties");
    }

    public static MayayaConfig getConfig() {
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
    	return cmnProp.getValueAsString("UploadImgPath");
    }
    
    public String getICSFilePath() {
    	return cmnProp.getValueAsString("ICSFilePath");
    }
    
    public boolean getEmailEnabled() {
    	return cmnProp.getValueAsBool("EmailEnabled");
    }
    
    public String getDouDouIP() {
    	return cmnProp.getValueAsString("DouDouIP");
    }
    
    public boolean getAPNSDebug() {
    	return cmnProp.getValueAsBool("APNSDebug");
    }
    
    public String getAPNSDebugPath() {
    	return cmnProp.getValueAsString("APNSDebugCertificatePath");
    }
    
    public int getInactiveDeviceFetchInterval() {
    	return cmnProp.getValueAsInt("InactiveDeviceFetchInterval");
    }
   
}
