package de.nordakademie.timetableservice.action.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.CenturyService;
import de.nordakademie.timetableservice.service.EventService;
import de.nordakademie.timetableservice.service.LecturerService;
import de.nordakademie.timetableservice.service.RoomService;

public class ShowEventAction extends ActionSupport implements Preparable {

	private Map<Long, String> availableLecturers;
	private Map<Long, String> availableRooms;
	private Map<Long, String> availableCenturies;

	private List<Long> selectedLecturerIds;
	private List<Long> selectedRoomIds;
	private List<Long> selectedCenturyIds;

	private Event event;
	private Long eventId;
	private EventService eventService;
	private LecturerService lecturerService;
	private RoomService roomService;
	private CenturyService centuryService;

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

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	@Override
	public String execute() throws Exception {
		if (eventId != null) {
			event = eventService.load(eventId);
			for (Lecturer lecturer : event.getLecturers()) {
				selectedLecturerIds.add(lecturer.getId());
			}
			for (Room room : event.getRooms()) {
				selectedRoomIds.add(room.getId());
			}
			for (Century century : event.getCenturies()) {
				selectedCenturyIds.add(century.getId());
			}
		} else {
			event = new Event();

		}
		return SUCCESS;
	}

	@Override
	public void prepare() throws Exception {
		selectedLecturerIds = new LinkedList<Long>();
		selectedRoomIds = new LinkedList<Long>();
		selectedCenturyIds = new LinkedList<Long>();
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

	public List<Long> getSelectedLecturerIds() {
		return selectedLecturerIds;
	}

	public void setSelectedLecturerIds(List<Long> selectedLecturerIds) {
		this.selectedLecturerIds = selectedLecturerIds;
	}
}
