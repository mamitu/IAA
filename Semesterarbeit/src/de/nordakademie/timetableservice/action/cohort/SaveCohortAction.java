package de.nordakademie.timetableservice.action.cohort;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.model.FieldOfStudy;
import de.nordakademie.timetableservice.service.CohortService;

/**
 * Struts-Action zum Speichern einer neuen Kohorte. Validiert zunaechst die
 * eingegebenen Werte und zeigt eventuelle Fehlermeldungen an. Sind keine Fehler
 * aufgetreten wird die Kohorte gespeichert.
 * 
 * @author
 */
public class SaveCohortAction extends ActionSupport {

	private static final long serialVersionUID = -2138016758609585534L;

	/**
	 * Service-Klasse fuer Kohorten.
	 */
	private CohortService cohortService;

	/**
	 * Die zu speichernde Kohorte.
	 */
	private Cohort cohort;

	/**
	 * Die Studienrichtung der Kohorte. Benoetigt, weil der Enumtyp nicht
	 * richtig konvertiert wird.
	 */
	private String fieldOfStudy;

	public void setCohortService(CohortService cohortService) {
		this.cohortService = cohortService;
	}

	public Cohort getCohort() {
		return this.cohort;
	}

	public void setCohort(Cohort cohort) {
		this.cohort = cohort;
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

	/**
	 * Laesst die erstellte Kohorte speichern.
	 */
	@Override
	public String execute() throws Exception {
		cohortService.saveCohort(cohort, translateFieldOfStudy());
		return SUCCESS;
	}

	/**
	 * Validiert die eingegebenen Werte und erzeugt entsprechende
	 * Fehlermeldungen. Validiert wird:<br>
	 * - wurde eine Studienrichtung selektiert<br>
	 * <br>
	 * Falls der Wert valide ist wird geprueft, ob bereits eine Kohorte mit
	 * einem solchen Namen existiert.
	 */
	@Override
	public void validate() {
		if (fieldOfStudy == null) {
			addActionError(getText("error.cohort.fieldOfStudyRequired"));
			return;
		}
		if (cohortService.checkCohortExists(translateFieldOfStudy(), cohort.getYear())) {
			addActionError(getText("error.cohort.existingCohort"));
		}
	}

	/**
	 * Ermittelt den richtigen Enumwert aus der selektierten Studienrichtung.
	 * 
	 * @return Studienrichtung der Kohorte
	 */
	private FieldOfStudy translateFieldOfStudy() {
		switch (fieldOfStudy) {
		case ("I"): {
			return FieldOfStudy.I;
		}
		case ("B"): {
			return FieldOfStudy.B;
		}
		case ("W"): {
			return FieldOfStudy.W;
		}
		}
		return null;
	}

}