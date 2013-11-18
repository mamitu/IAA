package de.nordakademie.timetableservice.action.event;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.service.EventService;

/**
 * Struts-Action zum Loeschen einer Veranstaltung.
 * 
 * @author
 * 
 */
public class DeleteEventAction extends ActionSupport {

	private static final long serialVersionUID = 6064012231726525810L;

	/**
	 * Service-Klasse fuer Veranstaltungen.
	 */
	private EventService eventService;

	/**
	 * Liste aller Veranstaltungen, die die nachfolgende ShowEventListAction
	 * anzeigen soll.
	 */
	private List<Event> events;

	/**
	 * ID der Veranstaltung, die geloescht werden soll.
	 */
	private Long eventId;

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	/**
	 * Laesst die Veranstaltung mit der uebergebenen ID loeschen. Laedt danach
	 * die Liste aller Veranstaltungen fuer die nachfolgende
	 * ShowEventListAction.
	 */
	@Override
	public String execute() throws Exception {
		eventService.deleteEventWithId(eventId);
		events = eventService.loadAll();
		return SUCCESS;
	}

}