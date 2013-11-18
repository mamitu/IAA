package de.nordakademie.timetableservice.action.room;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.RoomService;

/**
 * Struts-Action zum Anlegen eines neuen Raumes.
 * 
 * @author rs
 * 
 */
public class CreateRoomAction extends ActionSupport {

	private static final long serialVersionUID = -3597629136028462847L;

	/**
	 * Service-Klasse fuer Raeume.
	 */
	private RoomService roomService;

	/**
	 * Raum, der angelegt wird.
	 */
	private Room room;

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public Room getRoom() {
		return room;
	}

	/**
	 * Laesst einen neuen Raum erstellen und stellt ihn bereit.
	 */
	@Override
	public String execute() throws Exception {
		room = roomService.createNewRoom();
		return SUCCESS;
	}

}