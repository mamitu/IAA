package de.nordakademie.timetableservice.action.event;

import java.util.Calendar;
import java.util.Date;

/**
 * Struts-Action zum Speichern einer neuen Veranstalung
 * 
 * @author
 * 
 */
public class TrySaveNewEventAction extends AbstractSaveEventAction {

	private static final long serialVersionUID = 1813501256189408656L;

	/**
	 * Prueft zunaechst, ob Kollisionen beim moeglichen Anlegen einer
	 * Veranstaltung entstehen wuerden. Wenn nein, wird sie angelegt (mit ihren
	 * Folgeterminen). Falls Kollisionen entstehen wuerden, wird zu einer
	 * Fehlerseite navigiert, die dem Nutzer die Entscheidung laesst, ob er die
	 * Veranstaltung trotzdem anlegen moechte.
	 */
	@Override
	public String execute() throws Exception {
		if (collisions.size() > 0) {
			return ERROR;
		}
		event = eventService.createNewEvent(name, startDate, endDate, translateEventType(), breakTime);
		eventService.saveReferencesAndEvent(event, numberOfWeeklyRepetitions, selectedRooms, selectedLecturers,
				selectedCenturies);
		return SUCCESS;
	}

	/**
	 * Validiert die eingegebenen Werte und erzeugt entsprechende
	 * Fehlermeldungen. Validiert werden:<br>
	 * - Name<br>
	 * - Start- und Enddatum<br>
	 * - wurden Dozenten selektiert<br>
	 * - wurden Raeume selektiert<br>
	 * - ist die Pausenzeit valide<br>
	 * - ist die Anzahl woechentlicher Wiederholungen valide<br>
	 * - sind Eingaben zum Eventtyp valide<br>
	 * <br>
	 * Falls die Werte valide sind wird geprueft, ob Kollisionen im Stundenplan
	 * mit anderen Veranstaltungen, aufgrund von Pausenzeiten oder Raumgroessen,
	 * entstehen wuerden. Diese Kollisionen werden in Form einer Liste
	 * bereitgestellt.
	 */
	@Override
	public void validate() {
		if (name.equals("")) {
			addActionError(getText("error.event.nameRequired"));
		}
		if (eventType == null) {
			addActionError(getText("error.event.eventTypeRequired"));
		}
		checkDates();
		checkCenturyCohortSelection();
		if (selectedLecturerIds.size() == 0) {
			addActionError(getText("error.event.lecturerRequired"));
		}
		if (selectedRoomIds.size() == 0) {
			addActionError(getText("error.event.roomRequired"));
		}
		if (breakTime == null || breakTime < 0) {
			addActionError(getText("error.event.invalidBreakTime"));
		}
		if (numberOfWeeklyRepetitions < 0) {
			addActionError(getText("error.event.invalidNumberOfWeeklyRepetitions"));
		}

		if (getActionErrors().size() != 0) {
			return;
		}
		fillSelectedEntities();
		checkCenturySelections();
		checkBreakTimeOfEventType();

		// Ab jetzt wird zur DB gegangen, deswegen nur weitermachen, wenn
		// bisherige Werte valide sind
		if (getActionErrors().size() == 0) {
			Calendar c = Calendar.getInstance();
			// Kollisionen fuer alle woechentlichen Wiederholungen pruefen, in
			// jeder Schleife um 7 Tage erhoehen
			for (int i = 0; i <= numberOfWeeklyRepetitions; i++) {
				c.setTime(startDate);
				c.add(Calendar.DATE, 7 * i);
				Date tempStartDate = c.getTime();

				c.setTime(endDate);
				c.add(Calendar.DATE, 7 * i);
				Date tempEndDate = c.getTime();
				collisions.addAll(collisionService.getCollisions(eventId, name, tempStartDate, tempEndDate, breakTime,
						selectedCenturies, selectedLecturers, selectedRooms));
			}
		}
	}
}