package de.nordakademie.timetableservice.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.nordakademie.timetableservice.model.Room;

/**
 * Data Access Object fuer Raeume.
 * 
 * @author
 * 
 */
public class RoomDAO extends EventParticipantDAO {

	/**
	 * Die Hibernate Session Factory
	 */
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Laedt die Raeume mit dem uebergebenen Namen aus der Datenbank
	 * 
	 * @param roomName
	 *            Name des Raumes
	 * @return Liste mit Raeumen, die den uebergebenen Namen haben
	 */
	@SuppressWarnings("unchecked")
	public List<Room> findRoomsByName(String roomName) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Room>) session.createQuery("select room from Room as room where room.name = :roomName")
				.setString("roomName", roomName).list();
	}

	/**
	 * Gibt die Session zurueck
	 */
	@Override
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Gibt den Tabellennamen der Raueme zurueck
	 */
	@Override
	protected String getTableName() {
		return Room.class.getName();
	}

	/**
	 * Laedt den Raum mit der uebergebenen ID aus der Datenbank
	 * 
	 * @param id
	 *            ID des Raumes
	 * @return Raum mit der uebergebenen ID oder null, falls kein Raum zu der ID
	 *         gefunden werden konnte
	 */
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

	/**
	 * Laedt eine Liste mit allen in der Datenbank vorhandenen Raeumen
	 * 
	 * @return Liste aller angelegten Raeumen
	 */
	@SuppressWarnings("unchecked")
	public List<Room> loadAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Room").list();
	}

	/**
	 * Persistiert (erzeugt oder aktualisiert) einen Raum
	 * 
	 * @param room
	 *            Raum, der persitiert werden soll
	 */
	public void save(Room room) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(room);
	}

}