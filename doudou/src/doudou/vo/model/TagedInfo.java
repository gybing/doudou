package doudou.vo.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import doudou.vo.Child;
import doudou.vo.SchoolClass;

@SuppressWarnings("serial")
public class TagedInfo implements Serializable{
	private HashMap<SchoolClass, List<Child>> classChildMap;

	public TagedInfo() {
		classChildMap = new HashMap<SchoolClass, List<Child>>();
	}
	
	public void addClassAndChildList(SchoolClass schoolClass, List<Child> childList) {
		classChildMap.put(schoolClass, childList);
	}
	
	public HashMap<SchoolClass, List<Child>> getClassChildMap() {
		return classChildMap;
	}

	public void setClassChildMap(HashMap<SchoolClass, List<Child>> classChildMap) {
		this.classChildMap = classChildMap;
	}
	
}
