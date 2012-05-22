package doudou.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MessageUser implements Serializable {
	
	private int id;
	private int messageId;
	private int toChildId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public int getToChildId() {
		return toChildId;
	}
	public void setToChildId(int toChildId) {
		this.toChildId = toChildId;
	}

}
