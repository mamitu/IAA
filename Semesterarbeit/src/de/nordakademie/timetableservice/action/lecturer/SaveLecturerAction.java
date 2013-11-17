package de.nordakademie.timetableservice.action.lecturer;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.service.LecturerService;

public class SaveLecturerAction extends ActionSupport {

	private static final long serialVersionUID = -1604435706530679289L;
	private LecturerService lecturerService;
	private Lecturer lecturer;

	public void setLecturerService(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	@Override
	public String execute() throws Exception {
		lecturerService.saveLecturer(lecturer);
		return SUCCESS;
	}

	@Override
	public void validate() {
		if (lecturerService.checkEmailExists(lecturer.getEmailAddress())) {
			addActionError(getText("error.lecturer.existingEmailAddress"));
		}
	}
}