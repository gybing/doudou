package doudou.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MessageLog extends Message implements Serializable {
	private int logId;
	private String userName;
	private OperationType operationType;
	
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public OperationType getOperationType() {
		return operationType;
	}
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
}
