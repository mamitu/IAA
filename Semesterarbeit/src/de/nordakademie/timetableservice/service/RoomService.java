package de.nordakademie.timetableservice.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.model.RoomType;

public interface RoomService {

	public boolean checkNameExists(String roomName);

	public Room createNewRoom();

	public List<Room> findFreeRoomsWithOtherEventsByDates(Date startDate, Date endDate, Long eventId);

	public Map<Long, String> getAvailableRooms();

	public Map<Long, String> getAvailableRoomsByDates(Date startDate, Date endDate, Long eventId);

	public List<Room> load(List<Long> roomIds);

	public Room load(Long id);

	public List<Room> loadAll();

	public void saveRoom(Room room);

	public void saveRoom(Room room, RoomType roomType);

}