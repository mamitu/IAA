package de.nordakademie.timetableservice.service.impl;

import java.util.List;

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
	public List<Event> loadAll() {
		return this.eventDAO.loadAll();
	}

}