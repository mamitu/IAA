package de.nordakademie.timetableservice.service;

import java.util.Date;
import java.util.List;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.EventType;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;

/**
 * Interface fuer den Veranstaltungen-Service, das alle Methoden bereitstellt,
 * um mit Veranstaltungen zu arbeiten.
 * 
 * @author mm, rs
 */
public interface EventService {

	/**
	 * Instanziiert eine Veranstaltung.
	 * 
	 * @return ein neues Veranstaltungs-Objekt
	 */
	public Event createNewEvent();

	/**
	 * Instanziiert eine Veranstaltung und setzt die uebergebenen Werte.
	 * 
	 * @param name
	 *            Name der neuen Veranstaltung
	 * @param startDate
	 *            Startdatum der neuen Veranstaltung
	 * @param endDate
	 *            Enddatum der neuen Veranstaltung
	 * @param eventType
	 *            Typ der neuen Veranstaltung
	 * @param breakTime
	 *            Pausenzeit der neuen Veranstaltung
	 * @return neue Veranstaltung mit den uebergebenen Werten
	 */
	public Event createNewEvent(String name, Date startDate, Date endDate, EventType eventType, Long breakTime);

	/**
	 * Loescht die Veranstaltung mit der uebergebenen ID
	 * 
	 * @param id
	 *            ID der zu loeschenden Veranstaltung
	 */
	public void deleteEventWithId(Long id);

	/**
	 * Ermittelt eine Liste mit allen Veranstaltungen, bei der die Zenturie
	 * teilnimmt
	 * 
	 * @param centuryId
	 *            ID der Zenturie
	 * @return Liste mit allen Veranstaltungen, bei der die Zenturie teilnimmt
	 */
	public List<Event> findEventsForCentury(Long centuryId);

	/**
	 * Ermittelt eine Liste mit allen Veranstaltungen, bei der alle Zenturien
	 * der Kohorte teilnehmen
	 * 
	 * @param cohortID
	 *            ID der Kohorte
	 * @return Liste mit allen Veranstaltungen, bei der alle Zenturien der
	 *         Kohorte teilnehmen
	 */
	public List<Event> findEventsForCohort(Long cohortId);

	/**
	 * Ermittelt eine Liste mit allen Veranstaltungen, bei der der Dozent
	 * teilnimmt
	 * 
	 * @param lecturerID
	 *            ID des Dozenten
	 * @return Liste mit allen Veranstaltungen, bei der der Dozent teilnimmt
	 */
	public List<Event> findEventsForLecturer(Long lecturerId);

	/**
	 * Ermittelt eine Liste mit allen Veranstaltungen, bei der der Raum
	 * teilnimmt
	 * 
	 * @param roomID
	 *            ID des Raumes
	 * @return Liste mit allen Veranstaltungen, bei der der Raum teilnimmt
	 */
	public List<Event> findEventsForRoom(Long roomId);

	/**
	 * Laedt eine angelegte Veranstaltung aus der Datenbank.
	 * 
	 * @param id
	 *            Die ID der Veranstaltung
	 * @return Die Veranstaltung mit der ID aus der Datenbank oder null, falls
	 *         keine Veranstaltung mit dieser ID existiert
	 */
	public Event load(Long id);

	/**
	 * Laedt eine Liste aller angelegten Veranstaltungen aus der Datenbank. Ist
	 * nach dem Startdatum sortiert
	 * 
	 * @return Liste aller Veranstaltungen
	 */
	public List<Event> loadAll();

	/**
	 * Speichert die Veranstaltung
	 * 
	 * @param event
	 *            Zu speichernde Veranstaltung
	 */
	public void saveEvent(Event event);

	/**
	 * Speichert die Veranstaltung und setzt dabei auch die Referenzen fuer die
	 * teilnehmenden Zenturien, Raeume und Dozenten. Loescht dabei auch
	 * Referenzen, falls beim Editieren einer bestehenden Veranstaltung
	 * Teilnehmer wegfallen.
	 * 
	 * @param event
	 *            Die zu speichernde Veranstaltung
	 * @param numberOfWeeklyRepetitions
	 *            Anzahl an Wiederholungen, in die der Veranstaltung alle 7 Tage
	 *            wiederholt werden soll
	 * @param roomsToUpdate
	 *            Die teilnehmenden Raeume
	 * @param lecturersToUpdate
	 *            Die teilnehmenden Dozenten
	 * @param centuriesToUpdate
	 *            Die teilnehmenden Zenturien
	 */
	public void saveReferencesAndEvent(Event event, int numberOfWeeklyRepetitions, List<Room> roomsToUpdate,
			List<Lecturer> lecturersToUpdate, List<Century> centuriesToUpdate);

	/**
	 * Laedt die Veranstaltung mit der uebergebenden ID aus der Datenbank und
	 * setzt die uebergebenen Werte an der Veranstaltung. Gibt sie zurueck
	 * 
	 * @param eventId
	 *            ID der zu ladenden Veranstaltung
	 * @param startDate
	 *            Neues Startdatum der Veranstaltung
	 * @param endDate
	 *            Neues Enddatum der Veranstaltung
	 * @param breakTime
	 *            Neue Pausenzeit der Veranstaltung
	 * @return Die aktualisierte Veranstaltung
	 */
	public Event updateEvent(Long eventId, Date startDate, Date endDate, Long breakTime);

}