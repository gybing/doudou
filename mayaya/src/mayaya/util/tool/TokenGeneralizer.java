package mayaya.util.tool;

public class TokenGeneralizer {
	private static TokenGeneralizer instance;
	
	private TokenGeneralizer() {
		
	}
	
	public static TokenGeneralizer getInstance() {
		return instance;
	}
	
	public String getToken() {
		return "";
	}
}
