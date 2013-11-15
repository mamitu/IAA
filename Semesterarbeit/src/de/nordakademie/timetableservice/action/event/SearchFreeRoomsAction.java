package de.nordakademie.timetableservice.action.event;

import java.util.Set;

import de.nordakademie.timetableservice.model.Room;

public class SearchFreeRoomsAction extends HandleEventAction {

	@Override
	public String execute() throws Exception {
		Set<Room> rooms = roomService.findFreeRoomsWithOtherEventsByDates(startDate, endDate, eventId);
		availableRooms.clear();
		for (Room room : rooms) {
			availableRooms.put(room.getId(), room.toString());
		}
		return super.execute();
	}

	@Override
	public void validate() {
		super.validate();
		checkDates();
	}

}