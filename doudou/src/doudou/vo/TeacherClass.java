package doudou.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TeacherClass implements Serializable{
	private int id;
	private int teacherId;
	private int classId;
	private int teacherTypeId;
    private boolean available;
    
    private DoudouInfoType teacherType;
    private SchoolClass schoolClass;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getTeacherTypeId() {
		return teacherTypeId;
	}
	public void setTeacherTypeId(int teacherTypeId) {
		this.teacherTypeId = teacherTypeId;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public DoudouInfoType getTeacherType() {
		return teacherType;
	}
	public void setTeacherType(DoudouInfoType teacherType) {
		this.teacherType = teacherType;
	}
	public SchoolClass getSchoolClass() {
		return schoolClass;
	}
	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
	}
	
}
