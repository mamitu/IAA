package de.nordakademie.timetableservice.model;

import java.util.List;

public class Lecture extends Event {
	private List<Century> centuries;

	public List<Century> getCenturies() {
		return centuries;
	}

	public void setCenturies(List<Century> centuries) {
		this.centuries = centuries;
	}
}
