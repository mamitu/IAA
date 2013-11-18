package de.nordakademie.timetableservice.action.room;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.model.RoomType;
import de.nordakademie.timetableservice.service.RoomService;

/**
 * Struts-Action zum Speichern eines neuen Raumes. Validiert zunaechst die
 * eingegebenen Werte und zeigt eventuelle Fehlermeldungen an. Sind keine Fehler
 * aufgetreten wird der Raum gespeichert.
 * 
 * @author rs
 */
public class SaveRoomAction extends ActionSupport {

	private static final long serialVersionUID = -3166910830099761921L;

	/**
	 * Service-Klasse fuer Raeume.
	 */
	private RoomService roomService;

	/**
	 * Raum, der angelegt wird.
	 */
	private Room room;

	/**
	 * Der Typ des Raumes. Benoetigt, weil der Enumtyp nicht richtig konvertiert
	 * wird.
	 */
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

	/**
	 * Laesst den Raum mit den eingegebenen Werten speichern.
	 */
	@Override
	public String execute() throws Exception {
		roomService.saveRoom(room, translateRoomType());
		return SUCCESS;
	}

	/**
	 * Validiert, ob der Raumtyp eingegeben wurde, ob die Pausenzeit valide ist
	 * und ob der Raumname bereits existiert. Erzeugt entsprechende
	 * Fehlermeldungen.
	 */
	@Override
	public void validate() {
		if (roomType == null) {
			addActionError(getText("error.room.roomTypeRequired"));
			return;
		}
		checkRoomNameAlreadyExists();
		checkBreakTimeValid();
	}

	/**
	 * Laesst pruefen, ob die email-Adresse bereits existiert.
	 */
	private void checkRoomNameAlreadyExists() {
		if (roomService.checkNameExists(room.getName())) {
			addActionError(getText("error.room.existingRoomName"));
		}
	}

	/**
	 * Laesst pruefen, ob die Pausenzeit angegeben wurde, und ob sie fuer den
	 * Raumtyp ausreichend lang ist. Erzeugt entsprechende Fehlermeldungen.
	 */
	private void checkBreakTimeValid() {
		if (room.getBreakTime() == null) {
			addActionError(getText("error.room.breakTimeRequired"));
			return;
		}
		if (room.getBreakTime() < translateRoomType().getMinimalBreakTime()) {
			String errorMessage = getText("error.room.roomTypeMoreBreakTime");
			errorMessage = errorMessage.replace("$roomType", getText(translateRoomType().getName()));
			errorMessage = errorMessage
					.replace("$breakTime", String.valueOf(translateRoomType().getMinimalBreakTime()));
			addActionError(errorMessage);
		}
	}

	/**
	 * Ermittelt den richtigen Enumwert aus dem selektierten Raumtyp.
	 * 
	 * @return Raumtyp des Raumes
	 */
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