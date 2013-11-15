package de.nordakademie.timetableservice.action.room;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.RoomService;

public class CreateRoomAction extends ActionSupport {

	private RoomService roomService;
	private Room room;
	private Long roomId;

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	@Override
	public String execute() throws Exception {
		if (roomId != null) {
			room = roomService.load(roomId);
		} else {
			room = new Room();
		}
		return SUCCESS;
	}

}
