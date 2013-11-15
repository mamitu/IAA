package de.nordakademie.timetableservice.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Cohort;

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
	public Set<Cohort> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return new HashSet<Cohort>(session.createQuery("from Cohort").list());
	}

}