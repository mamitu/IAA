package de.nordakademie.timetableservice.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;

public class CenturyDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(Century century) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(century);
	}

	public Century load(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Century century = (Century) session.get(Century.class, id);
		// This is only of the possible solutions to lazy initialization
		// failures. Please note that a "join fetch" in a HQL query is the
		// better solution performance-wise.
		Hibernate.initialize(century.getName());
		Hibernate.initialize(century.getNumberOfStudents());
		Hibernate.initialize(century.getBreakTime());
		Hibernate.initialize(century.getEventsOfCentury());
		return century;
	}

	@SuppressWarnings("unchecked")
	public Set<Century> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return new HashSet<Century>(session.createQuery("from Century").list());
	}

	@SuppressWarnings("unchecked")
	public Set<Century> findCenturiesByEvent(Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		Set<Century> centuries = new HashSet<Century>(session.createQuery("select century from Century century join century.eventsOfCentury event where event.id = :eventId")
				.setParameter("eventId", eventId).list());
		return centuries;
	}

	@SuppressWarnings("unchecked")
	public Set<Century> findCenturiesWithDatesWithoutId(Date startDate, Date endDate, Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		Set<Century> centuries = new HashSet<Century>();
		getCenturiesWithStartDateBetweenExistingEvent(startDate, eventId, session, centuries);
		getCenturiesWithEndDateBetweenExistingEvent(endDate, eventId, session, centuries);
		getCenturiesWithDatesAroundExistingEvent(startDate, endDate, eventId, session, centuries);
		return centuries;
	}

	@SuppressWarnings("unchecked")
	private void getCenturiesWithDatesAroundExistingEvent(Date startDate, Date endDate, Long eventId, Session session, Set<Century> centuries) {
		if (eventId == null) {
			List<Century> centuryList = (List<Century>) session
					.createQuery("select century from Century century join century.eventsOfCentury event where :startDate <= event.startDate and :endDate >= event.endDate")
					.setTimestamp("endDate", endDate).setTimestamp("startDate", startDate).list();
			centuries.addAll(centuryList);
		} else {
			List<Century> centuryList = (List<Century>) session
					.createQuery(
							"select century from Century century join century.eventsOfCentury event where event.id != :eventId and :startDate <= event.startDate and :endDate >= event.endDate")
					.setTimestamp("endDate", endDate).setTimestamp("startDate", startDate).setParameter("eventId", eventId).list();
			centuries.addAll(centuryList);

		}
	}

	@SuppressWarnings("unchecked")
	private void getCenturiesWithEndDateBetweenExistingEvent(Date endDate, Long eventId, Session session, Set<Century> centuries) {
		if (eventId == null) {
			List<Century> centuryList = (List<Century>) session
					.createQuery("select century from Century century join century.eventsOfCentury event where :endDate between event.startDate and event.endDate")
					.setTimestamp("endDate", endDate).list();
			centuries.addAll(centuryList);
		} else {
			List<Century> centuryList = (List<Century>) session
					.createQuery(
							"select century from Century century join century.eventsOfCentury event where event.id != :eventId and :endDate between event.startDate and event.endDate")
					.setTimestamp("endDate", endDate).setParameter("eventId", eventId).list();
			centuries.addAll(centuryList);

		}
	}

	@SuppressWarnings("unchecked")
	private void getCenturiesWithStartDateBetweenExistingEvent(Date startDate, Long eventId, Session session, Set<Century> centuries) {
		if (eventId == null) {
			List<Century> centuryList = (List<Century>) session
					.createQuery("select century from Century century join century.eventsOfCentury event where :startDate between event.startDate and event.endDate")
					.setTimestamp("startDate", startDate).list();
			centuries.addAll(centuryList);
		} else {
			List<Century> centuryList = (List<Century>) session
					.createQuery(
							"select century from Century century join century.eventsOfCentury event where event.id != :eventId and :startDate between event.startDate and event.endDate")
					.setTimestamp("startDate", startDate).setParameter("eventId", eventId).list();
			centuries.addAll(centuryList);
		}
	}

	public List<Century> findCenturiesByName(String centuryName) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Century>) session.createQuery("select century from Century as century where century.name = :centuryName").setString("centuryName", centuryName).list();
	}

	public List<Century> findCenturiesByNameWithoutId(String centuryName, Long centuryId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Century>) session.createQuery("select century from Century as century where century.name = :centuryName and century.id != :centuryId")
				.setString("centuryName", centuryName).setParameter("centuryId", centuryId).list();
	}

	public List<Event> findEventsForCentury(Long centuryId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Event>) session.createQuery("select event from Century century join century.events event where century.id = :centuryId").setParameter("centuryId", centuryId)
				.list();
	}

	@SuppressWarnings("unchecked")
	public Set<Century> findCenturiesByCohort(Long cohortId) {
		Session session = sessionFactory.getCurrentSession();
		Set<Century> centuries = new HashSet<Century>(session.createQuery("select century from Century century join century.cohort cohort where cohort.id = :cohortId")
				.setParameter("cohortId", cohortId).list());
		return centuries;
	}

}