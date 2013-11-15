package de.nordakademie.timetableservice.action.room;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.model.RoomType;
import de.nordakademie.timetableservice.service.RoomService;

public class SaveRoomAction extends ActionSupport {

	private Room room;
	private RoomService roomService;
	private String roomType;

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

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
		room.setRoomType(translateRoomType());
		roomService.saveRoom(room);
		return super.execute();
	}

	private RoomType translateRoomType() {
		switch (roomType) {
		case ("roomType.audimax"): {
			return RoomType.AUDIMAX;
		}
		case ("roomType.computer_lab"): {
			return RoomType.COMPUTER_LAB;
		}
		case ("roomType.laboratory"): {
			return RoomType.LABORATORY;
		}
		case ("roomType.standard"): {
			return RoomType.STANDARD;
		}
		}
		return null;
	}

	@Override
	public void validate() {
		if (roomType == null) {
			addFieldError("room.name", getText("error.roomTypeRequired"));
		} else {
			checkRoomNameAlreadyExists();
			checkBreakTimeValid();

		}
	}

	private void checkRoomNameAlreadyExists() {
		if (roomService.checkNameExists(room.getName())) {
			addFieldError("room.name", getText("error.existingRoomName"));
		}
	}

	private void checkBreakTimeValid() {

		if (room.getBreakTime() == null) {
			addFieldError("room.breakTime", getText("label.required.breakTime"));
		} else if (room.getBreakTime() < translateRoomType().getMinimalChangeTime()) {
			String errorMessage = getText("error.roomTypeMoreChangeTime");
			errorMessage = errorMessage.replace("$roomType", getText(room.getRoomType().getName()));
			errorMessage = errorMessage.replace("$breakTime", getText(String.valueOf(room.getRoomType().getMinimalChangeTime())));
			addFieldError("room.breakTime", errorMessage);
		}
	}

}