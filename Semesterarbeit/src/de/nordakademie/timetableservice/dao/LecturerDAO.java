package de.nordakademie.timetableservice.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Lecturer;

public class LecturerDAO extends EventParticipantDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Lecturer> findLecturersByEmailAddress(String emailAddress) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("select lecturer from Lecturer as lecturer where lecturer.emailAddress = :emailAddress").setString("emailAddress", emailAddress).list();
	}

	@SuppressWarnings("unchecked")
	public List<Lecturer> findLecturersByEvent(Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		List<Lecturer> lecturers = session.createQuery("select lecturer from Lecturer lecturer join lecturer.events event where event.id = :eventId")
				.setParameter("eventId", eventId).list();
		return lecturers;
	}

	@Override
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	protected String getTableName() {
		return Lecturer.class.getName();
	}

	public Lecturer load(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Lecturer lecturer = (Lecturer) session.get(Lecturer.class, id);
		Hibernate.initialize(lecturer.getFirstName());
		Hibernate.initialize(lecturer.getLastName());
		Hibernate.initialize(lecturer.getBreakTime());
		Hibernate.initialize(lecturer.getEvents());
		Hibernate.initialize(lecturer.getEmailAddress());
		return lecturer;
	}

	/**
	 * Loads a list of all single entities in the database.
	 * 
	 * @return a list of single entities. If no single is stored in the database
	 *         an empty list is returned.
	 */
	@SuppressWarnings("unchecked")
	public List<Lecturer> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Lecturer").list();
	}

	public void save(Lecturer lecturer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(lecturer);
	}

}