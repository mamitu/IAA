package de.nordakademie.timetableservice.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Lecturer;

public class LecturerDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(Lecturer lecturer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(lecturer);
	}

	/**
	 * Loads an existing single entity from the database.
	 * 
	 * @param id
	 *            The identifier.
	 * @return either a persistent object or {@code null} if not entity was
	 *         found with the given identifier.
	 */
	public Lecturer load(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Lecturer lecturer = (Lecturer) session.get(Lecturer.class, id);
		// This is only of the possible solutions to lazy initialization
		// failures. Please note that a "join fetch" in a HQL query is the
		// better solution performance-wise.
		Hibernate.initialize(lecturer.getFirstName());
		Hibernate.initialize(lecturer.getLastName());
		Hibernate.initialize(lecturer.getBreakTime());
		Hibernate.initialize(lecturer.getEventsOfLecturer());
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
	public Set<Lecturer> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return new HashSet<Lecturer>(session.createQuery("from Lecturer").list());
	}

	@SuppressWarnings("unchecked")
	public Set<Lecturer> findLecturersByEvent(Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		Set<Lecturer> lecturers = new HashSet<Lecturer>(session
				.createQuery("select lecturer from Lecturer lecturer join lecturer.eventsOfLecturer event where event.id = :eventId").setParameter("eventId", eventId).list());
		return lecturers;
	}

	@SuppressWarnings("unchecked")
	public List<Lecturer> findLecturersByEmailAddress(String emailAddress) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Lecturer>) session.createQuery("select lecturer from Lecturer as lecturer where lecturer.emailAddress = :emailAddress")
				.setString("emailAddress", emailAddress).list();
	}
}
