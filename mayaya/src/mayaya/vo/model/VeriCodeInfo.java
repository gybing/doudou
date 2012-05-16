package mayaya.vo.model;

import java.util.Date;

/*
 * To record the vericode value information 
 * 
 * in auth map
 * 
 * */
public class VeriCodeInfo {
	
	private String telephone;
	private Date expireTime;
	private int userId;

	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
