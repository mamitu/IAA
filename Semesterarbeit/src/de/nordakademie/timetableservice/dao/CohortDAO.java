package de.nordakademie.timetableservice.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.model.FieldOfStudy;

/**
 * Data Access Object fuer Kohorten.
 * 
 * @author rs
 * 
 */
public class CohortDAO {

	/**
	 * Die Hibernate Session Factory
	 */
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Persistiert (erzeugt oder aktualisiert) eine Kohorte
	 * 
	 * @param cohort
	 *            Kohorte, die persitiert werden soll
	 */
	public void save(Cohort cohort) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(cohort);
	}

	/**
	 * Laedt die Kohorte mit der uebergebenen ID aus der Datenbank
	 * 
	 * @param id
	 *            ID der Kohorte
	 * @return Kohorte mit der uebergebenen ID oder null, falls keine Kohorte zu
	 *         der ID gefunden werden konnte
	 */
	public Cohort load(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Cohort cohort = (Cohort) session.get(Cohort.class, id);
		Hibernate.initialize(cohort.getFieldOfStudy());
		Hibernate.initialize(cohort.getYear());
		Hibernate.initialize(cohort.getCenturies());
		return cohort;
	}

	/**
	 * Laedt eine Liste mit allen in der Datenbank vorhandenen Kohorten
	 * 
	 * @return Liste aller angelegten Kohorten
	 */
	@SuppressWarnings("unchecked")
	public List<Cohort> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Cohort").list();
	}

	/**
	 * Laedt alle Kohorten, die zum uebergebenen Jahrgang und Studienrichtung
	 * gehoeren
	 * 
	 * @param fieldOfStudy
	 *            Studienrichtung der Kohorte
	 * @param year
	 *            Jahrgang der Kohorte
	 * @return Liste mit Kohorten, zum uebergebenen Jahrgang und Studienrichtung
	 *         gehoeren
	 */
	@SuppressWarnings("unchecked")
	public List<Cohort> findCohortsByFieldOfStudyAndYear(FieldOfStudy fieldOfStudy, int year) {
		Session session = sessionFactory.getCurrentSession();
		return session
				.createQuery("from Cohort cohort where cohort.fieldOfStudy = :fieldOfStudy and cohort.year = :year")
				.setParameter("fieldOfStudy", fieldOfStudy).setParameter("year", year).list();
	}

}