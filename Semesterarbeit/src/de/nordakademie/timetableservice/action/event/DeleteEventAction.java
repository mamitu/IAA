package de.nordakademie.timetableservice.action.event;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.service.EventService;

public class DeleteEventAction extends ActionSupport {

	private static final long serialVersionUID = 6064012231726525810L;
	private EventService eventService;
	private List<Event> events;
	private Long eventId;

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	@Override
	public String execute() throws Exception {
		eventService.deleteEventWithId(eventId);
		events = eventService.loadAll();
		return SUCCESS;
	}

}