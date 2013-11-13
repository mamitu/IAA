package de.nordakademie.timetableservice.action.event;

import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.nordakademie.timetableservice.business.Collision;
import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.EventType;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;

public class SaveNewEventAction extends HandleEventAction {

	@Override
	public String execute() throws Exception {
		saveReferencesAndEvent(event);

		if (numberOfWeeklyRepetitions >= 1) {
			Calendar c = Calendar.getInstance();
			for (int i = 1; i <= numberOfWeeklyRepetitions; i++) {
				Event tempEvent = new Event();
				tempEvent.setName(name);
				tempEvent.setEventType(translateventType());
				tempEvent.setChangeTime(changeTime);
				tempEvent.setLecturers(new HashSet<Lecturer>());
				tempEvent.setRooms(new HashSet<Room>());
				tempEvent.setCenturies(new HashSet<Century>());
				c.setTime(startDate);
				c.add(Calendar.DATE, 7 * i);
				tempEvent.setStartDate(c.getTime());

				c.setTime(endDate);
				c.add(Calendar.DATE, 7 * i);
				tempEvent.setEndDate(c.getTime());
				saveReferencesAndEvent(tempEvent);
			}
		}
		return super.execute();
	}

	private void saveReferencesAndEvent(Event eventToSave) {
		updateLecturers(eventToSave);
		updateRooms(eventToSave);
		updateCenturies(eventToSave);
		eventService.saveEvent(eventToSave);
	}

	private void updateLecturers(Event eventToSave) {
		List<Lecturer> lecturersToRemove = new LinkedList<Lecturer>(eventToSave.getLecturers());
		lecturersToRemove.removeAll(selectedLecturers);
		for (Lecturer lecturer : lecturersToRemove) {
			lecturer.removeEvent(eventToSave);
			lecturerService.saveLecturer(lecturer);
		}
		for (Lecturer lecturer : selectedLecturers) {
			if (!eventToSave.getLecturers().contains(lecturer)) {
				lecturer.associateEvent(eventToSave);
				lecturerService.saveLecturer(lecturer);
			}
		}
	}

	private void updateRooms(Event eventToSave) {
		List<Room> roomsToRemove = new LinkedList<Room>(eventToSave.getRooms());
		roomsToRemove.removeAll(selectedRooms);
		for (Room room : roomsToRemove) {
			room.removeEvent(eventToSave);
			roomService.saveRoom(room);
		}
		for (Room room : selectedRooms) {
			if (!eventToSave.getRooms().contains(room)) {
				room.associateEvent(eventToSave);
				roomService.saveRoom(room);
			}
		}
	}

	private void updateCenturies(Event eventToSave) {
		List<Century> centuriesToRemove = new LinkedList<Century>(eventToSave.getCenturies());
		centuriesToRemove.removeAll(selectedCenturies);
		for (Century century : centuriesToRemove) {
			century.removeEvent(eventToSave);
			centuryService.saveCentury(century);
		}
		for (Century century : selectedCenturies) {
			if (!eventToSave.getCenturies().contains(century)) {
				century.associateEvent(eventToSave);
				centuryService.saveCentury(century);
			}
		}
	}

	@Override
	public void prepare() throws Exception {
		super.prepare();
	}

	@Override
	public void validate() {
		if (name.equals("")) {
			addActionError(getText("error.nameRequired"));
		}
		checkDates();
		checkEventType();
		if (selectedLecturerIds.size() == 0) {
			addActionError(getText("error.lecturerRequired"));
		}
		if (selectedRoomIds.size() == 0) {
			addActionError(getText("error.roomRequired"));
		}

		if (getActionErrors().size() == 0) {

			createEvent();

			if (event.getEventType().equals(EventType.SEMINAR)) {
				if (selectedCenturyIds.size() != 0) {
					addActionError(getText("error.seminarNoCenturies"));
				}
			} else {
				if (selectedCenturyIds.size() == 0) {
					addActionError(getText("error.centuriesRequired"));
				}
			}
			if (event.getChangeTime() < event.getEventType().getMinimalChangeTime()) {
				String errorMessage = getText("error.eventTypeMoreChangeTime");
				errorMessage = errorMessage.replace("$eventType", getText(event.getEventType().getName()));
				errorMessage = errorMessage.replace("$breakTime", getText(String.valueOf(event.getEventType().getMinimalChangeTime())));
				addActionError(errorMessage);
			}

			Set<Collision> collisions = new HashSet<Collision>();

			checkCollisions(event, collisions);

			if (numberOfWeeklyRepetitions >= 1) {
				Calendar c = Calendar.getInstance();
				for (int i = 1; i <= numberOfWeeklyRepetitions; i++) {
					Event tempEvent = new Event();
					tempEvent.setName(name);
					tempEvent.setEventType(translateventType());
					tempEvent.setChangeTime(changeTime);
					tempEvent.setLecturers(new HashSet<Lecturer>());
					tempEvent.setRooms(new HashSet<Room>());
					tempEvent.setCenturies(new HashSet<Century>());
					c.setTime(startDate);
					c.add(Calendar.DATE, 7 * i);
					tempEvent.setStartDate(c.getTime());

					c.setTime(endDate);
					c.add(Calendar.DATE, 7 * i);
					tempEvent.setEndDate(c.getTime());
					checkCollisions(tempEvent, collisions);
				}
			}
			for (Collision collision : collisions) {
				addActionError(getText(collision.getCollisionType().toString()) + collision.getEntity() + getText(collision.getMessage()));
			}
		}

	}

	private void createEvent() {
		event = new Event();
		event.setName(name);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setEventType(translateventType());
		event.setLecturers(new HashSet<Lecturer>());
		event.setRooms(new HashSet<Room>());
		event.setCenturies(new HashSet<Century>());

		fillSelectedEntities();

		event.setChangeTime(changeTime);
	}

	private void checkEventType() {
		if (eventType == null) {
			addActionError(getText("error.eventTypeRequired"));
		}
	}

	private EventType translateventType() {
		switch (eventType) {
		case ("eventType.exam"): {
			return EventType.EXAM;
		}
		case ("eventType.lecture"): {
			return EventType.LECTURE;
		}
		case ("eventType.elective"): {
			return EventType.ELECTIVE;
		}
		case ("eventType.seminar"): {
			return EventType.SEMINAR;
		}
		}
		return null;
	}

	private void checkCollisions(Event eventToCheck, Set<Collision> collisions) {
		lecturerService.getCollisionsWithOtherEvents(eventToCheck, selectedLecturers, collisions);
		roomService.getCollisionsWithOtherEvents(eventToCheck, selectedRooms, collisions);
		centuryService.getCollisionsWithOtherEvents(eventToCheck, selectedCenturies, collisions);

		roomService.checkRoomSize(selectedRooms, selectedCenturies, collisions);

		eventService.getCollisionBecauseOfChangeTime(eventToCheck, selectedLecturers, selectedRooms, selectedCenturies, collisions);
	}
}