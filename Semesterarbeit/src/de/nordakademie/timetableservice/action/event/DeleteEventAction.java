package de.nordakademie.timetableservice.action.event;

import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.CenturyService;
import de.nordakademie.timetableservice.service.EventService;
import de.nordakademie.timetableservice.service.LecturerService;
import de.nordakademie.timetableservice.service.RoomService;

public class DeleteEventAction extends ActionSupport {

	private Long eventId;
	private Event event;
	private EventService eventService;
	private LecturerService lecturerService;
	private Set<Event> events;

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public void setLecturerService(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public void setCenturyService(CenturyService centuryService) {
		this.centuryService = centuryService;
	}

	private RoomService roomService;
	private CenturyService centuryService;

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	@Override
	public String execute() throws Exception {
		if (eventId == null) {
			throw new IllegalArgumentException();
		} else {
			this.event = eventService.load(eventId);
			for (Lecturer lecturer : event.getLecturers()) {
				lecturer.removeEvent(event);
				lecturerService.saveLecturer(lecturer);
			}
			for (Century century : event.getCenturies()) {
				century.removeEvent(event);
				centuryService.saveCentury(century);
			}
			for (Room room : event.getRooms()) {
				room.removeEvent(event);
				roomService.saveRoom(room);
			}
			eventService.deleteEventWithId(eventId);
		}
		events = eventService.loadAll();
		return SUCCESS;
	}

}