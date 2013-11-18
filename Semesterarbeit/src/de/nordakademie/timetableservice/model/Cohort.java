package de.nordakademie.timetableservice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NaturalId;

/**
 * Entitaet, die eine Kohorte repraesentiert
 * 
 * @author
 * 
 */
@Entity
public class Cohort {

	/**
	 * Die ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Der Jahrgang
	 */
	@NaturalId
	@Column(nullable = false)
	private int year;

	/**
	 * Die Studienrichtung
	 */
	@NaturalId
	@Enumerated
	private FieldOfStudy fieldOfStudy;

	/**
	 * Liste der Zenturien, die zur Kohorte gehoeren
	 */
	@OneToMany(mappedBy = "cohort")
	private List<Century> centuries;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public FieldOfStudy getFieldOfStudy() {
		return fieldOfStudy;
	}

	public void setFieldOfStudy(FieldOfStudy fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

	public List<Century> getCenturies() {
		return centuries;
	}

	public void setCenturies(List<Century> centuries) {
		this.centuries = centuries;
	}

	public void associateCentury(Century century) {
		if (century == null) {
			throw new IllegalArgumentException();
		}
		century.setCohort(this);
		this.centuries.add(century);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldOfStudy == null) ? 0 : fieldOfStudy.hashCode());
		result = prime * result + year;
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
		Cohort other = (Cohort) obj;
		if (fieldOfStudy != other.fieldOfStudy)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return fieldOfStudy.name() + year;
	}

}