package de.nordakademie.timetableservice.action.event;

import de.nordakademie.timetableservice.model.Event;

public class CreateEventAction extends HandleEventAction {

	@Override
	public String execute() throws Exception {
		event = new Event();
		return SUCCESS;
	}

}