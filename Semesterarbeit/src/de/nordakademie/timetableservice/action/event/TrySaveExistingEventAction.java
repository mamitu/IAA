package de.nordakademie.timetableservice.action.event;

/**
 * Struts-Action zum Speichern einer bereits existierenden Veranstalung
 * 
 * @author mm, rs
 * 
 */
public class TrySaveExistingEventAction extends AbstractSaveEventAction {

	private static final long serialVersionUID = -8892892141197367702L;

	/**
	 * Prueft zunaechst, ob Kollisionen beim moeglichen Anlegen einer
	 * Veranstaltung entstehen wuerden. Wenn nein, wird sie angelegt. Falls
	 * Kollisionen entstehen wuerden, wird zu einer Fehlerseite navigiert, die
	 * dem Nutzer die Entscheidung laesst, ob er die Veranstaltung trotzdem
	 * anlegen moechte.
	 */
	@Override
	public String execute() throws Exception {
		if (collisions.size() > 0)
			return ERROR;
		event = eventService.updateEvent(eventId, startDate, endDate, breakTime);
		eventService.saveReferencesAndEvent(event, numberOfWeeklyRepetitions, selectedRooms, selectedLecturers,
				selectedCenturies);
		return SUCCESS;
	}

	/**
	 * Validiert die eingegebenen Werte und erzeugt entsprechende
	 * Fehlermeldungen. Validiert werden:<br>
	 * - Start- und Enddatum<br>
	 * - wurden Dozenten selektiert<br>
	 * - wurden Raeume selektiert<br>
	 * - ist die Pausenzeit valide<br>
	 * - sind Eingaben zum Eventtyp valide<br>
	 * <br>
	 * Falls die Werte valide sind wird geprueft, ob Kollisionen im Stundenplan
	 * mit anderen Veranstaltungen, aufgrund von Pausenzeiten oder Raumgroessen,
	 * entstehen wuerden. Diese Kollisionen werden in Form einer Liste
	 * bereitgestellt.
	 */
	@Override
	public void validate() {
		checkDates();
		if (selectedLecturerIds.size() == 0) {
			addActionError(getText("error.event.lecturerRequired"));
		}
		if (selectedRoomIds.size() == 0) {
			addActionError(getText("error.event.roomRequired"));
		}
		if (breakTime == null || breakTime < 0) {
			addActionError(getText("error.event.invalidBreakTime"));
		}
		checkCenturyCohortSelection();
		if (getActionErrors().size() > 0) {
			return;
		}
		// Ab jetzt wird zur DB gegangen, deswegen nur weitermachen, wenn
		// bisherige Werte valide sind

		fillSelectedEntities();
		checkCenturySelections();
		checkBreakTimeOfEventType();
		collisions = collisionService.getCollisions(eventId, name, startDate, endDate, breakTime, selectedCenturies,
				selectedLecturers, selectedRooms);
	}

}