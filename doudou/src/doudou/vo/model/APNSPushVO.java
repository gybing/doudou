package doudou.vo.model;

import java.io.Serializable;
import java.util.Set;

import doudou.vo.type.TodoType;

@SuppressWarnings("serial")
public class APNSPushVO implements Serializable {
	
	private int contentId;
	private TodoType todoType;
	private Set<Integer> userIdList;
	private String fromUser;
	
	//Used when modified 
	private int formerId;
	
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public Set<Integer> getUserIdList() {
		return userIdList;
	}
	public void setUserIdList(Set<Integer> userIdList) {
		this.userIdList = userIdList;
	}
	
	public TodoType getTodoType() {
		return todoType;
	}
	public void setTodoType(TodoType todoType) {
		this.todoType = todoType;
	}
	public int getFormerId() {
		return formerId;
	}
	public void setFormerId(int formerId) {
		this.formerId = formerId;
	}
	
}
