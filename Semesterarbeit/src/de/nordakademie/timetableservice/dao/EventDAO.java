package de.nordakademie.timetableservice.dao;

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
		Hibernate.initialize(event.getNumberOfRepetitions());
		Hibernate.initialize(event.getRooms());
		return event;
	}

	@SuppressWarnings("unchecked")
	public List<Event> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Event").list();
	}

}
