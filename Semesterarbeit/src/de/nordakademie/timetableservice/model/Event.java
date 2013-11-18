package de.nordakademie.timetableservice.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Entitaet, die eine Veranstaltung repraesentiert
 * 
 * @author mm, rs
 * 
 */
@Entity
public class Event implements EventParticipant {

	/**
	 * Die ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Das Startdatum
	 */
	@Column(name = "start_date", nullable = false)
	private Date startDate;

	/**
	 * Das Enddatum
	 */
	@Column(name = "end_date", nullable = false)
	private Date endDate;

	/**
	 * Der Name
	 */
	@Column(length = 50, nullable = false)
	private String name;

	/**
	 * Der Typ der Veranstaltung
	 */
	@Enumerated
	private EventType eventType;

	/**
	 * Liste mit den Zenturien, die an der Veranstaltung teilnehmen
	 */
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "events")
	private Set<Century> centuries;

	/**
	 * Liste mit den Dozenten, die an der Veranstaltung teilnehmen
	 */
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "events")
	private Set<Lecturer> lecturers;

	/**
	 * Liste mit den Raeumen, die an der Veranstaltung teilnehmen
	 */
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "events")
	private Set<Room> rooms;

	/**
	 * Die Pausenzeit
	 */
	@Column(name = "break_time", nullable = false)
	private Long breakTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public Set<Century> getCenturies() {
		return centuries;
	}

	public void setCenturies(List<Century> centuries) {
		this.centuries = new HashSet<Century>(centuries);
	}

	public Set<Lecturer> getLecturers() {
		return lecturers;
	}

	public void setLecturers(List<Lecturer> lecturers) {
		this.lecturers = new HashSet<Lecturer>(lecturers);
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = new HashSet<Room>(rooms);
	}

	public Long getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(Long breakTime) {
		this.breakTime = breakTime;
	}

	@Override
	public String toString() {
		return name + "(" + getId() + ")";
	}

}