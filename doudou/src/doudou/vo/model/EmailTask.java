package doudou.vo.model;

import doudou.vo.type.TodoType;


public class EmailTask {
	
	private String[] to;
	
	// Email内容相应
	private TodoType todoType;
	private String fromUser;
	private String toUser;
	private String className;
	private String childName;
	private Object content;
	
	// 附件路径
	private String attachPath;
	
	public String[] getTo() {
		return to;
	}
	public void setTo(String[] to) {
		this.to = to;
	}
	public TodoType getTodoType() {
		return todoType;
	}
	public void setTodoType(TodoType todoType) {
		this.todoType = todoType;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getChildName() {
		return childName;
	}
	public void setChildName(String childName) {
		this.childName = childName;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public String getAttachPath() {
		return attachPath;
	}
	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}
}
