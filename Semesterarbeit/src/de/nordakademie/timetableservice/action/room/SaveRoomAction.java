package de.nordakademie.timetableservice.action.room;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.model.RoomType;
import de.nordakademie.timetableservice.service.RoomService;

public class SaveRoomAction extends ActionSupport {

	private static final long serialVersionUID = -3166910830099761921L;
	private RoomService roomService;
	private Room room;
	private String roomType;

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Override
	public String execute() throws Exception {
		roomService.saveRoom(room, translateRoomType());
		return SUCCESS;
	}

	@Override
	public void validate() {
		if (roomType == null) {
			addActionError(getText("error.room.roomTypeRequired"));
			return;
		}
		checkRoomNameAlreadyExists();
		checkBreakTimeValid();
	}

	private void checkRoomNameAlreadyExists() {
		if (roomService.checkNameExists(room.getName())) {
			addActionError(getText("error.room.existingRoomName"));
		}
	}

	private void checkBreakTimeValid() {
		if (room.getBreakTime() == null) {
			addActionError(getText("error.room.breakTimeRequired"));
			return;
		}
		if (room.getBreakTime() < translateRoomType().getMinimalBreakTime()) {
			String errorMessage = getText("error.room.roomTypeMoreBreakTime");
			errorMessage = errorMessage.replace("$roomType", getText(translateRoomType().getName()));
			errorMessage = errorMessage.replace("$breakTime", String.valueOf(translateRoomType().getMinimalBreakTime()));
			addActionError(errorMessage);
		}
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

}