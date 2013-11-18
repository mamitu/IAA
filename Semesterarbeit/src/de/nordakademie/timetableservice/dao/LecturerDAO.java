package de.nordakademie.timetableservice.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Lecturer;

/**
 * Data Access Object fuer Dozenten.
 * 
 * @author
 * 
 */
public class LecturerDAO extends EventParticipantDAO {

	/**
	 * Die Hibernate Session Factory
	 */
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Laedt die Dozenten mit der uebergebenen email Adresse aus der Datenbank
	 * 
	 * @param emailAddress
	 *            email Adresse des Dozenten
	 * @return Liste mit Dozenten, die die uebergebene email Adresse haben
	 */
	@SuppressWarnings("unchecked")
	public List<Lecturer> findLecturersByEmailAddress(String emailAddress) {
		Session session = sessionFactory.getCurrentSession();
		return session
				.createQuery("select lecturer from Lecturer as lecturer where lecturer.emailAddress = :emailAddress")
				.setString("emailAddress", emailAddress).list();
	}

	/**
	 * Gibt die Session zurueck
	 */
	@Override
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Gibt den Tabellennamen des Dozenten zurueck
	 */
	@Override
	protected String getTableName() {
		return Lecturer.class.getName();
	}

	/**
	 * Laedt den Dozenten mit der uebergebenen ID aus der Datenbank
	 * 
	 * @param id
	 *            ID des Dozenten
	 * @return Dozent mit der uebergebenen ID oder null, falls kein Dozent zu
	 *         der ID gefunden werden konnte
	 */
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
	 * Laedt eine Liste mit allen in der Datenbank vorhandenen Dozenten
	 * 
	 * @return Liste aller angelegten Dozenten
	 */
	@SuppressWarnings("unchecked")
	public List<Lecturer> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Lecturer").list();
	}

	/**
	 * Persistiert (erzeugt oder aktualisiert) einen Dozenten
	 * 
	 * @param lecturer
	 *            Dozent, der persitiert werden soll
	 */
	public void save(Lecturer lecturer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(lecturer);
	}

}