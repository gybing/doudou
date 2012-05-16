package mayaya.vo;

import java.io.Serializable;

import mayaya.vo.enums.Status;
import mayaya.vo.enums.TodoType;

@SuppressWarnings("serial")
public class Todo implements Serializable {

	private int todoID;
	private int userID;
	private TodoType todoType;
	private int contentId;
	private Status status;
	
	public int getTodoID() {
		return todoID;
	}
	public void setTodoID(int todoID) {
		this.todoID = todoID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public TodoType getTodoType() {
		return todoType;
	}
	public void setTodoType(TodoType todoType) {
		this.todoType = todoType;
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
