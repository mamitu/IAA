package de.nordakademie.timetableservice.action.room;

import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.RoomService;

public class ShowRoomListAction extends ActionSupport {

	private RoomService roomService;
	private Set<Room> rooms;

	@Override
	public String execute() throws Exception {
		rooms = roomService.loadAll();
		return SUCCESS;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

}
