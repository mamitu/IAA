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

/**
 * Implementation des Kollisionen-Services
 * 
 * @author
 * 
 */
public class CollisionServiceImpl implements CollisionService {

	/**
	 * Data Access Object fuer Zenturien
	 */
	private CenturyDAO centuryDAO;

	/**
	 * Data Access Object fuer Raeume
	 */
	private RoomDAO roomDAO;

	/**
	 * Data Access Object fuer Dozenten
	 */
	private LecturerDAO lecturerDAO;

	/**
	 * Data Access Object fuer Veranstaltungen
	 */
	private EventDAO eventDAO;

	/**
	 * i18n Bezeichnung fur "vor der Veranstaltung", um den richtigen Text aus
	 * der messages-Datei zu laden
	 */
	private final String POSITION_BEFORE = "position.before";

	/**
	 * i18n Bezeichnung fur "nach der Veranstaltung", um den richtigen Text aus
	 * der messages-Datei zu laden
	 */
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
		// Nicht immer nehmen Zenturien an einer Veranstaltung teil (zB
		// Wahlpflichtfaecher)
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

	/**
	 * Schaut, ob jede Entitaet schon eine Veranstaltung zu den eingegebenen
	 * Terminen hat. Geprueft wird, ob das Startdatum in einer bestehenden
	 * Veranstaltung der Entitaet liegt, ob das Enddatum in einer bestehenden
	 * Veranstaltung der Entitaet liegt, oder ob es eine umschliessende
	 * Veranstaltung fuer die Enittaet gibt.
	 * 
	 * @param eventId
	 *            ID der anzulegenden Veranstaltung
	 * @param startDate
	 *            Startdatum der anzulegenden Veranstaltung
	 * @param endDate
	 *            Enddatum der anzulegenden Veranstaltung
	 * @param entitiesToCheck
	 *            Zu ueberprufende Teilnehmer
	 * @param collisions
	 *            Liste mit Kollisionen, die gefuellt werden soll
	 */
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

	/**
	 * Ermittelt die Kollisionen, die aufgrund von zu kleiner Raeume entstehen.
	 * Ein Raum muss die komplette Summe aller teilnehmenden Studenten aufnehmen
	 * koennen.
	 * 
	 * @param roomsToCheck
	 *            Raeume der anzulegenden Veranstaltung
	 * @param selectedCenturies
	 *            Zenturien der anzulegenden Veranstaltung
	 * @param collisions
	 *            Liste der Kollisionen, die gefuellt werden soll
	 */
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

	/**
	 * Ermittelt Kollisionen, die aufgrund von zu kurzen Pausenzeiten entstehen.
	 * 
	 * @param eventId
	 *            ID der betrachteten Veranstaltung
	 * @param name
	 *            Name der betrachteten Veranstaltung
	 * @param startDate
	 *            Startdatum der betrachteten Veranstaltung
	 * @param endDate
	 *            Enddatum der betrachteten Veranstaltung
	 * @param breakTime
	 *            Pausenzeit der betrachteten Veranstaltung
	 * @param participantsToCheck
	 *            Zu ueberprufende Teilnehmer
	 * @param collisions
	 *            Liste mit Kollisionen, die gefullt werden sollen
	 */
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

	/**
	 * Ermittelt ob zwischen der betrachteten Veranstaltung und der
	 * Veranstaltung danach Kollisionen aufgrund ueberschneidender Zeiten
	 * entstehen. Ebenfalls wird geschaut, ob die Pausenzeit fuer die Entitaet
	 * ausreicht.
	 * 
	 * 
	 * @param thisEventId
	 *            die ID der betrachteten Veranstaltung
	 * @param thisEventName
	 *            der Name der betrachteten Veranstaltung
	 * @param thisEventStartDate
	 *            das Startdatum der betrachteten Veranstaltung
	 * @param thisEventEndDate
	 *            das Enddatum der betrachteten Veranstaltung
	 * @param thisEventBreakTime
	 *            die Pausenzeit der betrachteten Veranstaltung
	 * @param nextEventId
	 *            die ID der folgenden Veranstaltung
	 * @param nextEventName
	 *            der Name der folgenden Veranstaltung
	 * @param nextEventStartDate
	 *            das Startdatum der folgenden Veranstaltung
	 * @param nextEventEndDate
	 *            das Enddatum der folgenden Veranstaltung
	 * @param nextEventBreakTime
	 *            die Pausenzeit der folgenden Veranstaltung
	 * @param entityName
	 *            Name der Entitaet, die ueberpruft wird
	 * @param breakTimeForEntity
	 *            Pausenzeit der Entitaet, die ueberpruft wird
	 * @param positionOfEventToCheck
	 *            Wert, ob die anzulegende Veranstaltung die betrachtete oder
	 *            die folgende Veranstaltung dieser Methode ist
	 * @param collisions
	 *            Liste mit Kollisionen, die gefuellt wird
	 */
	private void checkCollisionsBetweenEvents(Long thisEventId, String thisEventName, Date thisEventStartDate,
			Date thisEventEndDate, Long thisEventBreakTime, Long nextEventId, String nextEventName,
			Date nextEventStartDate, Date nextEventEndDate, Long nextEventBreakTime, String entityName,
			long breakTimeForEntity, String positionOfEventToCheck, List<String> collisions) {
		// Die freie Zeit zwischen den aufeinander folgenden Veranstaltungen
		long timeBetweenEvents = nextEventStartDate.getTime() - thisEventEndDate.getTime();
		// Pausenzeit der betrachteten Veranstaltung
		long breakTimeForThisEvent = thisEventBreakTime * 1000 * 60;
		// Pausenzeit der folgenden Veranstaltung (schreibt allen Teilnehmern
		// eine Mindest-Pausenzeit vor)
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

	/**
	 * Ermittelt das relevante Datum, welches in der Kollision angezeigt werden
	 * soll. Wird benoetigt, falls Veranstaltungen ueber mehrere Tage gehen.
	 * 
	 * @param thisEventEndDate
	 *            Enddatum der betrachteten Veranstaltung
	 * @param nextEventStartDate
	 *            Startdatum der folgenden Veranstaltung
	 * @param positionOfEventToCheck
	 *            Position der betrachteten Veranstaltung
	 * @return Relevantes Datum, formatiert nach dd.MM.yyyy
	 */
	private String getDate(Date thisEventEndDate, Date nextEventStartDate, String positionOfEventToCheck) {
		Date result;
		if (positionOfEventToCheck.equals(POSITION_BEFORE))
			result = thisEventEndDate;
		else
			result = nextEventStartDate;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(result);
	}

	/**
	 * Ermittelt, welcher DAO angesprochen werden soll.
	 * 
	 * @param className
	 *            Klassenname der betrachteten Entitaet
	 * @return DAO der betrachteten Entitaet
	 */
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