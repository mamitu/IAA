package de.nordakademie.timetableservice.action.lecturer;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.service.LecturerService;

/**
 * Struts-Action zum Anzeigen aller angelegten Dozenten.
 * 
 * @author mm
 * 
 */
public class ShowLecturerListAction extends ActionSupport {

	private static final long serialVersionUID = 7167136547180158901L;

	/**
	 * Service-Klasse fuer Dozenten.
	 */
	private LecturerService lecturerService;

	/**
	 * Liste aller angelegten Dozenten.
	 */
	private List<Lecturer> lecturers;

	public void setLecturerService(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

	public List<Lecturer> getLecturers() {
		return lecturers;
	}

	/**
	 * Ermittelt alle angelegten Dozenten und stellt sie bereit.
	 */
	@Override
	public String execute() throws Exception {
		lecturers = lecturerService.loadAll();
		return SUCCESS;
	}

}