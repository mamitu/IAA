package de.nordakademie.timetableservice.service.impl;

import java.util.List;
import java.util.Set;

import de.nordakademie.timetableservice.business.Collision;
import de.nordakademie.timetableservice.business.CollisionType;
import de.nordakademie.timetableservice.dao.EventDAO;
import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;
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

	@Override
	public void getCollisionBecauseOfChangeTime(Event event, Set<Lecturer> lecturersToCheck, Set<Room> roomsToCheck, Set<Century> centuriesToCheck, Set<Collision> collisions) {
		checkLecturers(event, lecturersToCheck, collisions);
		checkRooms(event, roomsToCheck, collisions);
		checkCenturies(event, centuriesToCheck, collisions);
	}

	private void checkLecturers(Event event, Set<Lecturer> lecturersToCheck, Set<Collision> collisions) {
		long changeTimeEventNeeds = event.getChangeTime() * 1000 * 60;
		for (Lecturer lecturer : lecturersToCheck) {
			long breakTimeLecturerNeeds = lecturer.getBreakTime() * 1000 * 60;
			Event nextEventForLecturer = eventDAO.findClosestEventAfterDateForLecturer(lecturer.getId(), event.getEndDate(), event.getId());
			Event eventAheadForLecturer = eventDAO.findClosestEventBeforeDateForLecturer(lecturer.getId(), event.getStartDate(), event.getId());

			if (nextEventForLecturer != null) {
				long breakTimeAfterEventForLecturer = nextEventForLecturer.getStartDate().getTime() - event.getEndDate().getTime();

				if (breakTimeAfterEventForLecturer < Math.max(breakTimeLecturerNeeds, changeTimeEventNeeds)) {
					if (breakTimeLecturerNeeds >= changeTimeEventNeeds) {
						collisions.add(new Collision(CollisionType.ERROR, " " + lecturer.toString() + " (Veranstaltung am: " + event.getEndDate() + ") ",
								"error.collision.entityNeedsBreakTimeAfterEvent"));
					} else {
						collisions.add(new Collision(CollisionType.ERROR, " " + lecturer.toString() + " ", "error.collision.eventNeedsBreakTimeForEntityAfterEvent"));
					}
				}
			}

			if (eventAheadForLecturer != null) {
				long breakTimeBeforeEvent = event.getStartDate().getTime() - eventAheadForLecturer.getEndDate().getTime();

				if (breakTimeBeforeEvent < Math.max(breakTimeLecturerNeeds, changeTimeEventNeeds)) {
					if (breakTimeLecturerNeeds >= changeTimeEventNeeds) {
						collisions.add(new Collision(CollisionType.ERROR, " " + lecturer.toString() + " ", "error.collision.entityNeedsBreakTimeBeforeEvent"));
					} else {
						collisions.add(new Collision(CollisionType.ERROR, " " + lecturer.toString() + " ", "error.collision.eventNeedsBreakTimeForEntityBeforeEvent"));
					}
				}

			}
		}
	}

	private void checkRooms(Event event, Set<Room> roomsToCheck, Set<Collision> collisions) {
		long changeTimeEventNeeds = event.getChangeTime() * 1000 * 60;
		for (Room room : roomsToCheck) {
			long breakTimeRoomNeeds = room.getBreakTime() * 1000 * 60;
			Event nextEventForRoom = eventDAO.findClosestEventAfterDateForRoom(room.getId(), event.getEndDate(), event.getId());
			Event eventAheadForRoom = eventDAO.findClosestEventBeforeDateForRoom(room.getId(), event.getStartDate(), event.getId());

			if (nextEventForRoom != null) {
				long breakTimeAfterEventForRoom = nextEventForRoom.getStartDate().getTime() - event.getEndDate().getTime();

				if (breakTimeAfterEventForRoom < Math.max(breakTimeRoomNeeds, changeTimeEventNeeds)) {
					if (breakTimeRoomNeeds >= changeTimeEventNeeds) {
						collisions.add(new Collision(CollisionType.ERROR, " " + room.toString() + " ", "error.collision.entityNeedsBreakTimeAfterEvent"));
					} else {
						collisions.add(new Collision(CollisionType.ERROR, " " + room.toString() + " ", "error.collision.eventNeedsBreakTimeForEntityAfterEvent"));
					}
				}
			}

			if (eventAheadForRoom != null) {
				long breakTimeBeforeEvent = event.getStartDate().getTime() - eventAheadForRoom.getEndDate().getTime();

				if (breakTimeBeforeEvent < Math.max(breakTimeRoomNeeds, changeTimeEventNeeds)) {
					if (breakTimeRoomNeeds >= changeTimeEventNeeds) {
						collisions.add(new Collision(CollisionType.ERROR, " " + room.toString() + " ", "error.collision.entityNeedsBreakTimeBeforeEvent"));
					} else {
						collisions.add(new Collision(CollisionType.ERROR, " " + room.toString() + " ", "error.collision.eventNeedsBreakTimeForEntityBeforeEvent"));
					}
				}

			}
		}
	}

	private void checkCenturies(Event event, Set<Century> centuriesToCheck, Set<Collision> collisions) {
		long changeTimeEventNeeds = event.getChangeTime() * 1000 * 60;
		for (Century century : centuriesToCheck) {
			long breakTimeCenturyNeeds = century.getBreakTime() * 1000 * 60;
			Event nextEventForCentury = eventDAO.findClosestEventAfterDateForCentury(century.getId(), event.getEndDate(), event.getId());
			Event eventAheadForCentury = eventDAO.findClosestEventBeforeDateForCentury(century.getId(), event.getStartDate(), event.getId());

			if (nextEventForCentury != null) {
				long breakTimeAfterEventForCentury = nextEventForCentury.getStartDate().getTime() - event.getEndDate().getTime();

				if (breakTimeAfterEventForCentury < Math.max(breakTimeCenturyNeeds, changeTimeEventNeeds)) {
					if (breakTimeCenturyNeeds >= changeTimeEventNeeds) {
						collisions.add(new Collision(CollisionType.ERROR, " " + century.toString() + " ", "error.collision.entityNeedsBreakTimeAfterEvent"));
					} else {
						collisions.add(new Collision(CollisionType.ERROR, " " + century.toString() + " ", "error.collision.eventNeedsBreakTimeForEntityAfterEvent"));
					}
				}
			}

			if (eventAheadForCentury != null) {
				long breakTimeBeforeEvent = event.getStartDate().getTime() - eventAheadForCentury.getEndDate().getTime();

				if (breakTimeBeforeEvent < Math.max(breakTimeCenturyNeeds, changeTimeEventNeeds)) {
					if (breakTimeCenturyNeeds >= changeTimeEventNeeds) {
						collisions.add(new Collision(CollisionType.ERROR, " " + century.toString() + " ", "error.collision.entityNeedsBreakTimeBeforeEvent"));
					} else {
						collisions.add(new Collision(CollisionType.ERROR, " " + century.toString() + " ", "error.collision.eventNeedsBreakTimeForEntityBeforeEvent"));
					}
				}

			}
		}
	}

	// @Override
	// public boolean checkNameExists(String eventName) {
	// return eventDAO.findEventsByName(eventName).isEmpty() ? false : true;
	// }
	//
	// @Override
	// public boolean checkNameExistsForAnotherId(Long eventId, String
	// eventName) {
	// return eventDAO.findEventsByNameWithoutId(eventName, eventId).isEmpty() ?
	// false : true;
	// }

	@Override
	public List<Event> findEventsForCentury(Long centuryId) {
		return eventDAO.findEventsForCentury(centuryId);
	}

	@Override
	public List<Event> findEventsForLecturer(Long lecturerId) {
		return eventDAO.findEventsForLecturer(lecturerId);
	}

	@Override
	public List<Event> findEventsForRoom(Long roomId) {
		return eventDAO.findEventsForRoom(roomId);
	}

}