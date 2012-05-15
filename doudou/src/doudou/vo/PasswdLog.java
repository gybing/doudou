package doudou.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class PasswdLog implements Serializable{
	private int id;
	private String oldPasswd;
	private String newPasswd;
    private String operator;
    private Date modifyTime;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOldPasswd() {
		return oldPasswd;
	}
	public void setOldPasswd(String oldPasswd) {
		this.oldPasswd = oldPasswd;
	}
	public String getNewPasswd() {
		return newPasswd;
	}
	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
