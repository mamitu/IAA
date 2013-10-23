package de.nordakademie.timetableservice.action.lecturer;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.service.LecturerService;

public class ShowLecturerAction extends ActionSupport {

	private LecturerService lecturerService;
	private Lecturer lecturer;
	private Long lecturerId;

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	public Long getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(Long lecturerId) {
		this.lecturerId = lecturerId;
	}

	public void setLecturerService(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

	@Override
	public String execute() throws Exception {
		if (lecturerId != null) {
			lecturer = lecturerService.load(lecturerId);
		} else {
			lecturer = new Lecturer();
		}
		return SUCCESS;
	}

}
