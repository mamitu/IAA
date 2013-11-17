package de.nordakademie.timetableservice.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Room;

public class RoomDAO extends EventParticipantDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Room> findRoomsByEvent(Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		List<Room> rooms = session.createQuery("select room from Room room join room.events event where event.id = :eventId").setParameter("eventId", eventId).list();
		return rooms;
	}

	@SuppressWarnings("unchecked")
	public List<Room> findRoomsByName(String roomName) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Room>) session.createQuery("select room from Room as room where room.name = :roomName").setString("roomName", roomName).list();
	}

	@Override
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	protected String getTableName() {
		return Room.class.getName();
	}

	public Room load(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Room room = (Room) session.get(Room.class, id);
		Hibernate.initialize(room.getName());
		Hibernate.initialize(room.getNumberOfSeats());
		Hibernate.initialize(room.getBreakTime());
		Hibernate.initialize(room.getEvents());
		Hibernate.initialize(room.getRoomType());
		return room;
	}

	@SuppressWarnings("unchecked")
	public List<Room> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Room").list();
	}

	public void save(Room room) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(room);
	}

}