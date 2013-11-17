package de.nordakademie.timetableservice.action.event;

public class SearchFreeRoomsAction extends AbstractHandleEventAction {

	private static final long serialVersionUID = 5847355205220608060L;

	@Override
	public String execute() throws Exception {
		availableRooms = roomService.getAvailableRoomsByDates(startDate, endDate, eventId);
		return SUCCESS;
	}

	@Override
	public void validate() {
		super.validate();
		checkDates();
	}

}