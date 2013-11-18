package de.nordakademie.timetableservice.action.event;

import java.util.Date;
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
import de.nordakademie.timetableservice.service.CohortService;
import de.nordakademie.timetableservice.service.CollisionService;
import de.nordakademie.timetableservice.service.EventService;
import de.nordakademie.timetableservice.service.LecturerService;
import de.nordakademie.timetableservice.service.RoomService;

/**
 * Abstrakte Struts Action, die Funktionen und Attribute zum Anlegen, Editieren
 * und Speichern von Veranstaltungen fuer die konkreten Klassen bereitstellt.
 * 
 * @author
 * 
 */
public abstract class AbstractHandleEventAction extends ActionSupport implements Preparable {

	private static final long serialVersionUID = 7140640720058299778L;

	/**
	 * Service-Klasse fuer Zenturien.
	 */
	protected CenturyService centuryService;

	/**
	 * Service-Klasse fuer Kohorten.
	 */
	protected CohortService cohortService;

	/**
	 * Service-Klasse fuer Kollisionen.
	 */
	protected CollisionService collisionService;

	/**
	 * Service-Klasse fuer Veranstaltungen.
	 */
	protected EventService eventService;

	/**
	 * Service-Klasse fuer Dozenten.
	 */
	protected LecturerService lecturerService;

	/**
	 * Service-Klasse fuer Raeume.
	 */
	protected RoomService roomService;

	/**
	 * Map der bereits angelegten Zenturien.<br>
	 * Key: ID der Zenturie<br>
	 * Value: Name der Zenturie
	 */
	protected Map<Long, String> availableCenturies;

	/**
	 * Map der bereits angelegten Kohorten.<br>
	 * Key: ID der Kohorte<br>
	 * Value: Name der Kohorte
	 */
	protected Map<Long, String> availableCohorts;

	/**
	 * Map der bereits angelegten Dozenten.<br>
	 * Key: ID des Dozenten<br>
	 * Value: Name des Dozenten
	 */
	protected Map<Long, String> availableLecturers;

	/**
	 * Map der bereits angelegten Raeume.<br>
	 * Key: ID des Raumes<br>
	 * Value: Name des Raumes
	 */
	protected Map<Long, String> availableRooms;

	/**
	 * Liste mit den Zenturien-IDs, die der Nutzer in der Maske selektiert hat.
	 */
	protected List<Long> selectedCenturyIds = new LinkedList<Long>();

	/**
	 * Liste mit den Dozenten-IDs, die der Nutzer in der Maske selektiert hat.
	 */
	protected List<Long> selectedLecturerIds = new LinkedList<Long>();

	/**
	 * Liste mit den Raum-IDs, die der Nutzer in der Maske selektiert hat.
	 */
	protected List<Long> selectedRoomIds = new LinkedList<Long>();

	/**
	 * Liste mit den Kohorten-IDs, die der Nutzer in der Maske selektiert hat.
	 */
	protected List<Long> selectedCohortIds = new LinkedList<Long>();

	/**
	 * Liste mit den selektierten Zenturien
	 */
	protected List<Century> selectedCenturies = new LinkedList<Century>();

	/**
	 * Liste mit den selektierten Dozenten
	 */
	protected List<Lecturer> selectedLecturers = new LinkedList<Lecturer>();

	/**
	 * Liste mit den selektierten Raeumen
	 */
	protected List<Room> selectedRooms = new LinkedList<Room>();

	/**
	 * Veranstaltung, die erzeugt, editiert oder gespeichert wird
	 */
	protected Event event;

	/**
	 * ID der Veranstaltung, die erzeugt, editiert oder gespeichert wird
	 */
	protected Long eventId;

	/**
	 * Name der Veranstaltung, die erzeugt, editiert oder gespeichert wird
	 */
	protected String name;

	/**
	 * Typ der Veranstaltung, die erzeugt, editiert oder gespeichert werden soll
	 */
	protected String eventType;

	/**
	 * Startdatum der Veranstaltung, die erzeugt, editiert oder gespeichert
	 * werden soll
	 */
	protected Date startDate;

	/**
	 * Enddatum der Veranstaltung, die erzeugt, editiert oder gespeichert werden
	 * soll
	 */
	protected Date endDate;

	/**
	 * Wahrheitswert, ob das Feld mit den zur Verfuegung stehenden Zenturien
	 * oder Kohorten ausgelesen werden soll
	 */
	protected boolean isCenturySelected;

	/**
	 * Pausenzeit der Veranstaltung, die erzeugt, editiert oder gespeichert
	 * werden soll
	 */
	protected Long breakTime;

	/**
	 * Anzahl der Wiederholungen, die die Veranstaltung wiederholt wird
	 */
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

	/**
	 * Ermittelt die bereits angelegten Dozenten, Raeume, Zenturien und Kohorten
	 * und stellt sie bereit.
	 */
	@Override
	public void prepare() throws Exception {
		availableLecturers = lecturerService.getAvailableLecturers();
		availableRooms = roomService.getAvailableRooms();
		availableCenturies = centuryService.getAvailableCenturies();
		availableCohorts = cohortService.getAvailableCohorts();
	}

	/**
	 * Validierung, ob Start- und Enddatum valide sind. Erzeugt entsprechende
	 * Fehlermeldungen.
	 */
	protected void checkDates() {
		if (startDate == null || endDate == null) {
			addActionError(getText("error.event.invalidDate"));
		} else if (endDate.getTime() <= startDate.getTime()) {
			addActionError(getText("error.event.startDateLaterThanEndDate"));
		}
	}

}