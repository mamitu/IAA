package de.nordakademie.timetableservice.action.lecturer;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.service.LecturerService;

public class ShowLecturerListAction extends ActionSupport {

	private static final long serialVersionUID = 7167136547180158901L;
	private LecturerService lecturerService;
	private List<Lecturer> lecturers;

	public void setLecturerService(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

	public List<Lecturer> getLecturers() {
		return lecturers;
	}

	@Override
	public String execute() throws Exception {
		lecturers = lecturerService.loadAll();
		return SUCCESS;
	}

}