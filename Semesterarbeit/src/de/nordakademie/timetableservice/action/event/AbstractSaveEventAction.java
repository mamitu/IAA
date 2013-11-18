package de.nordakademie.timetableservice.action.event;

import java.util.LinkedList;
import java.util.List;

import de.nordakademie.timetableservice.model.EventType;

/**
 * Abstrakte Struts-Action, die fuer das Speichern einer Veranstaltung
 * Funktionen und Attribute bereitstellt.
 * 
 * @author rs
 * 
 */
public class AbstractSaveEventAction extends AbstractHandleEventAction {

	private static final long serialVersionUID = -4239230212348938285L;

	/**
	 * Liste mit Kollisionen, die beim Speichern einer Veranstaltung entstehen
	 * koennen
	 */
	protected List<String> collisions = new LinkedList<String>();

	public List<String> getCollisions() {
		return collisions;
	}

	public void setCollisions(List<String> collisions) {
		this.collisions = collisions;
	}

	/**
	 * Laesst die zu den IDs der selektierten Dozenten, Raeume, Zenturien und
	 * Kohorten entsprechenden Objekte aus der DB laden.
	 */
	protected void fillSelectedEntities() {
		selectedLecturers = lecturerService.load(selectedLecturerIds);
		selectedRooms = roomService.load(selectedRoomIds);
		if (isCenturySelected)
			selectedCenturies = centuryService.load(selectedCenturyIds);
		else
			selectedCenturies = centuryService.findCenturiesByCohortId(selectedCohortIds.get(0));
	}

	/**
	 * Validierung, ob zum angegebenen Typ der Veranstaltung ueberhaupt
	 * Zenturien selektiert werden duerfen, Zum Seminar duerfen keine Zenturien
	 * zugeordnet werden und Wahlpflichtkurse werden fuer Kohorten erstellt.
	 * Fuer Vorlesungen und Klausuren muessen Zenturien oder Kohorten gewaehlt
	 * werden. Erzeugt entsprechenden Fehlermeldungen.
	 */
	protected void checkCenturySelections() {
		if (translateEventType().equals(EventType.SEMINAR)) {
			if (selectedCenturyIds.size() != 0 || selectedCohortIds.size() != 0) {
				addActionError(getText("error.event.seminarNoCenturiesOrCohort"));
			}
		} else {
			if (isCenturySelected && selectedCenturyIds.size() == 0) {
				addActionError(getText("error.event.centuriesRequired"));
			}
		}
		if (isCenturySelected && translateEventType().equals(EventType.ELECTIVE)) {
			addActionError(getText("error.event.electiveJustCohort"));
		}
	}

	/**
	 * Ermittelt den richtigen Enumwert aus der selektierten Art der
	 * Veranstaltung.
	 * 
	 * @return Art der Veranstaltung
	 */
	protected EventType translateEventType() {
		switch (eventType) {
		case ("eventType.exam"): {
			return EventType.EXAM;
		}
		case ("eventType.lecture"): {
			return EventType.LECTURE;
		}
		case ("eventType.elective"): {
			return EventType.ELECTIVE;
		}
		case ("eventType.seminar"): {
			return EventType.SEMINAR;
		}
		}
		return null;
	}

	/**
	 * Validierung, ob Kohorten gewaehlt, aber keine selektiert wurden. Erzeugt
	 * entsprechende Fehlermeldung.
	 */
	protected void checkCenturyCohortSelection() {
		if (!isCenturySelected && selectedCohortIds.size() == 0) {
			addActionError(getText("error.event.noCohortSelected"));
		}
	}

	/**
	 * Validierung, ob die angegebene Pausenzeit groesser als die minimale
	 * Pausenzeit der Art der Veranstaltung ist. Erzeugt entsprechende
	 * Fehlermeldung.
	 */
	protected void checkBreakTimeOfEventType() {
		if (breakTime < translateEventType().getMinimalBreakTime()) {
			String errorMessage = getText("error.event.eventTypeMoreBreakTime");
			errorMessage = errorMessage.replace("$eventType", getText(translateEventType().getName()));
			errorMessage = errorMessage.replace("$breakTime",
					getText(String.valueOf(translateEventType().getMinimalBreakTime())));
			addActionError(errorMessage);
		}
	}

}