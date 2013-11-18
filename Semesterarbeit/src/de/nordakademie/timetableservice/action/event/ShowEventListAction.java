package de.nordakademie.timetableservice.action.event;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.service.EventService;

/**
 * Struts-Action zum Anzeigen aller angelegten Veranstaltungen.
 * 
 * @author rs
 * 
 */
public class ShowEventListAction extends ActionSupport {

	private static final long serialVersionUID = -5318259667593725287L;

	/**
	 * Service-Klasse fuer Veranstaltungen.
	 */
	private EventService eventService;

	/**
	 * Liste aller angelegten Veranstaltungen
	 */
	private List<Event> events;

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public List<Event> getEvents() {
		return events;
	}

	/**
	 * Laesst alle angelegten Veranstaltungen laden und stellt sie bereit.
	 */
	@Override
	public String execute() throws Exception {
		events = eventService.loadAll();
		return SUCCESS;
	}

}