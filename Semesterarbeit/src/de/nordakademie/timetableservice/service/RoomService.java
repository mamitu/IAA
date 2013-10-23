package de.nordakademie.timetableservice.service;

import java.util.List;

import de.nordakademie.timetableservice.model.Room;

public interface RoomService {

	public void saveRoom(Room room);

	public Room load(Long id);

	public List<Room> loadAll();

}