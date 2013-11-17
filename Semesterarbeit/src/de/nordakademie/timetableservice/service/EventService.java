package de.nordakademie.timetableservice.service;

import java.util.Date;
import java.util.List;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.EventType;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;

public interface EventService {

	public Event createNewEvent();

	public Event createNewEvent(String name, Date startDate, Date endDate, EventType eventType, Long breakTime);

	public void deleteEventWithId(Long id);

	public List<Event> findEventsForCentury(Long centuryId);

	public List<Event> findEventsForCohort(Long cohortId);

	public List<Event> findEventsForLecturer(Long lecturerId);

	public List<Event> findEventsForRoom(Long roomId);

	public Event load(Long id);

	public List<Event> loadAll();

	public void saveEvent(Event event);

	public void saveReferencesAndEvent(Event event, int numberOfWeeklyRepetitions, List<Room> roomsToUpdate, List<Lecturer> lecturersToUpdate, List<Century> centuriesToUpdate);

	public Event updateEvent(Long eventId, Date startDate, Date endDate, Long breakTime);

}