package doudou.vo;

import java.io.Serializable;

enum OperationType {
	Add, Update, Delete
}

@SuppressWarnings("serial")
public class EventLog extends Event implements Serializable {
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
