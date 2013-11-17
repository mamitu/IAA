package de.nordakademie.timetableservice.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;

public class CenturyDAO extends EventParticipantDAO {

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
		Hibernate.initialize(century.getName());
		Hibernate.initialize(century.getCohort());
		Hibernate.initialize(century.getNumberOfStudents());
		Hibernate.initialize(century.getBreakTime());
		Hibernate.initialize(century.getEvents());
		return century;
	}

	@SuppressWarnings("unchecked")
	public List<Century> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Century").list();
	}

	@SuppressWarnings("unchecked")
	public List<Century> findCenturiesByEvent(Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		List<Century> centuries = session.createQuery("select century from Century century join century.events event where event.id = :eventId").setParameter("eventId", eventId)
				.list();
		return centuries;
	}

	@SuppressWarnings("unchecked")
	public List<Century> findCenturiesByName(String centuryName) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("select century from Century as century where century.name = :centuryName").setString("centuryName", centuryName).list();
	}

	@SuppressWarnings("unchecked")
	public List<Event> findEventsForCentury(Long centuryId) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("select event from Century century join century.events event where century.id = :centuryId").setParameter("centuryId", centuryId).list();
	}

	@SuppressWarnings("unchecked")
	public List<Century> findCenturiesByCohort(Long cohortId) {
		Session session = sessionFactory.getCurrentSession();
		List<Century> centuries = session.createQuery("select century from Century century join century.cohort cohort where cohort.id = :cohortId")
				.setParameter("cohortId", cohortId).list();
		return centuries;
	}

	@Override
	protected String getTableName() {
		return Century.class.getName();
	}

	@Override
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}