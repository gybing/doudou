package doudou.vo.model;

import java.io.Serializable;
import java.util.List;

import doudou.vo.Event;

@SuppressWarnings("serial")
public class EvtPublishTask implements Serializable{
	
	private Event event;
	private List<Integer> childIdList;
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public List<Integer> getChildIdList() {
		return childIdList;
	}
	public void setChildIdList(List<Integer> childIdList) {
		this.childIdList = childIdList;
	}
}
