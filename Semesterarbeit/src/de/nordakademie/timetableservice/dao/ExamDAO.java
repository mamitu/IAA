package de.nordakademie.timetableservice.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Exam;

public class ExamDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(Exam exam) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(exam);
	}

	public Exam load(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Exam exam = (Exam) session.get(Exam.class, id);
		// This is only of the possible solutions to lazy initialization
		// failures. Please note that a "join fetch" in a HQL query is the
		// better solution performance-wise.
		Hibernate.initialize(exam.getName());
		Hibernate.initialize(exam.getStartDate());
		Hibernate.initialize(exam.getEndDate());
		Hibernate.initialize(exam.getLecturers());
		Hibernate.initialize(exam.getNumberOfRepetitions());
		Hibernate.initialize(exam.getRooms());
		Hibernate.initialize(exam.getCenturies());
		return exam;
	}

	@SuppressWarnings("unchecked")
	public List<Exam> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Exam").list();
	}

}
