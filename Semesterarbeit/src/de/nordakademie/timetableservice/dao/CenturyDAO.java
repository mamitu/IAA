package de.nordakademie.timetableservice.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Century;

public class CenturyDAO {

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
		// This is only of the possible solutions to lazy initialization
		// failures. Please note that a "join fetch" in a HQL query is the
		// better solution performance-wise.
		Hibernate.initialize(century.getName());
		Hibernate.initialize(century.getNumberOfStudents());
		Hibernate.initialize(century.getBreakTime());
		return century;
	}

	@SuppressWarnings("unchecked")
	public List<Century> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Century").list();
	}

}
