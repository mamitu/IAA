package de.nordakademie.timetableservice.action.event;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.EventType;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.CenturyService;
import de.nordakademie.timetableservice.service.CohortService;
import de.nordakademie.timetableservice.service.EventService;
import de.nordakademie.timetableservice.service.LecturerService;
import de.nordakademie.timetableservice.service.RoomService;

public abstract class HandleEventAction extends ActionSupport implements Preparable {

	protected Event event;

	protected List<Long> selectedLecturerIds;
	protected List<Long> selectedRoomIds;
	protected List<Long> selectedCenturyIds;
	protected List<Long> selectedCohortIds;
	protected String eventType;
	protected Set<Lecturer> selectedLecturers;
	protected Set<Room> selectedRooms;
	protected Set<Century> selectedCenturies;
	protected Cohort selectedCohort;

	protected Map<Long, String> availableLecturers;
	protected Map<Long, String> availableRooms;
	protected Map<Long, String> availableCenturies;
	protected Map<Long, String> availableCohorts;

	protected int numberOfWeeklyRepetitions;

	protected boolean isCenturySelected;

	public boolean getIsCenturySelected() {
		return isCenturySelected;
	}

	public void setIsCenturySelected(boolean isCenturySelected) {
		this.isCenturySelected = isCenturySelected;
	}

	protected Set<Century> getRelevantCenturies() {
		if (isCenturySelected)
			return selectedCenturies;
		return centuryService.findCenturiesByCohort(selectedCohort);
	}

	protected Date startDate;

	protected Long changeTime;

	protected Long eventId;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Long changeTime) {
		this.changeTime = changeTime;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected Date endDate;
	protected String name;

	protected EventService eventService;
	protected LecturerService lecturerService;
	protected RoomService roomService;
	protected CenturyService centuryService;
	protected CohortService cohortService;

	public List<Long> getSelectedCohortIds() {
		return selectedCohortIds;
	}

	public void setSelectedCohortIds(List<Long> selectedCohortIds) {
		this.selectedCohortIds = selectedCohortIds;
	}

	public Cohort getSelectedCohort() {
		return selectedCohort;
	}

	public void setSelectedCohort(Cohort selectedCohort) {
		this.selectedCohort = selectedCohort;
	}

	public Map<Long, String> getAvailableCohorts() {
		return availableCohorts;
	}

	public void setAvailableCohorts(Map<Long, String> availableCohorts) {
		this.availableCohorts = availableCohorts;
	}

	public void setCohortService(CohortService cohortService) {
		this.cohortService = cohortService;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<Long> getSelectedLecturerIds() {
		return selectedLecturerIds;
	}

	public void setSelectedLecturerIds(List<Long> selectedLecturerIds) {
		this.selectedLecturerIds = selectedLecturerIds;
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

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

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

	public int getNumberOfWeeklyRepetitions() {
		return numberOfWeeklyRepetitions;
	}

	public void setNumberOfWeeklyRepetitions(int numberOfWeeklyRepetitions) {
		this.numberOfWeeklyRepetitions = numberOfWeeklyRepetitions;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
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

	@Override
	public void prepare() throws Exception {
		selectedLecturerIds = new LinkedList<Long>();
		selectedRoomIds = new LinkedList<Long>();
		selectedCenturyIds = new LinkedList<Long>();
		selectedCohortIds = new LinkedList<Long>();

		selectedRooms = new HashSet<Room>();
		selectedCenturies = new HashSet<Century>();
		selectedLecturers = new HashSet<Lecturer>();

		availableLecturers = new HashMap<Long, String>();
		availableRooms = new HashMap<Long, String>();
		availableCenturies = new HashMap<Long, String>();
		availableCohorts = new HashMap<Long, String>();

		for (Room room : roomService.loadAll()) {
			availableRooms.put(room.getId(), room.toString());
		}
		for (Lecturer lecturer : lecturerService.loadAll()) {
			availableLecturers.put(lecturer.getId(), lecturer.toString());
		}
		for (Century century : centuryService.loadAll()) {
			availableCenturies.put(century.getId(), century.toString());
		}
		for (Cohort cohort : cohortService.loadAll()) {
			availableCohorts.put(cohort.getId(), cohort.toString());
		}

	}

	protected void checkDates() {
		if (startDate == null || endDate == null) {
			addActionError(getText("error.invalidDate"));
		} else if (startDate.getTime() > endDate.getTime()) {
			addActionError(getText("error.startDateLaterThanEndDate"));
		}
	}

	protected void checkCenturySelections() {
		if (event.getEventType().equals(EventType.SEMINAR)) {
			if (selectedCenturyIds.size() != 0) {
				addActionError(getText("error.seminarNoCenturies"));
			}
		} else {
			if (isCenturySelected && selectedCenturyIds.size() == 0) {
				addActionError(getText("error.centuriesRequired"));
			}
		}
		if (isCenturySelected && event.getEventType().equals(EventType.ELECTIVE)) {
			addActionError(getText("error.electiveJustCohort"));
		}
	}

	protected void fillSelectedEntities() {
		for (Long id : selectedLecturerIds) {
			selectedLecturers.add(lecturerService.load(id));
		}
		for (Long id : selectedCenturyIds) {
			selectedCenturies.add(centuryService.load(id));
		}
		for (Long id : selectedRoomIds) {
			selectedRooms.add(roomService.load(id));
		}
		if (!isCenturySelected)
			selectedCohort = cohortService.load(selectedCohortIds.get(0));
	}
}