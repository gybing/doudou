package mayaya.service;

import java.util.List;

import mayaya.vo.Event;

public interface EventService {
	public List<Event> getEventsByChildId(int childId);
	void addEvent(Event event);
	Event getEventById(int eventId);
}
