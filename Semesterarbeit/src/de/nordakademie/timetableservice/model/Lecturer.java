package de.nordakademie.timetableservice.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.NaturalId;

@Entity
public class Lecturer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "first_name", length = 50, nullable = false)
	private String firstName;

	@Column(name = "last_name", length = 50, nullable = false)
	private String lastName;

	@Column(nullable = false)
	private int breakTime;

	@NaturalId
	@Column(nullable = false, length = 50)
	private String emailAddress;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(joinColumns = { @JoinColumn(name = "lecturer_id") }, inverseJoinColumns = { @JoinColumn(name = "event_id") })
	private Set<Event> eventsOfLecturer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(int breakTime) {
		this.breakTime = breakTime;
	}

	@Override
	public String toString() {
		return getFirstName() + " " + getLastName() + " (" + getEmailAddress() + ")";
	}

	public void associateEvent(Event event) {
		if (event == null) {
			throw new IllegalArgumentException();
		}
		event.getLecturers().add(this);
		this.eventsOfLecturer.add(event);
	}

	public void removeEvent(Event event) {
		if (event == null) {
			throw new IllegalArgumentException();
		}
		event.getLecturers().remove(this);
		this.eventsOfLecturer.remove(event);
	}

	public Set<Event> getEventsOfLecturer() {
		return eventsOfLecturer;
	}

	public void setEventsOfLecturer(Set<Event> eventsOfLecturer) {
		this.eventsOfLecturer = eventsOfLecturer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
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
		Lecturer other = (Lecturer) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		return true;
	}

}