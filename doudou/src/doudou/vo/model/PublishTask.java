package doudou.vo.model;

import java.util.List;

import doudou.vo.Child;
import doudou.vo.type.TodoType;

public abstract class PublishTask {
	
	private List<Integer> targetChildIdList;
	
	private int schoolId;
	private TodoType todoType;
	
	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	
	public List<Integer> getTargetChildIdList() {
		return targetChildIdList;
	}

	public void setTargetChildIdList(List<Integer> targetChildIdList) {
		this.targetChildIdList = targetChildIdList;
	}

	public TodoType getTodoType() {
		return todoType;
	}

	public void setTodoType(TodoType todoType) {
		this.todoType = todoType;
	}

//	public List<Integer> getNewClassIdList() {
//		return newClassIdList;
//	}
//
//	public void setNewClassIdList(List<Integer> newClassIdList) {
//		this.newClassIdList = newClassIdList;
//	}
//
//	public List<Integer> getOldClassIdList() {
//		return oldClassIdList;
//	}
//
//	public void setOldClassIdList(List<Integer> oldClassIdList) {
//		this.oldClassIdList = oldClassIdList;
//	}

	public abstract void setChildrenListString(String atChildList);
}
