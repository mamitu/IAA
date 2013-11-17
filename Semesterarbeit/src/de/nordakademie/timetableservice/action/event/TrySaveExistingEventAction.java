package de.nordakademie.timetableservice.action.event;

public class TrySaveExistingEventAction extends AbstractSaveEventAction {

	private static final long serialVersionUID = -8892892141197367702L;

	@Override
	public String execute() throws Exception {
		if (collisions.size() > 0)
			return ERROR;
		event = eventService.updateEvent(eventId, startDate, endDate, breakTime);
		eventService.saveReferencesAndEvent(event, numberOfWeeklyRepetitions, selectedRooms, selectedLecturers, getRelevantCenturies());
		return SUCCESS;
	}

	@Override
	public void validate() {
		checkDates();
		if (selectedLecturerIds.size() == 0) {
			addActionError(getText("error.event.lecturerRequired"));
		}
		if (selectedRoomIds.size() == 0) {
			addActionError(getText("error.event.roomRequired"));
		}
		checkCenturyCohortSelection();
		if (getActionErrors().size() > 0) {
			return;
		}

		fillSelectedEntities();
		checkCenturySelections();
		checkBreakTimeOfEventType();
		collisions = collisionService.getCollisions(eventId, name, startDate, endDate, breakTime, getRelevantCenturies(), selectedLecturers, selectedRooms);
	}

}