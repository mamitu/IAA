package de.nordakademie.timetableservice.action.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import de.nordakademie.timetableservice.business.Collision;
import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.CenturyService;
import de.nordakademie.timetableservice.service.EventService;
import de.nordakademie.timetableservice.service.LecturerService;
import de.nordakademie.timetableservice.service.RoomService;

public class SaveEventAction extends ActionSupport implements Preparable {

	private Event event;
	private EventService eventService;

	private List<Long> selectedLecturerIds;
	private List<Long> selectedRoomIds;
	private List<Long> selectedCenturyIds;
	private Map<Long, String> availableLecturers;
	private Map<Long, String> availableRooms;
	private Map<Long, String> availableCenturies;

	public Map<Long, String> getAvailableLecturers() {
		return availableLecturers;
	}

	public void setAvailableLecturers(Map<Long, String> availableLecturers) {
		this.availableLecturers = availableLecturers;
	}

	public Map<Long, String> getAvailableRooms() {
		return availableRooms;
	}

	public void setAvailableRooms(Map<Long, String> availableRooms) {
		this.availableRooms = availableRooms;
	}

	public Map<Long, String> getAvailableCenturies() {
		return availableCenturies;
	}

	public void setAvailableCenturies(Map<Long, String> availableCenturies) {
		this.availableCenturies = availableCenturies;
	}

	public void prepare() {

		availableLecturers = new HashMap<Long, String>();
		availableRooms = new HashMap<Long, String>();
		availableCenturies = new HashMap<Long, String>();
		for (Lecturer lecturer : lecturerService.loadAll()) {
			availableLecturers.put(lecturer.getId(), lecturer.toString());
		}
		for (Room room : roomService.loadAll()) {
			availableRooms.put(room.getId(), room.toString());
		}
		for (Century century : centuryService.loadAll()) {
			availableCenturies.put(century.getId(), century.toString());
		}
	}

	private List<Lecturer> selectedLecturers;
	private List<Room> selectedRooms;
	private List<Century> selectedCenturies;

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
		updateLecturers();
		updateRooms();
		updateCenturies();
		eventService.saveEvent(event);
		return super.execute();
	}

	private void updateLecturers() {
		List<Lecturer> lecturersToRemove = new LinkedList<Lecturer>(event.getLecturers());
		lecturersToRemove.removeAll(selectedLecturers);
		for (Lecturer lecturer : lecturersToRemove) {
			lecturer.removeEvent(event);
			lecturerService.saveLecturer(lecturer);
		}
		for (Lecturer lecturer : selectedLecturers) {
			if (!event.getLecturers().contains(lecturer)) {
				lecturer.associateEvent(event);
				lecturerService.saveLecturer(lecturer);
			}
		}
	}

	private void updateRooms() {
		List<Room> roomsToRemove = new LinkedList<Room>(event.getRooms());
		roomsToRemove.removeAll(selectedRooms);
		for (Room room : roomsToRemove) {
			room.removeEvent(event);
			roomService.saveRoom(room);
		}
		for (Room room : selectedRooms) {
			if (!event.getRooms().contains(room)) {
				room.associateEvent(event);
				roomService.saveRoom(room);
			}
		}
	}

	private void updateCenturies() {
		List<Century> centuriesToRemove = new LinkedList<Century>(event.getCenturies());
		centuriesToRemove.removeAll(selectedCenturies);
		for (Century century : centuriesToRemove) {
			century.removeEvent(event);
			centuryService.saveCentury(century);
		}
		for (Century century : selectedCenturies) {
			if (!event.getCenturies().contains(century)) {
				century.associateEvent(event);
				centuryService.saveCentury(century);
			}
		}
	}

	public List<Long> getSelectedLecturerIds() {
		return selectedLecturerIds;
	}

	public void setSelectedLecturerIds(List<Long> selectedLecturerIds) {
		this.selectedLecturerIds = selectedLecturerIds;
	}

	@Override
	public void validate() {
		selectedRooms = new LinkedList<Room>();
		selectedCenturies = new LinkedList<Century>();
		selectedLecturers = new LinkedList<Lecturer>();
		event.setLecturers(lecturerService.findLecturersByEvent(event));
		event.setRooms(roomService.findRoomsByEvent(event));
		event.setCenturies(centuryService.findCenturiesByEvent(event));

		for (Long id : selectedLecturerIds) {
			selectedLecturers.add(lecturerService.load(id));
		}
		for (Long id : selectedCenturyIds) {
			selectedCenturies.add(centuryService.load(id));
		}
		for (Long id : selectedRoomIds) {
			selectedRooms.add(roomService.load(id));
		}

		List<Collision> collisions = new LinkedList<Collision>();
		lecturerService.getCollisionsWithOtherEvents(event, selectedLecturers, collisions);
		roomService.getCollisionsWithOtherEvents(event, selectedRooms, collisions);
		centuryService.getCollisionsWithOtherEvents(event, selectedCenturies, collisions);

		roomService.checkRoomSize(selectedRooms, selectedCenturies, collisions);

		for (Collision collision : collisions) {
			addActionError(getText(collision.getCollisionType().toString()) + collision.getEntity() + getText(collision.getMessage()));
		}
	}

}