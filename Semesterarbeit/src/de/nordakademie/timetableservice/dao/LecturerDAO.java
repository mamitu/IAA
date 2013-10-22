package de.nordakademie.timetableservice.dao;

import java.util.List;

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

}
