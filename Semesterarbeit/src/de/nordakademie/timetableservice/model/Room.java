package de.nordakademie.timetableservice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.NaturalId;

/**
 * Entitaet, die einen Raum repraesentiert
 * 
 * @author
 * 
 */
@Entity
public class Room implements EventParticipant {

	/**
	 * Die ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Der Name
	 */
	@NaturalId
	@Column(length = 50, nullable = false)
	private String name;

	/**
	 * Die Typ des Raumes
	 */
	@Enumerated
	private RoomType roomType;

	/**
	 * Die Anzahl der Sitzplaetze
	 */
	@Column(name = "number_of_seats", nullable = false)
	private int numberOfSeats;

	/**
	 * Die Pausenzeit
	 */
	@Column(nullable = false)
	private Long breakTime;

	/**
	 * Liste mit den Veranstaltungen, an die der Raum teilnimmt
	 */
	@ManyToMany
	@JoinTable(joinColumns = { @JoinColumn(name = "room_id") }, inverseJoinColumns = { @JoinColumn(name = "event_id") })
	private List<Event> events;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Long getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(Long breakTime) {
		this.breakTime = breakTime;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public void associateEvent(Event event) {
		if (event == null) {
			throw new IllegalArgumentException();
		}
		event.getRooms().add(this);
		this.events.add(event);
	}

	public void removeEvent(Event event) {
		if (event == null) {
			throw new IllegalArgumentException();
		}
		event.getRooms().remove(this);
		this.events.remove(event);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getName() + " (Sitze: " + getNumberOfSeats() + ")";
	}

}