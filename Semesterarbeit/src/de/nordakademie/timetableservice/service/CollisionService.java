package de.nordakademie.timetableservice.service;

import java.util.Date;
import java.util.List;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;

/**
 * Interface fuer den Kollisionen-Service, das alle Methoden bereitstellt, um
 * mit Kohorten zu arbeiten.
 * 
 * @author
 */
public interface CollisionService {

	/**
	 * Ermittelt alle Kollisionen, die beim Anlegen der Veranstaltung enstehen
	 * wuerden. und liefert sie in einer Liste zurueck.
	 * 
	 * @param eventId
	 *            ID der Veranstaltung, die bearbeitet wird (null, wenn neue
	 *            Veranstaltung)
	 * @param eventName
	 *            Name der Veranstaltung, die bearbeitet wird
	 * @param startDate
	 *            Startdatum der anzulegenden Veranstaltung
	 * @param endDate
	 *            Enddatum der anzulegenden Veranstaltung
	 * @param breakTime
	 *            Pausenzeit der anzulegenden Veranstaltung
	 * @param centuriesToCheck
	 *            Zenturien, die an der Veranstaltung teilnehmen sollen und
	 *            ueberprueft werden muessen
	 * @param lecturersToCheck
	 *            Dozenten, die an der Veranstaltung teilnehmen sollen und
	 *            ueberprueft werden muessen
	 * @param roomsToCheck
	 *            Raeume, die an der Veranstaltung teilnehmen sollen und
	 *            ueberprueft werden muessen
	 * @return
	 */
	public List<String> getCollisions(Long eventId, String eventName, Date startDate, Date endDate, Long breakTime,
			List<Century> centuriesToCheck, List<Lecturer> lecturersToCheck, List<Room> roomsToCheck);

}