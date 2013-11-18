package de.nordakademie.timetableservice.action.event;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;

/**
 * Struts-Action zum Editieren einer bereits existierenden Veranstaltung.
 * 
 * @author
 * 
 */
public class EditExistingEventAction extends AbstractHandleEventAction {

	private static final long serialVersionUID = 1432104176076722146L;

	/**
	 * Laedt die Veranstaltung mit der uebergebenen ID und stellt deren Werte
	 * bereit.
	 */
	@Override
	public String execute() throws Exception {
		event = eventService.load(eventId);
		name = event.getName();
		eventType = event.getEventType().getName();
		startDate = event.getStartDate();
		endDate = event.getEndDate();
		breakTime = event.getBreakTime();
		for (Lecturer lecturer : event.getLecturers()) {
			selectedLecturerIds.add(lecturer.getId());
		}
		for (Room room : event.getRooms()) {
			selectedRoomIds.add(room.getId());
		}
		for (Century century : event.getCenturies()) {
			selectedCenturyIds.add(century.getId());
		}
		return SUCCESS;
	}
}