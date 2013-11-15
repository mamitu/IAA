package de.nordakademie.timetableservice.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Event;

public class EventDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(Event event) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(event);
	}

	public Event load(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Event event = (Event) session.get(Event.class, id);
		// This is only of the possible solutions to lazy initialization
		// failures. Please note that a "join fetch" in a HQL query is the
		// better solution performance-wise.
		Hibernate.initialize(event.getName());
		Hibernate.initialize(event.getStartDate());
		Hibernate.initialize(event.getEndDate());
		Hibernate.initialize(event.getLecturers());
		Hibernate.initialize(event.getCenturies());
		Hibernate.initialize(event.getRooms());
		return event;
	}

	@SuppressWarnings("unchecked")
	public List<Event> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return new LinkedList<Event>(session.createQuery("from Event event order by startDate asc").list());
	}

	public void deleteEventWithId(Long id) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(load(id));
	}

	@SuppressWarnings("unchecked")
	public Event findClosestEventAfterDateForLecturer(Long lecturerId, Date endDate, Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		List<Event> eventsAfterEndDate = (List<Event>) session
				.createQuery(
						"select event from Event event join event.lecturers lecturer where lecturer.id = :lecturerId and event.startDate > :endDate order by event.startDate asc")
				.setTimestamp("endDate", endDate).setParameter("lecturerId", lecturerId).list();
		return eventsAfterEndDate.size() == 0 ? null : eventsAfterEndDate.get(0);
	}

	public Event findClosestEventAfterDateForCentury(Long centuryId, Date endDate, Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		List<Event> eventsAfterEndDate = (List<Event>) session
				.createQuery("select event from Event event join event.centuries century where century.id = :centuryId and event.startDate > :endDate order by event.startDate asc")
				.setTimestamp("endDate", endDate).setParameter("centuryId", centuryId).list();
		return eventsAfterEndDate.size() == 0 ? null : eventsAfterEndDate.get(0);
	}

	public Event findClosestEventAfterDateForRoom(Long roomId, Date endDate, Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		List<Event> eventsAfterEndDate = (List<Event>) session
				.createQuery("select event from Event event join event.rooms room where room.id = :roomId and event.startDate > :endDate order by event.startDate asc")
				.setTimestamp("endDate", endDate).setParameter("roomId", roomId).list();
		return eventsAfterEndDate.size() == 0 ? null : eventsAfterEndDate.get(0);
	}

	public Event findClosestEventBeforeDateForLecturer(Long lecturerId, Date startDate, Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		List<Event> eventsBeforeStartDate = (List<Event>) session
				.createQuery(
						"select event from Event event join event.lecturers lecturer where lecturer.id = :lecturerId and event.endDate < :startDate order by event.endDate desc")
				.setTimestamp("startDate", startDate).setParameter("lecturerId", lecturerId).list();
		return eventsBeforeStartDate.size() == 0 ? null : eventsBeforeStartDate.get(0);
	}

	public Event findClosestEventBeforeDateForCentury(Long centuryId, Date startDate, Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		List<Event> eventsBeforeStartDate = (List<Event>) session
				.createQuery("select event from Event event join event.centuries century where century.id = :centuryId and event.endDate < :startDate order by event.endDate desc")
				.setTimestamp("startDate", startDate).setParameter("centuryId", centuryId).list();
		return eventsBeforeStartDate.size() == 0 ? null : eventsBeforeStartDate.get(0);
	}

	public Event findClosestEventBeforeDateForRoom(Long roomId, Date startDate, Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		List<Event> eventsBeforeStartDate = (List<Event>) session
				.createQuery("select event from Event event join event.rooms room where room.id = :roomId and event.endDate < :startDate order by event.endDate desc")
				.setTimestamp("startDate", startDate).setParameter("roomId", roomId).list();
		return eventsBeforeStartDate.size() == 0 ? null : eventsBeforeStartDate.get(0);
	}

	public List<Event> findEventsByName(String eventName) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Event>) session.createQuery("select event from Event as event where event.name = :eventName").setString("eventName", eventName).list();
	}

	public List<Event> findEventsByNameWithoutId(String eventName, Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Event>) session.createQuery("select event from Event as event where event.name = :eventName and event.id != :eventId").setString("eventName", eventName)
				.setParameter("eventId", eventId).list();
	}

	public List<Event> findEventsForCentury(Long centuryId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Event>) session.createQuery("select event from Event event join event.centuries century where century.id = :centuryId order by event.endDate asc")
				.setParameter("centuryId", centuryId).list();
	}

	public List<Event> findEventsForLecturer(Long lecturerId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Event>) session.createQuery("select event from Event event join event.lecturers lecturer where lecturer.id = :lecturerId order by event.endDate asc")
				.setParameter("lecturerId", lecturerId).list();
	}

	public List<Event> findEventsForRoom(Long roomId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Event>) session.createQuery("select event from Event event join event.rooms room where room.id = :roomId order by event.endDate asc")
				.setParameter("roomId", roomId).list();
	}

	public List<Event> findEventsForCohort(Long cohortId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Event>) session
				.createQuery(
						"select distinct event from Event event join event.centuries century join century.cohort cohort where cohort.id = :cohortId order by event.startDate asc")
				.setParameter("cohortId", cohortId).list();
	}

}