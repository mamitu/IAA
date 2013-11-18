package de.nordakademie.timetableservice.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.nordakademie.timetableservice.dao.RoomDAO;
import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.model.RoomType;
import de.nordakademie.timetableservice.service.RoomService;

/**
 * Implementation des Raum-Services
 * 
 * @author
 * 
 */
public class RoomServiceImpl implements RoomService {

	private RoomDAO roomDAO;

	@Override
	public boolean checkNameExists(String roomName) {
		return roomDAO.findRoomsByName(roomName).isEmpty() ? false : true;
	}

	@Override
	public Room createNewRoom() {
		return new Room();
	}

	@Override
	public Map<Long, String> getAvailableRooms() {
		Map<Long, String> availableRooms = new HashMap<Long, String>();
		for (Room room : loadAll()) {
			availableRooms.put(room.getId(), room.toString());
		}
		return availableRooms;
	}

	@Override
	public Map<Long, String> getAvailableRoomsByDates(Date startDate, Date endDate, Long eventId) {
		Map<Long, String> availableRooms = new HashMap<Long, String>();
		List<Room> rooms = findFreeRoomsWithOtherEventsByDates(startDate, endDate, eventId);
		for (Room room : rooms) {
			availableRooms.put(room.getId(), room.toString());
		}
		return availableRooms;
	}

	@Override
	public List<Room> load(List<Long> roomIds) {
		List<Room> rooms = new LinkedList<Room>();
		for (Long id : roomIds) {
			rooms.add(load(id));
		}
		return rooms;
	}

	@Override
	public Room load(Long id) {
		return roomDAO.load(id);
	}

	@Override
	public List<Room> loadAll() {
		return this.roomDAO.loadAll();
	}

	@Override
	public void saveRoom(Room room) {
		roomDAO.save(room);
	}

	@Override
	public void saveRoom(Room room, RoomType roomType) {
		room.setRoomType(roomType);
		saveRoom(room);
	}

	public void setRoomDAO(RoomDAO roomDAO) {
		this.roomDAO = roomDAO;
	}

	private List<Room> findFreeRoomsWithOtherEventsByDates(Date startDate, Date endDate, Long eventId) {
		List<Room> resultSet = roomDAO.loadAll();
		resultSet.removeAll(roomDAO.findEntitiesWithDatesWithoutId(startDate, endDate, eventId));
		return resultSet;
	}
}
