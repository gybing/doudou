package doudou.vo.model;

import java.util.List;

import doudou.vo.Child;
import doudou.vo.type.TodoType;

public abstract class PublishTask {
	
	private List<Integer> newChildIdList;
	private List<Integer> oldChildIdList;
	private int schoolId;
	private TodoType todoType;
	
	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public List<Integer> getNewChildIdList() {
		return newChildIdList;
	}

	public void setNewChildIdList(List<Integer> newChildIdList) {
		this.newChildIdList = newChildIdList;
	}

	public List<Integer> getOldChildIdList() {
		return oldChildIdList;
	}

	public void setOldChildIdList(List<Integer> oldChildIdList) {
		this.oldChildIdList = oldChildIdList;
	}

	public TodoType getTodoType() {
		return todoType;
	}

	public void setTodoType(TodoType todoType) {
		this.todoType = todoType;
	}

	public abstract void setChildrenListString(String atChildList);
}
