package de.nordakademie.timetableservice.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.model.RoomType;

/**
 * Interface fuer den Raum-Service, das alle Methoden bereitstellt, um mit
 * Raeumen zu arbeiten.
 * 
 * @author mm
 */
public interface RoomService {

	/**
	 * Prueft, ob bereits ein Raum mit diesem Namen in der Datenbank existiert.
	 * 
	 * @param roomName
	 *            der zu ueberpruefende Raumname
	 * @return true, falls bereits ein Raum mit dem Namen existiert.
	 */
	public boolean checkNameExists(String roomName);

	/**
	 * Instanziiert einen Raum.
	 * 
	 * @return ein neues Raum Objekt
	 */
	public Room createNewRoom();

	/**
	 * Ermittelt alle angelegten Raeume in einer Map. Diese entaelt als Key die
	 * ID des Raumes und als Value dessen Namen.
	 * 
	 * @return Map, aller angelegten Raeume, die als Key die ID der Raeume und
	 *         als Value deren Namen enthaelt.
	 */
	public Map<Long, String> getAvailableRooms();

	/**
	 * Ermittelt alle angelegten Raeume in einer Map, die zu den uebergebenen
	 * Zeiten noch keine Veranstaltung haben. Die Map entaelt als Key die ID des
	 * Raumes und als Value dessen Namen.
	 * 
	 * @return Map, aller angelegten Raeume, die zu den uebergebenen Zeiten noch
	 *         keine Veranstaltung haben. In der Map dient als Key die ID der
	 *         Raeume und als Value deren Namen.
	 */
	public Map<Long, String> getAvailableRoomsByDates(Date startDate, Date endDate, Long eventId);

	/**
	 * Laedt eine Liste aller angelegten Raeume mit den uebergebenen IDs aus der
	 * Datenbank.
	 * 
	 * @param id
	 *            Die ID der zu ladenden Raeume
	 * @return Liste aller Raeume mit den uebergebenen IDs
	 */
	public List<Room> load(List<Long> roomIds);

	/**
	 * Laedt einen angelegten Raum aus der Datenbank.
	 * 
	 * @param id
	 *            Die ID des Raumes
	 * @return Den Raum mit der ID aus der Datenbank oder null, falls kein Raum
	 *         mit dieser ID existiert
	 */
	public Room load(Long id);

	/**
	 * Laedt eine Liste aller angelegten Raeume aus der Datenbank.
	 * 
	 * @return Liste aller Raeume
	 */
	public List<Room> loadAll();

	/**
	 * Speichert den Raum
	 * 
	 * @param room
	 *            Zu speichernder Raum
	 */
	public void saveRoom(Room room);

	/**
	 * Speichert den Raum und setzt vorher den uebergebenen Raumtyp
	 * 
	 * @param room
	 *            Zu speichernder Raum
	 * @oaram roomType Raumtyp, der am Raum gesetzt werden soll
	 */
	public void saveRoom(Room room, RoomType roomType);

}