package de.nordakademie.timetableservice.action;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.service.EventService;

public class ShowTimetable extends ActionSupport {

	private String entity;
	private Long entityId;

	private EventService eventService;

	private List<Event> events;

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
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
		case ("room"): {
			events = eventService.findEventsForRoom(entityId);
			break;
		}
		}
		return SUCCESS;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
