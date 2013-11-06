package de.nordakademie.timetableservice.service.impl;

import java.util.Set;

import de.nordakademie.timetableservice.dao.EventDAO;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.service.EventService;

public class EventServiceImpl implements EventService {

	private EventDAO eventDAO;

	@Override
	public void saveEvent(Event event) {
		eventDAO.save(event);
	}

	@Override
	public Event load(Long id) {
		return eventDAO.load(id);
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	@Override
	public Set<Event> loadAll() {
		return this.eventDAO.loadAll();
	}

	@Override
	public void deleteEventWithId(Long id) {
		this.eventDAO.deleteEventWithId(id);
	}

}