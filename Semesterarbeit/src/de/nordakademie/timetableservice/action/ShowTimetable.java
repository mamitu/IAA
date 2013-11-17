package de.nordakademie.timetableservice.action;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.service.EventService;

public class ShowTimetable extends ActionSupport {

	private static final long serialVersionUID = -7940693926011624414L;
	private EventService eventService;
	private List<Event> events;
	private String entity;
	private Long entityId;

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	@Override
	public String execute() throws Exception {
		switch (entity) {
		case ("lecturer"): {
			events = eventService.findEventsForLecturer(entityId);
			break;
		}
		case ("century"): {
			events = eventService.findEventsForCentury(entityId);
			break;
		}
		case ("cohort"): {
			events = eventService.findEventsForCohort(entityId);
			break;
		}
		case ("room"): {
			events = eventService.findEventsForRoom(entityId);
			break;
		}
		}
		return SUCCESS;
	}

}