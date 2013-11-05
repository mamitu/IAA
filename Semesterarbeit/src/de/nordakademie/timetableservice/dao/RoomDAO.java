package de.nordakademie.timetableservice.dao;

import java.util.HashSet;
import java.util.Set;

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
		session.merge(room);
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
		Hibernate.initialize(room.getEvents());
		return room;
	}

	@SuppressWarnings("unchecked")
	public Set<Room> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return new HashSet<Room>(session.createQuery("from Room").list());
	}

	public Set<Room> findRoomsByEvent(Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Set<Room> rooms = new HashSet<Room>(session.createQuery("select room from Room room join room.events event where event.id = :eventId").setParameter("eventId", eventId)
				.list());
		return rooms;
	}

}
