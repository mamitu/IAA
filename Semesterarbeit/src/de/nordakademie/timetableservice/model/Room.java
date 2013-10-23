package de.nordakademie.timetableservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 50, nullable = false)
	private String name;

	@Column(name = "number_of_seats", nullable = false)
	private int NumberOfSeats;

	@Column(nullable = false)
	private int breakTime;

	public int getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(int breakTime) {
		this.breakTime = breakTime;
	}

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

	public int getNumberOfSeats() {
		return NumberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		NumberOfSeats = numberOfSeats;
	}

}
