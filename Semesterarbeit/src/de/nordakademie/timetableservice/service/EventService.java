package de.nordakademie.timetableservice.service;

import java.util.Set;

import de.nordakademie.timetableservice.model.Event;

public interface EventService {

	public void saveEvent(Event event);

	public Event load(Long id);

	public Set<Event> loadAll();

}