package de.nordakademie.timetableservice.action.event;

import java.util.List;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.EventType;

public class AbstractSaveEventAction extends AbstractHandleEventAction {

	private static final long serialVersionUID = -4239230212348938285L;

	protected void fillSelectedEntities() {
		selectedCenturies = centuryService.load(selectedCenturyIds);
		selectedLecturers = lecturerService.load(selectedLecturerIds);
		selectedRooms = roomService.load(selectedRoomIds);
		if (!isCenturySelected)
			selectedCohort = cohortService.load(selectedCohortIds.get(0));
	}

	protected void checkCenturySelections() {
		if (translateEventType().equals(EventType.SEMINAR)) {
			if (selectedCenturyIds.size() != 0) {
				addActionError(getText("error.seminarNoCenturies"));
			}
		} else {
			if (isCenturySelected && selectedCenturyIds.size() == 0) {
				addActionError(getText("error.centuriesRequired"));
			}
		}
		if (isCenturySelected && translateEventType().equals(EventType.ELECTIVE)) {
			addActionError(getText("error.electiveJustCohort"));
		}
	}

	protected List<Century> getRelevantCenturies() {
		if (isCenturySelected)
			return selectedCenturies;
		return centuryService.findCenturiesByCohort(selectedCohort);
	}

	protected EventType translateEventType() {
		switch (eventType) {
		case ("eventType.exam"): {
			return EventType.EXAM;
		}
		case ("eventType.lecture"): {
			return EventType.LECTURE;
		}
		case ("eventType.elective"): {
			return EventType.ELECTIVE;
		}
		case ("eventType.seminar"): {
			return EventType.SEMINAR;
		}
		}
		return null;
	}

	protected void checkCenturyCohortSelection() {
		if (!isCenturySelected && selectedCohortIds.size() == 0) {
			addActionError(getText("error.event.noCohortSelected"));
		}
	}

	protected void checkBreakTimeOfEventType() {
		if (breakTime < translateEventType().getMinimalBreakTime()) {
			String errorMessage = getText("error.eventTypeMoreBreakTime");
			errorMessage = errorMessage.replace("$eventType", getText(translateEventType().getName()));
			errorMessage = errorMessage.replace("$breakTime", getText(String.valueOf(translateEventType().getMinimalBreakTime())));
			addActionError(errorMessage);
		}
	}

}