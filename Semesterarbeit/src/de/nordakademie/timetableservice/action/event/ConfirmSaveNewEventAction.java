package de.nordakademie.timetableservice.action.event;

import java.util.List;

import de.nordakademie.timetableservice.model.Event;

public class ConfirmSaveNewEventAction extends AbstractSaveEventAction {

	private static final long serialVersionUID = -2412124316748534362L;
	private List<Event> events;

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Event> getEvents() {
		return events;
	}

	@Override
	public String execute() throws Exception {
		event = eventService.createNewEvent(name, startDate, endDate, translateEventType(), breakTime);
		fillSelectedEntities();
		eventService.saveReferencesAndEvent(event, numberOfWeeklyRepetitions, selectedRooms, selectedLecturers, getRelevantCenturies());
		events = eventService.loadAll();
		return SUCCESS;
	}

}