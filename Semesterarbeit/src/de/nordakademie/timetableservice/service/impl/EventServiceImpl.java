package de.nordakademie.timetableservice.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.nordakademie.timetableservice.dao.EventDAO;
import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.EventType;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.CenturyService;
import de.nordakademie.timetableservice.service.EventService;
import de.nordakademie.timetableservice.service.LecturerService;
import de.nordakademie.timetableservice.service.RoomService;

public class EventServiceImpl implements EventService {

	private EventDAO eventDAO;
	private LecturerService lecturerService;
	private RoomService roomService;
	private CenturyService centuryService;

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public void setLecturerService(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public void setCenturyService(CenturyService centuryService) {
		this.centuryService = centuryService;
	}

	@Override
	public Event createNewEvent() {
		return new Event();
	}

	@Override
	public Event createNewEvent(String name, Date startDate, Date endDate, EventType eventType, Long breakTime) {
		Event event = new Event();
		event.setName(name);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setEventType(eventType);
		event.setBreakTime(breakTime);
		event.setCenturies(new LinkedList<Century>());
		event.setLecturers(new LinkedList<Lecturer>());
		event.setRooms(new LinkedList<Room>());
		return event;

	}

	@Override
	public void deleteEventWithId(Long id) {
		Event event = load(id);
		for (Lecturer lecturer : event.getLecturers()) {
			lecturer.removeEvent(event);
			lecturerService.saveLecturer(lecturer);
		}
		for (Century century : event.getCenturies()) {
			century.removeEvent(event);
			centuryService.saveCentury(century);
		}
		for (Room room : event.getRooms()) {
			room.removeEvent(event);
			roomService.saveRoom(room);
		}
		this.eventDAO.deleteEventWithId(id);
	}

	@Override
	public List<Event> findEventsForCentury(Long centuryId) {
		return eventDAO.findEventsForCentury(centuryId);
	}

	@Override
	public List<Event> findEventsForCohort(Long cohortId) {
		return eventDAO.findEventsForCohort(cohortId);
	}

	@Override
	public List<Event> findEventsForLecturer(Long lecturerId) {
		return eventDAO.findEventsForLecturer(lecturerId);
	}

	@Override
	public List<Event> findEventsForRoom(Long roomId) {
		return eventDAO.findEventsForRoom(roomId);
	}

	@Override
	public Event load(Long id) {
		return eventDAO.load(id);
	}

	@Override
	public List<Event> loadAll() {
		return this.eventDAO.loadAll();
	}

	@Override
	public void saveEvent(Event event) {
		eventDAO.save(event);
	}

	@Override
	public void saveReferencesAndEvent(Event event, int numberOfWeeklyRepetitions, List<Room> roomsToUpdate, List<Lecturer> lecturersToUpdate, List<Century> centuriesToUpdate) {
		updateLecturers(event, lecturersToUpdate);
		updateRooms(event, roomsToUpdate);
		updateCenturies(event, centuriesToUpdate);
		saveEvent(event);

		Calendar c = Calendar.getInstance();
		for (int i = 1; i <= numberOfWeeklyRepetitions; i++) {

			Event tempEvent = new Event();
			tempEvent.setName(event.getName());
			tempEvent.setEventType(event.getEventType());
			tempEvent.setBreakTime(event.getBreakTime());
			tempEvent.setLecturers(new LinkedList<Lecturer>());
			tempEvent.setRooms(new LinkedList<Room>());
			tempEvent.setCenturies(new LinkedList<Century>());
			c.setTime(event.getStartDate());
			c.add(Calendar.DATE, 7 * i);
			tempEvent.setStartDate(c.getTime());

			c.setTime(event.getEndDate());
			c.add(Calendar.DATE, 7 * i);
			tempEvent.setEndDate(c.getTime());

			updateLecturers(tempEvent, lecturersToUpdate);
			updateRooms(tempEvent, roomsToUpdate);
			updateCenturies(tempEvent, centuriesToUpdate);
			saveEvent(tempEvent);
		}
	}

	@Override
	public Event updateEvent(Long eventId, Date startDate, Date endDate, Long breakTime) {
		Event event = load(eventId);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setBreakTime(breakTime);
		return event;
	}

	private void updateLecturers(Event eventToSave, List<Lecturer> lecturersToUpdate) {
		List<Lecturer> lecturersToRemove = new LinkedList<Lecturer>(eventToSave.getLecturers());
		lecturersToRemove.removeAll(lecturersToUpdate);
		for (Lecturer lecturer : lecturersToRemove) {
			lecturer.removeEvent(eventToSave);
			lecturerService.saveLecturer(lecturer);
		}
		for (Lecturer lecturer : lecturersToUpdate) {
			if (!eventToSave.getLecturers().contains(lecturer)) {
				lecturer.associateEvent(eventToSave);
				lecturerService.saveLecturer(lecturer);
			}
		}
	}

	private void updateRooms(Event eventToSave, List<Room> roomsToUpdate) {
		List<Room> roomsToRemove = new LinkedList<Room>(eventToSave.getRooms());
		roomsToRemove.removeAll(roomsToUpdate);
		for (Room room : roomsToRemove) {
			room.removeEvent(eventToSave);
			roomService.saveRoom(room);
		}
		for (Room room : roomsToUpdate) {
			if (!eventToSave.getRooms().contains(room)) {
				room.associateEvent(eventToSave);
				roomService.saveRoom(room);
			}
		}
	}

	private void updateCenturies(Event eventToSave, List<Century> centuriesToUpdate) {
		List<Century> centuriesToRemove = new LinkedList<Century>(eventToSave.getCenturies());
		centuriesToRemove.removeAll(centuriesToUpdate);
		for (Century century : centuriesToRemove) {
			century.removeEvent(eventToSave);
			centuryService.saveCentury(century);
		}
		for (Century century : centuriesToUpdate) {
			if (!eventToSave.getCenturies().contains(century)) {
				century.associateEvent(eventToSave);
				centuryService.saveCentury(century);
			}
		}
	}

}