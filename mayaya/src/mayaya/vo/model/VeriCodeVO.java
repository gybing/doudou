package mayaya.vo.model;

import java.io.Serializable;
import java.util.List;

import mayaya.vo.Child;
import mayaya.vo.User;

@SuppressWarnings("serial")
public class VeriCodeVO implements Serializable{
	
	private String VerifyResult;
	private User user;
	private String token;
	private List<Child> childList;
	
	public String getVerifyResult() {
		return VerifyResult;
	}
	public void setVerifyResult(String verifyResult) {
		VerifyResult = verifyResult;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public List<Child> getChildList() {
		return childList;
	}
	public void setChildList(List<Child> childList) {
		this.childList = childList;
	}
}
