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
public class Century {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NaturalId
	@Column(length = 50, nullable = false)
	private String name;

	@Column(name = "number_of_students", nullable = false)
	private int numberOfStudents;

	public Set<Event> getEventsOfCentury() {
		return eventsOfCentury;
	}

	public void setEventsOfCentury(Set<Event> eventsOfCentury) {
		this.eventsOfCentury = eventsOfCentury;
	}

	@Column(nullable = false)
	private int breakTime;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(joinColumns = { @JoinColumn(name = "century_id") }, inverseJoinColumns = { @JoinColumn(name = "event_id") })
	private Set<Event> eventsOfCentury;

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

	public int getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	public int getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(int breakTime) {
		this.breakTime = breakTime;
	}

	public void removeEvent(Event event) {
		if (event == null) {
			throw new IllegalArgumentException();
		}
		event.getCenturies().remove(this);
		this.eventsOfCentury.remove(event);
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
		Century other = (Century) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getName() + " (" + getId() + ")";
	}

	public void associateEvent(Event event) {
		if (event == null) {
			throw new IllegalArgumentException();
		}
		event.getCenturies().add(this);
		this.eventsOfCentury.add(event);
	}

}
