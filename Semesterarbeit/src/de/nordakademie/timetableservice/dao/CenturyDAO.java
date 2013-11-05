package de.nordakademie.timetableservice.dao;

import java.util.HashSet;
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
		session.merge(century);
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
		Hibernate.initialize(century.getEvents());
		return century;
	}

	@SuppressWarnings("unchecked")
	public Set<Century> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return new HashSet<Century>(session.createQuery("from Century").list());
	}

	public Set<Century> findCenturiesByEvent(Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Set<Century> centuries = new HashSet<Century>(session.createQuery("select century from Century century join century.events event where event.id = :eventId")
				.setParameter("eventId", eventId).list());
		return centuries;
	}

}
