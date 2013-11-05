package de.nordakademie.timetableservice.action.lecturer;

import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.service.LecturerService;

public class ShowLecturerListAction extends ActionSupport {

	private LecturerService lecturerService;
	private Set<Lecturer> lecturers;

	@Override
	public String execute() throws Exception {
		lecturers = lecturerService.loadAll();
		return SUCCESS;
	}

	public Set<Lecturer> getLecturers() {
		return lecturers;
	}

	public void setLecturers(Set<Lecturer> lecturers) {
		this.lecturers = lecturers;
	}

	public void setLecturerService(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

}
