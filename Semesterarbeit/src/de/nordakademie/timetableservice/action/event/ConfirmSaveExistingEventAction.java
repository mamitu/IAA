package de.nordakademie.timetableservice.action.event;

import java.util.List;

import de.nordakademie.timetableservice.model.Event;

/**
 * Struts-Action zum Anzeigen der Kollisionen und zum Erzwingen des Speicherns
 * der Veranstaltung, die bereits existiert.
 * 
 * @author
 * 
 */
public class ConfirmSaveExistingEventAction extends AbstractSaveEventAction {

	private static final long serialVersionUID = 3885161365075986593L;

	/**
	 * Liste aller Veranstaltungen, die die nachfolgende ShowEventListAction
	 * anzeigen soll.
	 */
	private List<Event> events;

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	/**
	 * Aktualisiert die bestehende Veranstaltung mit den neu eingegebenen Werten
	 * und laesst sie abspeichern. Laedt danach die Liste aller Veranstaltungen
	 * fuer die nachfolgende ShowEventListAction.
	 */
	@Override
	public String execute() throws Exception {
		event = eventService.updateEvent(eventId, startDate, endDate, breakTime);
		fillSelectedEntities();
		eventService.saveReferencesAndEvent(event, numberOfWeeklyRepetitions, selectedRooms, selectedLecturers,
				selectedCenturies);
		events = eventService.loadAll();
		return SUCCESS;
	}

}