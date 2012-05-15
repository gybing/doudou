package doudou.vo;

import java.io.Serializable;

import doudou.vo.type.TodoType;

enum TodoStatus {
	Done, UnDone
}

@SuppressWarnings("serial")
public class Todo implements Serializable {

	private int id;
	private int userId;
	private TodoType todoType;
	private int contentId;
	private TodoStatus status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public TodoStatus getStatus() {
		return status;
	}
	public void setStatus(TodoStatus status) {
		this.status = status;
	}
}
