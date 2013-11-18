package de.nordakademie.timetableservice.service;

import java.util.List;
import java.util.Map;

import de.nordakademie.timetableservice.model.Lecturer;

/**
 * Interface fuer den Dozenten-Service, das alle Methoden bereitstellt, um mit
 * Dozenten zu arbeiten.
 * 
 * @author mm
 */
public interface LecturerService {

	/**
	 * Prueft, ob bereits ein Dozent mit dieser email-Adresse in der Datenbank
	 * existiert.
	 * 
	 * @param emailAddress
	 *            die zu ueberpruefende email Adresse
	 * @return true, falls bereits ein Dozent mit der email Adresse existiert.
	 */
	public boolean checkEmailExists(String emailAddress);

	/**
	 * Instanziiert einen Dozenten.
	 * 
	 * @return ein neues Dozent-Objekt
	 */
	public Lecturer createNewLecturer();

	/**
	 * Ermittelt alle angelegten Dozenten in einer Map. Diese entaelt als Key
	 * die ID des Dozenten und als Value dessen Namen.
	 * 
	 * @return Map, aller angelegten Dozenten, die als Key die ID der Dozenten
	 *         und als Value deren Namen enthaelt.
	 */
	public Map<Long, String> getAvailableLecturers();

	/**
	 * Laedt eine Liste aller angelegten Dozenten mit den uebergebenen IDs aus
	 * der Datenbank.
	 * 
	 * @param id
	 *            Die ID der zu ladenden Dozenten
	 * @return Liste aller Dozenten mit den uebergebenen IDs
	 */
	public List<Lecturer> load(List<Long> lecturerIds);

	/**
	 * Laedt einen angelegten Dozent aus der Datenbank.
	 * 
	 * @param id
	 *            Die ID des Dozenten
	 * @return Den Dozent mit der ID aus der Datenbank oder null, falls kein
	 *         Dozent mit dieser ID existiert
	 */
	public Lecturer load(Long id);

	/**
	 * Laedt eine Liste aller angelegten Dozenten aus der Datenbank.
	 * 
	 * @return Liste aller Dozenten
	 */
	public List<Lecturer> loadAll();

	/**
	 * Speichert den Dozenten
	 * 
	 * @param room
	 *            Zu speichernder Dozent
	 */
	public void saveLecturer(Lecturer lecturer);

}