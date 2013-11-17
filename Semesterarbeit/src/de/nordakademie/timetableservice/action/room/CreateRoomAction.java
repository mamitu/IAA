package de.nordakademie.timetableservice.action.room;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.RoomService;

public class CreateRoomAction extends ActionSupport {

	private static final long serialVersionUID = -3597629136028462847L;
	private RoomService roomService;
	private Room room;

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public Room getRoom() {
		return room;
	}

	@Override
	public String execute() throws Exception {
		room = roomService.createNewRoom();
		return SUCCESS;
	}

}