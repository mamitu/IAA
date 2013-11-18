package de.nordakademie.timetableservice.action.lecturer;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.service.LecturerService;

/**
 * Struts-Action zum Anlegen eines neuen Dozenten.
 * 
 * @author
 * 
 */
public class CreateLecturerAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	/**
	 * Service-Klasse fuer Dozenten.
	 */
	private LecturerService lecturerService;

	/**
	 * Dozent, der angelegt wird.
	 */
	private Lecturer lecturer;

	public void setLecturerService(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

	public Lecturer getLecturer() {
		return lecturer;
	}

	/**
	 * Laesst einen neuen Dozenten anlegen und stellt ihn bereit.
	 */
	@Override
	public String execute() throws Exception {
		lecturer = lecturerService.createNewLecturer();
		return SUCCESS;
	}
}