package de.nordakademie.timetableservice.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Event;

/**
 * Data Access Object fuer Veranstaltungen.
 * 
 * @author
 * 
 */
public class EventDAO {

	/**
	 * Die Hibernate Session Factory
	 */
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Loescht die Veranstaltung mit der uebergebenen ID
	 * 
	 * @param id
	 *            ID der zu loeschenden Veranstaltung
	 */
	public void deleteEventWithId(Long id) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(load(id));
	}

	/**
	 * Ermittelt die Veranstaltung, die als naechstes auf das uebergebene
	 * Enddatum fuer die Zenturie folgt
	 * 
	 * @param centuryId
	 *            ID der Zenturie
	 * @param endDate
	 *            Enddatum
	 * @return Veranstaltung, die als naechstes auf das uebergebene Enddatum
	 *         fuer die Zenturie folgt
	 */
	@SuppressWarnings("unchecked")
	public Event findClosestEventAfterDateForCentury(Long centuryId, Date endDate) {
		Session session = sessionFactory.getCurrentSession();
		List<Event> eventsAfterEndDate = (List<Event>) session
				.createQuery(
						"select event from Event event join event.centuries century where century.id = :centuryId and event.startDate > :endDate order by event.startDate asc")
				.setTimestamp("endDate", endDate).setParameter("centuryId", centuryId).list();
		return eventsAfterEndDate.size() == 0 ? null : eventsAfterEndDate.get(0);
	}

	/**
	 * Ermittelt die Veranstaltung, die als naechstes auf das uebergebene
	 * Enddatum fuer den Dozenten folgt
	 * 
	 * @param lecturerID
	 *            ID des Dozenten
	 * @param endDate
	 *            Enddatum
	 * @return Veranstaltung, die als naechstes auf das uebergebene Enddatum
	 *         fuer den Dozenten folgt
	 */
	@SuppressWarnings("unchecked")
	public Event findClosestEventAfterDateForLecturer(Long lecturerId, Date endDate) {
		Session session = sessionFactory.getCurrentSession();
		List<Event> eventsAfterEndDate = (List<Event>) session
				.createQuery(
						"select event from Event event join event.lecturers lecturer where lecturer.id = :lecturerId and event.startDate > :endDate order by event.startDate asc")
				.setTimestamp("endDate", endDate).setParameter("lecturerId", lecturerId).list();
		return eventsAfterEndDate.size() == 0 ? null : eventsAfterEndDate.get(0);
	}

	/**
	 * Ermittelt die Veranstaltung, die als naechstes auf das uebergebene
	 * Enddatum fuer den Raum folgt
	 * 
	 * @param roomID
	 *            ID des Raumes
	 * @param endDate
	 *            Enddatum
	 * @return Veranstaltung, die als naechstes auf das uebergebene Enddatum
	 *         fuer den Raum folgt
	 */
	@SuppressWarnings("unchecked")
	public Event findClosestEventAfterDateForRoom(Long roomId, Date endDate) {
		Session session = sessionFactory.getCurrentSession();
		List<Event> eventsAfterEndDate = (List<Event>) session
				.createQuery(
						"select event from Event event join event.rooms room where room.id = :roomId and event.startDate > :endDate order by event.startDate asc")
				.setTimestamp("endDate", endDate).setParameter("roomId", roomId).list();
		return eventsAfterEndDate.size() == 0 ? null : eventsAfterEndDate.get(0);
	}

	/**
	 * Ermittelt die Veranstaltung, die vor dem uebergebenen Startdatum fuer die
	 * Zenturie liegt
	 * 
	 * @param centuryId
	 *            ID der Zenturie
	 * @param startDate
	 *            Startdatum
	 * @return Veranstaltung, die vor dem uebergebenen Startdatum fuer die
	 *         Zenturie liegt
	 */
	@SuppressWarnings("unchecked")
	public Event findClosestEventBeforeDateForCentury(Long centuryId, Date startDate) {
		Session session = sessionFactory.getCurrentSession();
		List<Event> eventsBeforeStartDate = (List<Event>) session
				.createQuery(
						"select event from Event event join event.centuries century where century.id = :centuryId and event.endDate < :startDate order by event.endDate desc")
				.setTimestamp("startDate", startDate).setParameter("centuryId", centuryId).list();
		return eventsBeforeStartDate.size() == 0 ? null : eventsBeforeStartDate.get(0);
	}

	/**
	 * Ermittelt die Veranstaltung, die vor dem uebergebenen Startdatum fuer den
	 * Dozenten liegt
	 * 
	 * @param lecturerID
	 *            ID des Dozenten
	 * @param startDate
	 *            Startdatum
	 * @return Veranstaltung, die vor dem uebergebenen Startdatum fuer den
	 *         Dozenten liegt
	 */
	@SuppressWarnings("unchecked")
	public Event findClosestEventBeforeDateForLecturer(Long lecturerId, Date startDate) {
		Session session = sessionFactory.getCurrentSession();
		List<Event> eventsBeforeStartDate = (List<Event>) session
				.createQuery(
						"select event from Event event join event.lecturers lecturer where lecturer.id = :lecturerId and event.endDate < :startDate order by event.endDate desc")
				.setTimestamp("startDate", startDate).setParameter("lecturerId", lecturerId).list();
		return eventsBeforeStartDate.size() == 0 ? null : eventsBeforeStartDate.get(0);
	}

	/**
	 * Ermittelt die Veranstaltung, die vor dem uebergebenen Startdatum fuer den
	 * Raum liegt
	 * 
	 * @param roomID
	 *            ID des Raumes
	 * @param startDate
	 *            Startdatum
	 * @return Veranstaltung, die vor dem uebergebenen Startdatum fuer den Raum
	 *         liegt
	 */
	@SuppressWarnings("unchecked")
	public Event findClosestEventBeforeDateForRoom(Long roomId, Date startDate) {
		Session session = sessionFactory.getCurrentSession();
		List<Event> eventsBeforeStartDate = (List<Event>) session
				.createQuery(
						"select event from Event event join event.rooms room where room.id = :roomId and event.endDate < :startDate order by event.endDate desc")
				.setTimestamp("startDate", startDate).setParameter("roomId", roomId).list();
		return eventsBeforeStartDate.size() == 0 ? null : eventsBeforeStartDate.get(0);
	}

	/**
	 * Laedt alle Veranstaltungen der Zenturie mit der uebergebenen ID
	 * 
	 * @param centuryId
	 *            ID der Zenturie
	 * @return Liste mit allen Veranstaltungen der Zenturie
	 */
	@SuppressWarnings("unchecked")
	public List<Event> findEventsForCentury(Long centuryId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Event>) session
				.createQuery(
						"select event from Event event join event.centuries century where century.id = :centuryId order by event.endDate asc")
				.setParameter("centuryId", centuryId).list();
	}

	/**
	 * Laedt alle Veranstaltungen der Kohorte mit der uebergebenen ID
	 * 
	 * @param cohortID
	 *            ID der Kohorte
	 * @return Liste mit allen Veranstaltungen der Kohorte
	 */
	@SuppressWarnings("unchecked")
	public List<Event> findEventsForCohort(Long cohortId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Event>) session
				.createQuery(
						"select distinct event from Event event join event.centuries century join century.cohort cohort where cohort.id = :cohortId order by event.startDate asc")
				.setParameter("cohortId", cohortId).list();
	}

	/**
	 * Laedt alle Veranstaltung des Dozenten mit der uebergebenen ID
	 * 
	 * @param lecturerID
	 *            ID des Dozenten
	 * @return Liste mit allen Veranstaltungen des Dozenten
	 */
	@SuppressWarnings("unchecked")
	public List<Event> findEventsForLecturer(Long lecturerId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Event>) session
				.createQuery(
						"select event from Event event join event.lecturers lecturer where lecturer.id = :lecturerId order by event.endDate asc")
				.setParameter("lecturerId", lecturerId).list();
	}

	/**
	 * Laedt alle Veranstaltung des Raumes mit der uebergebenen ID
	 * 
	 * @param roomID
	 *            ID des Raumes
	 * @return Liste mit allen Veranstaltungen des Raumes
	 */
	@SuppressWarnings("unchecked")
	public List<Event> findEventsForRoom(Long roomId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Event>) session
				.createQuery(
						"select event from Event event join event.rooms room where room.id = :roomId order by event.endDate asc")
				.setParameter("roomId", roomId).list();
	}

	/**
	 * Laedt die Veranstaltung mit der uebergebenen ID aus der Datenbank
	 * 
	 * @param id
	 *            ID der Veranstaltung
	 * @return Veranstaltung mit der uebergebenen ID oder null, falls keine
	 *         Veranstaltung zu der ID gefunden werden konnte
	 */
	public Event load(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Event event = (Event) session.get(Event.class, id);
		Hibernate.initialize(event.getName());
		Hibernate.initialize(event.getStartDate());
		Hibernate.initialize(event.getEndDate());
		Hibernate.initialize(event.getLecturers());
		Hibernate.initialize(event.getCenturies());
		Hibernate.initialize(event.getRooms());
		Hibernate.initialize(event.getBreakTime());
		Hibernate.initialize(event.getEventType());
		return event;
	}

	/**
	 * Laedt eine Liste mit allen in der Datenbank vorhandenen Veranstaltungen
	 * 
	 * @return Liste aller angelegten Veranstaltungen
	 */
	@SuppressWarnings("unchecked")
	public List<Event> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Event event order by startDate asc").list();
	}

	/**
	 * Persistiert (erzeugt oder aktualisiert) eine Veranstaltung
	 * 
	 * @param event
	 *            Veranstaltung, die persitiert werden soll
	 */
	public void save(Event event) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(event);
	}

}