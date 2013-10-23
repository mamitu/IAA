package de.nordakademie.timetableservice.service;

import java.util.List;

import de.nordakademie.timetableservice.model.Event;

public interface EventService {

	public void saveEvent(Event event);

	public Event load(Long id);

	public List<Event> loadAll();

}