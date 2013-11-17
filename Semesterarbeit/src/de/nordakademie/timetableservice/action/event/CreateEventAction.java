package de.nordakademie.timetableservice.action.event;

public class CreateEventAction extends AbstractHandleEventAction {

	private static final long serialVersionUID = 86853074980435433L;

	@Override
	public String execute() throws Exception {
		event = eventService.createNewEvent();
		return SUCCESS;
	}
}