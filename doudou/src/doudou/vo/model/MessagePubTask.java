package doudou.vo.model;

import java.util.List;

import doudou.vo.Message;

public class MessagePubTask {
	private Message message;
	private List<Integer> childrenList;
	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public List<Integer> getChildrenList() {
		return childrenList;
	}
	public void setChildrenList(List<Integer> childrenList) {
		this.childrenList = childrenList;
	}

}
