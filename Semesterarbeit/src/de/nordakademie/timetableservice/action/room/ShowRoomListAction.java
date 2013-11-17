package de.nordakademie.timetableservice.action.room;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.RoomService;

public class ShowRoomListAction extends ActionSupport {

	private static final long serialVersionUID = -487012732866074650L;
	private RoomService roomService;
	private List<Room> rooms;

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	@Override
	public String execute() throws Exception {
		rooms = roomService.loadAll();
		return SUCCESS;
	}

}