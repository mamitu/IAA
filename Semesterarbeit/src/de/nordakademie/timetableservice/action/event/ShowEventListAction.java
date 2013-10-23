package de.nordakademie.timetableservice.action.event;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.service.EventService;

public class ShowEventListAction extends ActionSupport {

	private EventService eventService;
	private List<Event> events;

	@Override
	public String execute() throws Exception {
		events = eventService.loadAll();
		return SUCCESS;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

}
