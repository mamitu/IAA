package de.nordakademie.timetableservice.action.event;

import java.util.List;

import de.nordakademie.timetableservice.model.Event;

/**
 * Struts-Action zum Anzeigen der Kollisionen und zum Erzwingen des Speicherns
 * der Veranstaltung, die neu angelegt wird.
 * 
 * @author
 * 
 */
public class ConfirmSaveNewEventAction extends AbstractSaveEventAction {

	private static final long serialVersionUID = -2412124316748534362L;

	/**
	 * Liste aller Veranstaltungen, die die nachfolgende ShowEventListAction
	 * anzeigen soll.
	 */
	private List<Event> events;

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Event> getEvents() {
		return events;
	}

	/**
	 * Erzeugt eine neue Veranstaltung mit den eingegebenen Werten und laesst
	 * sie abspeichern. Laedt danach die Liste aller Veranstaltungen fuer die
	 * nachfolgende ShowEventListAction.
	 */
	@Override
	public String execute() throws Exception {
		event = eventService.createNewEvent(name, startDate, endDate, translateEventType(), breakTime);
		fillSelectedEntities();
		eventService.saveReferencesAndEvent(event, numberOfWeeklyRepetitions, selectedRooms, selectedLecturers,
				selectedCenturies);
		events = eventService.loadAll();
		return SUCCESS;
	}

}