package de.nordakademie.timetableservice.service;

import java.util.Date;
import java.util.Set;

import de.nordakademie.timetableservice.business.Collision;
import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.Room;

public interface RoomService {

	public void saveRoom(Room room);

	public Room load(Long id);

	public Set<Room> loadAll();

	public Set<Room> findRoomsByEvent(Event event);

	public void getCollisionsWithOtherEvents(Event event, Set<Room> roomsToCheck, Set<Collision> collisions);

	public void checkRoomSize(Set<Room> roomsToCheck, Set<Century> selectedCenturies, Set<Collision> collisions);

	public boolean checkNameExists(String roomName);

	public boolean checkNameExistsForAnotherId(Long roomId, String roomName);

	public Set<Room> findFreeRoomsWithOtherEventsByDates(Date startDate, Date endDate, Long eventId);

}