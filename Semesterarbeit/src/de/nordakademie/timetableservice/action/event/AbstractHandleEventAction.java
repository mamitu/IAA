package de.nordakademie.timetableservice.action.event;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;
import de.nordakademie.timetableservice.service.CenturyService;
import de.nordakademie.timetableservice.service.CohortService;
import de.nordakademie.timetableservice.service.CollisionService;
import de.nordakademie.timetableservice.service.EventService;
import de.nordakademie.timetableservice.service.LecturerService;
import de.nordakademie.timetableservice.service.RoomService;

public abstract class AbstractHandleEventAction extends ActionSupport implements Preparable {

	private static final long serialVersionUID = 7140640720058299778L;
	protected CenturyService centuryService;
	protected CohortService cohortService;
	protected CollisionService collisionService;
	protected EventService eventService;
	protected LecturerService lecturerService;
	protected RoomService roomService;

	protected Map<Long, String> availableCenturies;
	protected Map<Long, String> availableCohorts;
	protected Map<Long, String> availableLecturers;
	protected Map<Long, String> availableRooms;

	protected List<Long> selectedCenturyIds = new LinkedList<Long>();
	protected List<Long> selectedLecturerIds = new LinkedList<Long>();
	protected List<Long> selectedRoomIds = new LinkedList<Long>();
	protected List<Long> selectedCohortIds = new LinkedList<Long>();

	protected List<Century> selectedCenturies = new LinkedList<Century>();
	protected List<Lecturer> selectedLecturers = new LinkedList<Lecturer>();
	protected List<Room> selectedRooms = new LinkedList<Room>();
	protected Cohort selectedCohort;

	protected List<String> collisions = new LinkedList<String>();

	protected Event event;
	protected Long eventId;
	protected String name;
	protected String eventType;
	protected Date startDate;
	protected Date endDate;
	protected boolean isCenturySelected;
	protected Long breakTime;
	protected int numberOfWeeklyRepetitions;

	public void setCenturyService(CenturyService centuryService) {
		this.centuryService = centuryService;
	}

	public void setCohortService(CohortService cohortService) {
		this.cohortService = cohortService;
	}

	public void setCollisionService(CollisionService collisionService) {
		this.collisionService = collisionService;
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

	public Map<Long, String> getAvailableCenturies() {
		return availableCenturies;
	}

	public Map<Long, String> getAvailableCohorts() {
		return availableCohorts;
	}

	public Map<Long, String> getAvailableLecturers() {
		return availableLecturers;
	}

	public Map<Long, String> getAvailableRooms() {
		return availableRooms;
	}

	public List<Long> getSelectedCenturyIds() {
		return selectedCenturyIds;
	}

	public void setSelectedCenturyIds(List<Long> selectedCenturyIds) {
		this.selectedCenturyIds = selectedCenturyIds;
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

	public List<String> getCollisions() {
		return collisions;
	}

	public void setCollisions(List<String> collisions) {
		this.collisions = collisions;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
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

	public boolean getIsCenturySelected() {
		return isCenturySelected;
	}

	public void setIsCenturySelected(boolean isCenturySelected) {
		this.isCenturySelected = isCenturySelected;
	}

	public Long getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(Long breakTime) {
		this.breakTime = breakTime;
	}

	public int getNumberOfWeeklyRepetitions() {
		return numberOfWeeklyRepetitions;
	}

	public void setNumberOfWeeklyRepetitions(int numberOfWeeklyRepetitions) {
		this.numberOfWeeklyRepetitions = numberOfWeeklyRepetitions;
	}

	@Override
	public void prepare() throws Exception {
		availableLecturers = lecturerService.getAvailableLecturers();
		availableRooms = roomService.getAvailableRooms();
		availableCenturies = centuryService.getAvailableCenturies();
		availableCohorts = cohortService.getAvailableCohorts();
	}

	protected void checkDates() {
		if (startDate == null || endDate == null) {
			addActionError(getText("error.event.invalidDate"));
		} else if (startDate.getTime() > endDate.getTime()) {
			addActionError(getText("error.event.startDateLaterThanEndDate"));
		}
	}

}