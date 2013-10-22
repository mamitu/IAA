package de.nordakademie.timetableservice.action;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.service.LecturerService;

public class ShowLecturerAction extends ActionSupport {

	private LecturerService lecturerService;
	private Lecturer lecturer;
	private Long id;

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLecturerService(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

	@Override
	public String execute() throws Exception {
		if (id != null) {
			lecturer = lecturerService.load(id);
		} else {
			lecturer = new Lecturer();
		}
		return SUCCESS;
	}

}
