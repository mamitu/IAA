package de.nordakademie.timetableservice.service.impl;

import java.util.Date;
import java.util.Set;

import de.nordakademie.timetableservice.business.Collision;
import de.nordakademie.timetableservice.business.CollisionType;
import de.nordakademie.timetableservice.dao.RoomDAO;
import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.RoomService;

public class RoomServiceImpl implements RoomService {

	private RoomDAO roomDAO;

	@Override
	public void saveRoom(Room room) {
		roomDAO.save(room);
	}

	@Override
	public Room load(Long id) {
		return roomDAO.load(id);
	}

	public void setRoomDAO(RoomDAO roomDAO) {
		this.roomDAO = roomDAO;
	}

	@Override
	public Set<Room> loadAll() {
		return this.roomDAO.loadAll();
	}

	@Override
	public Set<Room> findRoomsByEvent(Event event) {
		return roomDAO.findRoomsByEvent(event.getId());
	}

	@Override
	public void getCollisionsWithOtherEvents(Event event, Set<Room> roomsToCheck, Set<Collision> collisions) {
		Set<Room> roomsWithExistingEvent = roomDAO.findRoomsWithDatesWithoutId(event.getStartDate(), event.getEndDate(), event.getId());
		if (!roomsWithExistingEvent.isEmpty()) {
			for (Room room : roomsToCheck) {
				if (roomsWithExistingEvent.contains(room)) {
					collisions.add(new Collision(CollisionType.ERROR, room.toString(), "label.collision.existingEventForEntity"));
				}
			}
		}
	}

	@Override
	public void checkRoomSize(Set<Room> roomsToCheck, Set<Century> selectedCenturies, Set<Collision> collisions) {
		int numberOfSeatsRequired = 0;
		for (Century century : selectedCenturies) {
			numberOfSeatsRequired += century.getNumberOfStudents();
		}
		for (Room room : roomsToCheck) {
			if (room.getNumberOfSeats() < numberOfSeatsRequired) {
				collisions.add(new Collision(CollisionType.ERROR, room.toString(), "label.collision.roomNotEnoughSeats"));
			}
		}

	}

	@Override
	public boolean checkNameExists(String roomName) {
		return roomDAO.findRoomsByName(roomName).isEmpty() ? false : true;
	}

	@Override
	public boolean checkNameExistsForAnotherId(Long roomId, String roomName) {
		return roomDAO.findRoomsByNameWithoutId(roomName, roomId).isEmpty() ? false : true;
	}

	@Override
	public Set<Room> findFreeRoomsByDates(Date startDate, Date endDate) {
		return roomDAO.findFreeRoomsByDate(startDate, endDate);
	}
}