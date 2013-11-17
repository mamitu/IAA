package de.nordakademie.timetableservice.action.event;

import java.util.Calendar;
import java.util.Date;

public class TrySaveNewEventAction extends AbstractSaveEventAction {

	private static final long serialVersionUID = 1813501256189408656L;

	@Override
	public String execute() throws Exception {
		if (collisions.size() > 0) {
			return ERROR;
		}
		event = eventService.createNewEvent(name, startDate, endDate, translateEventType(), breakTime);
		eventService.saveReferencesAndEvent(event, numberOfWeeklyRepetitions, selectedRooms, selectedLecturers, getRelevantCenturies());
		return SUCCESS;
	}

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

		if (getActionErrors().size() != 0) {
			return;
		}
		fillSelectedEntities();
		checkCenturySelections();
		checkBreakTimeOfEventType();

		if (getActionErrors().size() == 0) {
			Calendar c = Calendar.getInstance();
			for (int i = 0; i <= numberOfWeeklyRepetitions; i++) {
				c.setTime(startDate);
				c.add(Calendar.DATE, 7 * i);
				Date tempStartDate = c.getTime();

				c.setTime(endDate);
				c.add(Calendar.DATE, 7 * i);
				Date tempEndDate = c.getTime();
				collisions.addAll(collisionService.getCollisions(eventId, name, tempStartDate, tempEndDate, breakTime, getRelevantCenturies(), selectedLecturers, selectedRooms));
			}
		}
	}
}