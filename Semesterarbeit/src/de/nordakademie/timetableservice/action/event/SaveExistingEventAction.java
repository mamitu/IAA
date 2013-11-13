package de.nordakademie.timetableservice.action.event;

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

public class SaveExistingEventAction extends HandleEventAction {

	@Override
	public String execute() throws Exception {
		saveReferencesAndEvent(event);
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
	public void validate() {
		event = eventService.load(eventId);
		checkDates();
		if (selectedLecturerIds.size() == 0) {
			addActionError(getText("error.lecturerRequired"));
		}
		if (selectedRoomIds.size() == 0) {
			addActionError(getText("error.roomRequired"));
		}

		if (getActionErrors().size() == 0) {
			fillSelectedEntities();
			event.setStartDate(startDate);
			event.setEndDate(endDate);
			event.setChangeTime(changeTime);

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
			for (Collision collision : collisions) {
				addActionError(getText(collision.getCollisionType().toString()) + collision.getEntity() + getText(collision.getMessage()));
			}
		}
	}

	private void checkCollisions(Event eventToCheck, Set<Collision> collisions) {
		lecturerService.getCollisionsWithOtherEvents(eventToCheck, selectedLecturers, collisions);
		roomService.getCollisionsWithOtherEvents(eventToCheck, selectedRooms, collisions);
		centuryService.getCollisionsWithOtherEvents(eventToCheck, selectedCenturies, collisions);

		roomService.checkRoomSize(selectedRooms, selectedCenturies, collisions);

		eventService.getCollisionBecauseOfChangeTime(eventToCheck, selectedLecturers, selectedRooms, selectedCenturies, collisions);
	}
}