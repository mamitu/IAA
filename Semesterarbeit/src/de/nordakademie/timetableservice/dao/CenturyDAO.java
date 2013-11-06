package de.nordakademie.timetableservice.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Century;

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
	public List<Century> findCenturiesByDatesWithoutId(Date startDate, Date endDate, Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		List<Century> centuries = (List<Century>) session
				.createQuery(
						"select century from Century century join century.eventsOfCentury event where event.id = :eventId and event.startDate < :endDate and event.endDate > :startDate")
				.setTimestamp("endDate", endDate).setTimestamp("startDate", startDate).setParameter("eventId", eventId).list();
		return centuries;
	}

}