package de.nordakademie.timetableservice.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
		Hibernate.initialize(room.getEventsOfRoom());
		Hibernate.initialize(room.getRoomType());
		return room;
	}

	@SuppressWarnings("unchecked")
	public Set<Room> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return new HashSet<Room>(session.createQuery("from Room").list());
	}

	@SuppressWarnings("unchecked")
	public Set<Room> findRoomsByEvent(Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		Set<Room> rooms = new HashSet<Room>(session.createQuery("select room from Room room join room.eventsOfRoom event where event.id = :eventId")
				.setParameter("eventId", eventId).list());
		return rooms;
	}

	public Set<Room> findRoomsWithDatesWithoutId(Date startDate, Date endDate, Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		Set<Room> rooms = new HashSet<Room>();
		getRoomsWithStartDateBetweenExistingEvent(startDate, eventId, session, rooms);
		getRoomsWithEndDateBetweenExistingEvent(endDate, eventId, session, rooms);
		getRoomsWithDatesAroundExistingEvent(startDate, endDate, eventId, session, rooms);
		return rooms;
	}

	@SuppressWarnings("unchecked")
	private void getRoomsWithDatesAroundExistingEvent(Date startDate, Date endDate, Long eventId, Session session, Set<Room> rooms) {
		if (eventId == null) {
			List<Room> roomList = (List<Room>) session
					.createQuery("select room from Room room join room.eventsOfRoom event where :startDate <= event.startDate and :endDate >= event.endDate")
					.setTimestamp("endDate", endDate).setTimestamp("startDate", startDate).list();
			rooms.addAll(roomList);
		} else {
			List<Room> roomList = (List<Room>) session
					.createQuery(
							"select room from Room room join room.eventsOfRoom event where event.id != :eventId and :startDate <= event.startDate and :endDate >= event.endDate")
					.setTimestamp("endDate", endDate).setTimestamp("startDate", startDate).setParameter("eventId", eventId).list();
			rooms.addAll(roomList);

		}
	}

	@SuppressWarnings("unchecked")
	private void getRoomsWithEndDateBetweenExistingEvent(Date endDate, Long eventId, Session session, Set<Room> rooms) {
		if (eventId == null) {
			List<Room> roomList = (List<Room>) session
					.createQuery("select room from Room room join room.eventsOfRoom event where :endDate between event.startDate and event.endDate")
					.setTimestamp("endDate", endDate).list();
			rooms.addAll(roomList);
		} else {
			List<Room> roomList = (List<Room>) session
					.createQuery("select room from Room room join room.eventsOfRoom event where event.id != :eventId and :endDate between event.startDate and event.endDate")
					.setTimestamp("endDate", endDate).setParameter("eventId", eventId).list();
			rooms.addAll(roomList);

		}
	}

	@SuppressWarnings("unchecked")
	private void getRoomsWithStartDateBetweenExistingEvent(Date startDate, Long eventId, Session session, Set<Room> rooms) {
		if (eventId == null) {
			List<Room> roomList = (List<Room>) session
					.createQuery("select room from Room room join room.eventsOfRoom event where :startDate between event.startDate and event.endDate")
					.setTimestamp("startDate", startDate).list();
			rooms.addAll(roomList);
		} else {
			List<Room> roomList = (List<Room>) session
					.createQuery("select room from Room room join room.eventsOfRoom event where event.id != :eventId and :startDate between event.startDate and event.endDate")
					.setTimestamp("startDate", startDate).setParameter("eventId", eventId).list();
			rooms.addAll(roomList);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Room> findRoomsByName(String roomName) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Room>) session.createQuery("select room from Room as room where room.name = :roomName").setString("roomName", roomName).list();
	}

	@SuppressWarnings("unchecked")
	public List<Room> findRoomsByNameWithoutId(String roomName, Long roomId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Room>) session.createQuery("select room from Room as room where room.name = :roomName and room.id != :roomId").setString("roomName", roomName)
				.setParameter("roomId", roomId).list();
	}

	@SuppressWarnings("unchecked")
	public Set<Room> findFreeRoomsByDate(Date startDate, Date endDate) {
		Session session = sessionFactory.getCurrentSession();
		return new HashSet<Room>(
				session.createQuery(
						"select room from Room as room join room.eventsOfRoom as event where ( event.startDate not between :startDate and :endDate) OR (event.endDate not between :startDate and :endDate) OR ( event.startDate < :startDate AND event.endDate > :endDate )")
						.setTimestamp("startDate", startDate).setTimestamp("endDate", endDate).list());
	}

}
