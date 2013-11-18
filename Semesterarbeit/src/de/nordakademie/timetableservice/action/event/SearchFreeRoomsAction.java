package de.nordakademie.timetableservice.action.event;

/**
 * Struts-Action zum Suchen freier Raeume zum eingegebenen Start- und Enddatum.
 * 
 * @author
 * 
 */
public class SearchFreeRoomsAction extends AbstractHandleEventAction {

	private static final long serialVersionUID = 5847355205220608060L;

	/**
	 * Laesst die freien Raeume zum eingegebenen Start- und Enddatum laden und
	 * stellt sie bereit.
	 */
	@Override
	public String execute() throws Exception {
		availableRooms = roomService.getAvailableRoomsByDates(startDate, endDate, eventId);
		return SUCCESS;
	}

	/**
	 * Validiert, ob das eingegebene Start- und Enddatum valide ist, um damit
	 * dann freie Raeume zu suchen.
	 */
	@Override
	public void validate() {
		super.validate();
		checkDates();
	}

}