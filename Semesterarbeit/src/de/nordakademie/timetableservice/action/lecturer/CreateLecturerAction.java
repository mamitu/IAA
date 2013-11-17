package de.nordakademie.timetableservice.action.lecturer;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.service.LecturerService;

public class CreateLecturerAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private LecturerService lecturerService;
	private Lecturer lecturer;

	public void setLecturerService(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

	public Lecturer getLecturer() {
		return lecturer;
	}

	@Override
	public String execute() throws Exception {
		lecturer = lecturerService.createNewLecturer();
		return SUCCESS;
	}
}