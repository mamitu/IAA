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

	@Override
	public void validate() {
		checkRoomNameAlreadyExists();
		checkBreakTimeValid();
	}

	private void checkRoomNameAlreadyExists() {
		if (roomService.checkNameExists(room.getName())) {
			addFieldError("room.name", getText("error.existingRoomName"));
		}
	}

	private void checkBreakTimeValid() {
		if (room.getBreakTime() == null) {
			addFieldError("room.breakTime", getText("label.required.breakTime"));
		} else if (room.getBreakTime() < room.getRoomType().getMinimalChangeTime()) {
			String errorMessage = getText("error.roomTypeMoreChangeTime");
			errorMessage = errorMessage.replace("$roomType", getText(room.getRoomType().getName()));
			errorMessage = errorMessage.replace("$breakTime", getText(String.valueOf(room.getRoomType().getMinimalChangeTime())));
			addFieldError("room.breakTime", errorMessage);
		}
	}

}