package doudou.vo.model;

import doudou.vo.Message;

public class MsgPublishTask extends PublishTask {
	private Message message;
	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	
	@Override
	public void setChildrenListString(String atChildList) {
		message.setAtChildList(atChildList);
	}

}
