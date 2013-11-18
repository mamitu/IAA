package de.nordakademie.timetableservice.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

import de.nordakademie.timetableservice.dao.CenturyDAO;
import de.nordakademie.timetableservice.dao.EventDAO;
import de.nordakademie.timetableservice.dao.EventParticipantDAO;
import de.nordakademie.timetableservice.dao.LecturerDAO;
import de.nordakademie.timetableservice.dao.RoomDAO;
import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.EventParticipant;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.CollisionService;

public class CollisionServiceImpl implements CollisionService {

	private CenturyDAO centuryDAO;
	private RoomDAO roomDAO;
	private LecturerDAO lecturerDAO;
	private EventDAO eventDAO;

	private final String POSITION_BEFORE = "position.before";
	private final String POSITION_AFTER = "position.after";

	public void setCenturyDAO(CenturyDAO centuryDAO) {
		this.centuryDAO = centuryDAO;
	}

	public void setRoomDAO(RoomDAO roomDAO) {
		this.roomDAO = roomDAO;
	}

	public void setLecturerDAO(LecturerDAO lecturerDAO) {
		this.lecturerDAO = lecturerDAO;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	@Override
	public List<String> getCollisions(Long eventId, String eventName, Date startDate, Date endDate, Long breakTime,
			List<Century> centuriesToCheck, List<Lecturer> lecturersToCheck, List<Room> roomsToCheck) {
		List<String> collisions = new LinkedList<String>();
		if (centuriesToCheck.size() > 0) {
			getCollisionsWithOtherEventsForEntities(eventId, startDate, endDate, centuriesToCheck, collisions);
			getCollisionBecauseOfBreakTime(eventId, eventName, startDate, endDate, breakTime, centuriesToCheck,
					collisions);
		}
		getCollisionsWithOtherEventsForEntities(eventId, startDate, endDate, lecturersToCheck, collisions);
		getCollisionsWithOtherEventsForEntities(eventId, startDate, endDate, roomsToCheck, collisions);
		getCollisionCausedByRoomSize(roomsToCheck, centuriesToCheck, collisions);
		getCollisionBecauseOfBreakTime(eventId, eventName, startDate, endDate, breakTime, lecturersToCheck, collisions);
		getCollisionBecauseOfBreakTime(eventId, eventName, startDate, endDate, breakTime, roomsToCheck, collisions);
		return collisions;
	}

	private void getCollisionsWithOtherEventsForEntities(Long eventId, Date startDate, Date endDate,
			List<? extends EventParticipant> entitiesToCheck, List<String> collisions) {
		List<? extends EventParticipant> entitiesWithExistingEvent = getDAO(entitiesToCheck.get(0).getClass().getName())
				.findEntitiesWithDatesWithoutId(startDate, endDate, eventId);
		if (!entitiesWithExistingEvent.isEmpty()) {
			for (EventParticipant eventParticipant : entitiesToCheck) {
				if (entitiesWithExistingEvent.contains(eventParticipant)) {
					String collision = LocalizedTextUtil.findDefaultText("collision.existingEventForEntity",
							ActionContext.getContext().getLocale());
					collision = collision.replace("$entity", eventParticipant.toString());
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					Date date = (Date) endDate.clone();
					collision = collision.replace("$date", sdf.format(date));
					collisions.add(collision);
				}
			}
		}
	}

	private void getCollisionCausedByRoomSize(List<Room> roomsToCheck, List<Century> selectedCenturies,
			List<String> collisions) {
		int numberOfSeatsRequired = 0;
		for (Century century : selectedCenturies) {
			numberOfSeatsRequired += century.getNumberOfStudents();
		}
		for (Room room : roomsToCheck) {
			if (room.getNumberOfSeats() < numberOfSeatsRequired) {
				collisions.add(room.toString()
						+ LocalizedTextUtil.findDefaultText("collision.roomNotEnoughSeats", ActionContext.getContext()
								.getLocale()));
			}
		}
	}

	private void getCollisionBecauseOfBreakTime(Long eventId, String name, Date startDate, Date endDate,
			Long breakTime, List<? extends EventParticipant> participantsToCheck, List<String> collisions) {
		for (EventParticipant entity : participantsToCheck) {
			// Mindest-Pausenzeit des Dozenten/der Zenturie/des Raumes
			long breakTimeForEntity = entity.getBreakTime() * 1000 * 60;

			// Die Veranstaltung vor der anzulegenden Veranstaltung fuer den
			// Dozenten/der Zenturie/den Raum
			Event eventBefore = eventDAO.findClosestEventBeforeDateForCentury(entity.getId(), startDate);

			// Die Veranstaltung nach der anzulegenden Veranstaltung fuer den
			// Dozent/der Zenturie/den Raum
			Event eventAfter = eventDAO.findClosestEventAfterDateForCentury(entity.getId(), endDate);

			if (eventBefore != null) {
				checkCollisionsBetweenEvents(eventBefore.getId(), eventBefore.getName(), eventBefore.getStartDate(),
						eventBefore.getEndDate(), eventBefore.getBreakTime(), eventId, name, startDate, endDate,
						breakTime, entity.toString(), breakTimeForEntity, POSITION_AFTER, collisions);
			}
			if (eventAfter != null) {
				checkCollisionsBetweenEvents(eventId, name, startDate, endDate, breakTime, eventAfter.getId(),
						eventAfter.getName(), eventAfter.getStartDate(), eventAfter.getEndDate(),
						eventAfter.getBreakTime(), entity.toString(), breakTimeForEntity, POSITION_BEFORE, collisions);
			}
		}
	}

	private void checkCollisionsBetweenEvents(Long thisEventId, String thisEventName, Date thisEventStartDate,
			Date thisEventEndDate, Long thisEventBreakTime, Long nextEventId, String nextEventName,
			Date nextEventStartDate, Date nextEventEndDate, Long nextEventBreakTime, String entityName,
			long breakTimeForEntity, String positionOfEventToCheck, List<String> collisions) {
		long timeBetweenEvents = nextEventStartDate.getTime() - thisEventEndDate.getTime();
		long breakTimeForThisEvent = thisEventBreakTime * 1000 * 60;
		long breakTimeForNextEvent = nextEventBreakTime * 1000 * 60;

		if (timeBetweenEvents < breakTimeForEntity) {
			String collision = LocalizedTextUtil.findDefaultText("collision.breakTimeForEntityTooShort", ActionContext
					.getContext().getLocale());
			String translatedPosition = LocalizedTextUtil.findDefaultText(positionOfEventToCheck, ActionContext
					.getContext().getLocale());
			collision = collision.replace("$date",
					getDate(thisEventEndDate, nextEventStartDate, positionOfEventToCheck));
			collision = collision.replace("$entity", entityName);
			collision = collision.replace("$position", translatedPosition);
			collisions.add(collision);
		}

		if (timeBetweenEvents < breakTimeForThisEvent) {
			String collision = LocalizedTextUtil.findDefaultText("collision.breakTimeForThisEventTooShort",
					ActionContext.getContext().getLocale());
			collision = collision.replace("$date",
					getDate(thisEventEndDate, nextEventStartDate, positionOfEventToCheck));
			collision = collision.replace("$thisEvent", thisEventName);
			collision = collision.replace("$nextEvent", nextEventName);
			collision = collision.replace("$breakTime", thisEventBreakTime.toString());
			collision = collision.replace("$entity", entityName);
			collisions.add(collision);
		}

		if (timeBetweenEvents < breakTimeForNextEvent) {
			String collision = LocalizedTextUtil.findDefaultText("collision.breakTimeForNextEventTooShort",
					ActionContext.getContext().getLocale());
			collision = collision.replace("$date",
					getDate(thisEventEndDate, nextEventStartDate, positionOfEventToCheck));
			collision = collision.replace("$thisEvent", thisEventName);
			collision = collision.replace("$nextEvent", nextEventName);
			collision = collision.replace("$breakTime", thisEventBreakTime.toString());
			collision = collision.replace("$entity", entityName);
			collisions.add(collision);
		}
	}

	private String getDate(Date thisEventEndDate, Date nextEventStartDate, String positionOfEventToCheck) {
		Date result;
		if (positionOfEventToCheck.equals(POSITION_BEFORE))
			result = thisEventEndDate;
		else
			result = nextEventStartDate;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(result);
	}

	private EventParticipantDAO getDAO(String className) {
		switch (className) {
		case ("de.nordakademie.timetableservice.model.Room"): {
			return roomDAO;
		}
		case ("de.nordakademie.timetableservice.model.Century"): {
			return centuryDAO;
		}
		case ("de.nordakademie.timetableservice.model.Lecturer"): {
			return lecturerDAO;
		}
		}
		return null;
	}

}