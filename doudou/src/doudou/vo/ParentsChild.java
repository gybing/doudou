package doudou.vo;

import java.io.Serializable;

enum Relation {
	Father, Mother, Guardian
}

@SuppressWarnings("serial")
public class ParentsChild implements Serializable {
	
	private int id;
	private int parentId;
	private int childId;
	private Relation relation;
	private boolean available;
	private boolean chiefGuardian;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getChildId() {
		return childId;
	}
	public void setChildId(int childId) {
		this.childId = childId;
	}
	public Relation getRelation() {
		return relation;
	}
	public void setRelation(Relation relation) {
		this.relation = relation;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public boolean isChiefGuardian() {
		return chiefGuardian;
	}
	public void setChiefGuardian(boolean chiefGuardian) {
		this.chiefGuardian = chiefGuardian;
	}
	

	
}
