package de.nordakademie.timetableservice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

@Entity
public class Century implements EventParticipant {

	public static Long STANDARD_BREAKTIME = Long.valueOf(15l);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NaturalId
	@Column(length = 50, nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cohort_id")
	private Cohort cohort;

	@Column(name = "number_of_students", nullable = false)
	private int numberOfStudents;

	@Column(nullable = false)
	private Long breakTime;

	@ManyToMany
	@JoinTable(joinColumns = { @JoinColumn(name = "century_id") }, inverseJoinColumns = { @JoinColumn(name = "event_id") })
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

	public Cohort getCohort() {
		return cohort;
	}

	public void setCohort(Cohort cohort) {
		this.cohort = cohort;
	}

	public int getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
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
		event.getCenturies().add(this);
		this.events.add(event);
	}

	public void removeEvent(Event event) {
		if (event == null) {
			throw new IllegalArgumentException();
		}
		event.getCenturies().remove(this);
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
		return getName() + " (Studenten: " + getNumberOfStudents() + ")";
	}

}