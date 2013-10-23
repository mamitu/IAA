package de.nordakademie.timetableservice.action.room;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.RoomService;

public class SaveRoomAction extends ActionSupport {

	private Room room;
	private RoomService roomService;

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	@Override
	public String execute() throws Exception {
		roomService.saveRoom(room);
		return super.execute();
	}
}
