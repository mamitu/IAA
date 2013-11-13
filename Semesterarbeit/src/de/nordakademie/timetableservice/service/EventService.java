package de.nordakademie.timetableservice.service;

import java.util.List;
import java.util.Set;

import de.nordakademie.timetableservice.business.Collision;
import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;

public interface EventService {

	public void saveEvent(Event event);

	public Event load(Long id);

	public Set<Event> loadAll();

	public void deleteEventWithId(Long id);

	public void getCollisionBecauseOfChangeTime(Event event, Set<Lecturer> lecturersToCheck, Set<Room> roomsToCheck, Set<Century> centuriesToCheck, Set<Collision> collisions);

	// public boolean checkNameExists(String eventName);
	//
	// public boolean checkNameExistsForAnotherId(Long eventId, String
	// eventName);

	public List<Event> findEventsForCentury(Long centuryId);

	public List<Event> findEventsForLecturer(Long lecturerId);

	public List<Event> findEventsForRoom(Long roomId);

}