package de.nordakademie.timetableservice.action.event;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.service.EventService;

public class ShowEventListAction extends ActionSupport {

	private static final long serialVersionUID = -5318259667593725287L;
	private EventService eventService;
	private List<Event> events;

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public List<Event> getEvents() {
		return events;
	}

	@Override
	public String execute() throws Exception {
		events = eventService.loadAll();
		return SUCCESS;
	}

}