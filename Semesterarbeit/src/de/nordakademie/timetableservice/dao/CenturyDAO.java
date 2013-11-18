package de.nordakademie.timetableservice.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Century;

/**
 * Data Access Object fuer Zenturien.
 * 
 * @author
 * 
 */
public class CenturyDAO extends EventParticipantDAO {

	/**
	 * Die Hibernate Session Factory
	 */
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Persistiert (erzeugt oder aktualisiert) eine Zenturie
	 * 
	 * @param century
	 *            Zenturie, die persitiert werden soll
	 */
	public void save(Century century) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(century);
	}

	/**
	 * Laedt die Zenturie mit der uebergebenen ID aus der Datenbank
	 * 
	 * @param id
	 *            ID der Zenturie
	 * @return Zenturie mit der uebergebenen ID oder null, falls keine Zenturie
	 *         zu der ID gefunden werden konnte
	 */
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

	/**
	 * Laedt eine Liste mit allen in der Datenbank vorhandenen Zenturien
	 * 
	 * @return Liste aller angelegten Zenturien
	 */
	@SuppressWarnings("unchecked")
	public List<Century> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Century").list();
	}

	/**
	 * Laedt die Zenturien mit dem uebergebenen Namen aus der Datenbank
	 * 
	 * @param centuryName
	 *            Name der Zenturie
	 * @return Liste mit Zenturien, die den uebergebenen Namen haben
	 */
	@SuppressWarnings("unchecked")
	public List<Century> findCenturiesByName(String centuryName) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("select century from Century as century where century.name = :centuryName")
				.setString("centuryName", centuryName).list();
	}

	/**
	 * Laedt alle Zenturien, die der Kohorte mit der uebergebenen ID angehoeren
	 * 
	 * @param cohortId
	 *            ID der Kohorte
	 * @return Liste mit Zenturien der Kohorte
	 */
	@SuppressWarnings("unchecked")
	public List<Century> findCenturiesByCohort(Long cohortId) {
		Session session = sessionFactory.getCurrentSession();
		List<Century> centuries = session
				.createQuery(
						"select century from Century as century left join fetch century.events as event join century.cohort as cohort where cohort.id = :cohortId")
				.setParameter("cohortId", cohortId).list();
		return centuries;
	}

	/**
	 * Gibt den Tabellennamen der Zenturie zurueck
	 */
	@Override
	protected String getTableName() {
		return Century.class.getName();
	}

	/**
	 * Gibt die Session zurueck
	 */
	@Override
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}