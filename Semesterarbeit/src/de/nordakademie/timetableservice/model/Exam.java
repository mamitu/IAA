package de.nordakademie.timetableservice.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class Exam extends Event {

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Century> centuries;

	public List<Century> getCenturies() {
		return centuries;
	}

	public void setCenturies(List<Century> centuries) {
		this.centuries = centuries;
	}

}
