package de.nordakademie.timetableservice.action.event;

import java.util.LinkedList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.CenturyService;
import de.nordakademie.timetableservice.service.EventService;
import de.nordakademie.timetableservice.service.LecturerService;
import de.nordakademie.timetableservice.service.RoomService;

public class SaveEventAction extends ActionSupport {

	private Event event;
	private EventService eventService;

	private List<Long> selectedLecturerIds;
	private List<Long> selectedRoomIds;
	private List<Long> selectedCenturyIds;

	private LecturerService lecturerService;
	private RoomService roomService;
	private CenturyService centuryService;

	public List<Long> getSelectedRoomIds() {
		return selectedRoomIds;
	}

	public void setSelectedRoomIds(List<Long> selectedRoomIds) {
		this.selectedRoomIds = selectedRoomIds;
	}

	public List<Long> getSelectedCenturyIds() {
		return selectedCenturyIds;
	}

	public void setSelectedCenturyIds(List<Long> selectedCenturyIds) {
		this.selectedCenturyIds = selectedCenturyIds;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public void setCenturyService(CenturyService centuryService) {
		this.centuryService = centuryService;
	}

	public void setLecturerService(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	@Override
	public String execute() throws Exception {
		event.setLecturers(lecturerService.findLecturersByEvent(event));
		event.setRooms(roomService.findRoomsByEvent(event));
		event.setCenturies(centuryService.findCenturiesByEvent(event));
		updateLecturers();
		updateRooms();
		updateCenturies();
		eventService.saveEvent(event);
		return super.execute();
	}

	private void updateLecturers() {
		List<Lecturer> selectedLecturers = new LinkedList<Lecturer>();
		for (Long id : selectedLecturerIds) {
			selectedLecturers.add(lecturerService.load(id));
		}
		List<Lecturer> lecturersToRemove = new LinkedList<Lecturer>(event.getLecturers());
		lecturersToRemove.removeAll(selectedLecturers);
		for (Lecturer lecturer : lecturersToRemove) {
			lecturer.removeEvent(event);
			lecturerService.saveLecturer(lecturer);
		}
		for (Lecturer lecturer : selectedLecturers) {
			if (!event.getLecturers().contains(lecturer)) {
				lecturer.associateEvent(event);
			}
		}
	}

	private void updateRooms() {
		List<Room> selectedRooms = new LinkedList<Room>();
		for (Long id : selectedRoomIds) {
			selectedRooms.add(roomService.load(id));
		}
		List<Room> roomsToRemove = new LinkedList<Room>(event.getRooms());
		roomsToRemove.removeAll(selectedRooms);
		for (Room room : roomsToRemove) {
			room.removeEvent(event);
			roomService.saveRoom(room);
		}
		for (Room room : selectedRooms) {
			if (!event.getRooms().contains(room)) {
				room.associateEvent(event);
			}
		}
	}

	private void updateCenturies() {
		List<Century> selectedCenturies = new LinkedList<Century>();
		for (Long id : selectedCenturyIds) {
			selectedCenturies.add(centuryService.load(id));
		}
		List<Century> centuriesToRemove = new LinkedList<Century>(event.getCenturies());
		centuriesToRemove.removeAll(selectedCenturies);
		for (Century century : centuriesToRemove) {
			century.removeEvent(event);
			centuryService.saveCentury(century);
		}
		for (Century century : selectedCenturies) {
			if (!event.getCenturies().contains(century)) {
				century.associateEvent(event);
			}
		}
	}

	public List<Long> getSelectedLecturerIds() {
		return selectedLecturerIds;
	}

	public void setSelectedLecturerIds(List<Long> selectedLecturerIds) {
		this.selectedLecturerIds = selectedLecturerIds;
	}

}