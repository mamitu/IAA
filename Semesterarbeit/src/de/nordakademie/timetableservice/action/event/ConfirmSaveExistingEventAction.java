package de.nordakademie.timetableservice.action.event;

import java.util.List;

import de.nordakademie.timetableservice.model.Event;

public class ConfirmSaveExistingEventAction extends AbstractSaveEventAction {

	private static final long serialVersionUID = 3885161365075986593L;
	private List<Event> events;

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	@Override
	public String execute() throws Exception {
		event = eventService.updateEvent(eventId, startDate, endDate, breakTime);
		fillSelectedEntities();
		eventService.saveReferencesAndEvent(event, numberOfWeeklyRepetitions, selectedRooms, selectedLecturers, getRelevantCenturies());
		events = eventService.loadAll();
		return SUCCESS;
	}

}