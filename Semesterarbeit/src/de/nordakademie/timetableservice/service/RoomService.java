package de.nordakademie.timetableservice.service;

import java.util.List;
import java.util.Set;

import de.nordakademie.timetableservice.business.Collision;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.Room;

public interface RoomService {

	public void saveRoom(Room room);

	public Room load(Long id);

	public Set<Room> loadAll();

	public Set<Room> findRoomsByEvent(Event event);

	public void getCollisions(Event event, List<Room> roomsToCheck, List<Collision> collisions);

}