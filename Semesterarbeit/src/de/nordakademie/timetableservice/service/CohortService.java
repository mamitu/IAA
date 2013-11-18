package de.nordakademie.timetableservice.service;

import java.util.List;
import java.util.Map;

import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.model.FieldOfStudy;

/**
 * Interface fuer den Kohorten-Service, das alle Methoden bereitstellt, um mit
 * Kohorten zu arbeiten.
 * 
 * @author
 */
public interface CohortService {

	/**
	 * Prueft, ob bereits eine Kohorte mit der Studienrichtung und dem Jahrgang
	 * existiert
	 * 
	 * @param fieldOfStudy
	 *            die zu ueberpruefende Studienrichtung
	 * @param year
	 *            der zu ueberpruefende Jahrgang
	 * @return true, falls bereits eine Kohorte mit dem Jahrgang und der
	 *         Studienrichtung existiert.
	 */
	public boolean checkCohortExists(FieldOfStudy fieldOfStudy, int year);

	/**
	 * Instanziiert eine Kohorte.
	 * 
	 * @return ein neues Kohorten-Objekt
	 */
	public Cohort createCohort();

	/**
	 * Ermittelt alle angelegten Kohorten in einer Map. Diese entaelt als Key
	 * die ID der Kohorte und als Value deren Namen.
	 * 
	 * @return Map, aller angelegten Kohorten, die als Key die ID der Kohorte
	 *         und als Value deren Namen enthaelt.
	 */
	public Map<Long, String> getAvailableCohorts();

	/**
	 * Laedt einen angelegte Kohorte aus der Datenbank.
	 * 
	 * @param id
	 *            Die ID der Kohorte
	 * @return Die Kohorte mit der ID aus der Datenbank oder null, falls keine
	 *         Kohorte mit dieser ID existiert
	 */
	public Cohort load(Long id);

	/**
	 * Laedt eine Liste aller angelegten Kohorten aus der Datenbank.
	 * 
	 * @return Liste aller Kohorten
	 */
	public List<Cohort> loadAll();

	/**
	 * Speichert die Kohorte
	 * 
	 * @param cohort
	 *            Zu speichernde Kohorte
	 */
	public void saveCohort(Cohort cohort);

	/**
	 * Speichert die Kohorte und setzt vorher die Studienrichtung
	 * 
	 * @param cohort
	 *            Zu speichernde Kohorte
	 * @param fieldOfStudy
	 *            Studienrichtung der Kohorte
	 */
	public void saveCohort(Cohort cohort, FieldOfStudy fieldOfStudy);

}