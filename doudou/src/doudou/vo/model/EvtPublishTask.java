package doudou.vo.model;

import doudou.vo.Event;

public class EvtPublishTask extends PublishTask {
	
	private Event event;
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	
	@Override
	public void setChildrenListString(String atChildList) {
		event.setAtChildList(atChildList);
		
	}
}
