package de.nordakademie.timetableservice.action.event;

/**
 * Struts-Action zum Anlegen einer neuen Veranstaltung.
 * 
 * @author mm
 * 
 */
public class CreateEventAction extends AbstractHandleEventAction {

	private static final long serialVersionUID = 86853074980435433L;

	/**
	 * Laesst eine neue Veranstaltung erstellen und stellt sie bereit.
	 */
	@Override
	public String execute() throws Exception {
		event = eventService.createNewEvent();
		return SUCCESS;
	}
}