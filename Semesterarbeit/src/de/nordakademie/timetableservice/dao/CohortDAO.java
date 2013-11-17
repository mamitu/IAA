package de.nordakademie.timetableservice.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.model.FieldOfStudy;

public class CohortDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(Cohort cohort) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(cohort);
	}

	public Cohort load(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Cohort cohort = (Cohort) session.get(Cohort.class, id);
		// This is only of the possible solutions to lazy initialization
		// failures. Please note that a "join fetch" in a HQL query is the
		// better solution performance-wise.
		Hibernate.initialize(cohort.getFieldOfStudy());
		Hibernate.initialize(cohort.getYear());
		Hibernate.initialize(cohort.getCenturies());
		return cohort;
	}

	@SuppressWarnings("unchecked")
	public List<Cohort> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Cohort").list();
	}

	@SuppressWarnings("unchecked")
	public List<Cohort> findCohortsByFieldOfStudyAndYear(FieldOfStudy fieldOfStudy, int year) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Cohort cohort where cohort.fieldOfStudy = :fieldOfStudy and cohort.year = :year").setParameter("fieldOfStudy", fieldOfStudy)
				.setParameter("year", year).list();
	}

}