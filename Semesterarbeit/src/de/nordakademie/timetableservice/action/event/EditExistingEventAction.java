package de.nordakademie.timetableservice.action.event;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;

public class EditExistingEventAction extends AbstractHandleEventAction {

	private static final long serialVersionUID = 1432104176076722146L;

	@Override
	public String execute() throws Exception {
		event = eventService.load(eventId);
		eventType = event.getEventType().getName();
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