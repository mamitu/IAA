package de.nordakademie.timetableservice.action.lecturer;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.service.LecturerService;

public class SaveLecturerAction extends ActionSupport {

	private Lecturer lecturer;
	private LecturerService lecturerService;

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	public void setLecturerService(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

	@Override
	public String execute() throws Exception {
		lecturerService.saveLecturer(lecturer);
		return super.execute();
	}

	@Override
	public void validate() {
		if (lecturer.getId() == null) {
			if (lecturerService.checkEmailExists(lecturer.getEmailAddress())) {
				addFieldError("lecturer.emailAddress", getText("error.existingEmailAddress"));
			}
		} else {
			if (lecturerService.checkEmailExistsForAnotherId(lecturer.getId(), lecturer.getEmailAddress())) {
				addFieldError("lecturer.emailAddress", getText("error.existingEmailAddress"));
			}
		}
	}
}
