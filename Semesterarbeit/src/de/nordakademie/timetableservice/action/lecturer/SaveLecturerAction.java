package de.nordakademie.timetableservice.action.lecturer;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.service.LecturerService;

/**
 * Struts-Action zum Speichern eines neuen Dozenten. Validiert zunaechst die
 * eingegebenen Werte und zeigt eventuelle Fehlermeldungen an. Sind keine Fehler
 * aufgetreten wird der Dozent gespeichert.
 * 
 * @author
 */
public class SaveLecturerAction extends ActionSupport {

	private static final long serialVersionUID = -1604435706530679289L;

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

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	/**
	 * Laesst den Dozenten abspeichern.
	 */
	@Override
	public String execute() throws Exception {
		lecturerService.saveLecturer(lecturer);
		return SUCCESS;
	}

	/**
	 * Prueft, ob die email-Adresse des Dozenten bereits existiert. Erzeugt
	 * entsprechende Fehlermeldung.
	 */
	@Override
	public void validate() {
		if (lecturerService.checkEmailExists(lecturer.getEmailAddress())) {
			addActionError(getText("error.lecturer.existingEmailAddress"));
		}
	}
}