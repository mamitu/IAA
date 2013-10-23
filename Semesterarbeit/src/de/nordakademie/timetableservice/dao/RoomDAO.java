package de.nordakademie.timetableservice.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Room;

public class RoomDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(Room room) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(room);
	}

	public Room load(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Room room = (Room) session.get(Room.class, id);
		// This is only of the possible solutions to lazy initialization
		// failures. Please note that a "join fetch" in a HQL query is the
		// better solution performance-wise.
		Hibernate.initialize(room.getName());
		Hibernate.initialize(room.getNumberOfSeats());
		Hibernate.initialize(room.getBreakTime());
		return room;
	}

	@SuppressWarnings("unchecked")
	public List<Room> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Room").list();
	}

}
