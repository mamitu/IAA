package de.nordakademie.timetableservice.service.impl;

import java.util.List;

import de.nordakademie.timetableservice.dao.RoomDAO;
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
	public List<Room> loadAll() {
		return this.roomDAO.loadAll();
	}

}