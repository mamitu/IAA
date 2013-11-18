package de.nordakademie.timetableservice.service;

import java.util.List;
import java.util.Map;

import de.nordakademie.timetableservice.model.Century;

/**
 * Interface fuer den Zenturien-Service, das alle Methoden bereitstellt, um mit
 * Zenturien zu arbeiten.
 * 
 * @author
 */
public interface CenturyService {

	/**
	 * Prueft, ob bereits eine Zenturie mit diesem Namen in der Datenbank
	 * existiert.
	 * 
	 * @param roomName
	 *            der zu ueberpruefende Zenturienname
	 * @return true, falls bereits eine Zenturie mit dem Namen existiert.
	 */
	public boolean checkNameExists(String suffix, Long cohortId);

	/**
	 * Ermittelt alle angelegten Zenturien die zur Kohorte mit der uebergebenen
	 * ID gehoeren.
	 * 
	 * @return Liste aller Zenturien, die zur Kohorte gehoeren
	 */
	public List<Century> findCenturiesByCohortId(Long cohortId);

	/**
	 * Ermittelt alle angelegten Zenturien in einer Map. Diese entaelt als Key
	 * die ID der Zenturie und als Value deren Namen.
	 * 
	 * @return Map, aller angelegten Zenturien, die als Key die ID der Zenturie
	 *         und als Value deren Namen enthaelt.
	 */
	public Map<Long, String> getAvailableCenturies();

	/**
	 * Instanziiert eine Zenturie.
	 * 
	 * @return ein neues Zenturien-Objekt
	 */
	public Century getNewCentury();

	/**
	 * Laedt eine Liste aller angelegten Zenturien mit den uebergebenen IDs aus
	 * der Datenbank.
	 * 
	 * @param id
	 *            Die ID der zu ladenden Zenturien
	 * @return Liste aller Zenturien mit den uebergebenen IDs
	 */
	public List<Century> load(List<Long> centuryIds);

	/**
	 * Laedt einen angelegte Zenturie aus der Datenbank.
	 * 
	 * @param id
	 *            Die ID der Zenturie
	 * @return Die Zenturie mit der ID aus der Datenbank oder null, falls keine
	 *         Zenturie mit dieser ID existiert
	 */
	public Century load(Long id);

	/**
	 * Laedt eine Liste aller angelegten Zenturien aus der Datenbank.
	 * 
	 * @return Liste aller Zenturien
	 */
	public List<Century> loadAll();

	/**
	 * Speichert die Zenturie
	 * 
	 * @param century
	 *            Zu speichernde Zenturie
	 */
	public void saveCentury(Century century);

	/**
	 * Speichert die Zenturie und setzt vorher deren Namen aus der Kohorte und
	 * dem Suffix zusammen
	 * 
	 * @param century
	 *            Zu speichernde Zenturie
	 * @param suffix
	 *            Suffix des Namens
	 * @param cohortId
	 *            Kohorte, zu der die Zenturie gehoert
	 */
	public void saveCentury(Century century, String suffix, Long cohortId);

}