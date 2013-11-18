package de.nordakademie.timetableservice.action.cohort;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.service.CohortService;

/**
 * Struts-Action zum Anlegen einer neuen Kohorte.
 * 
 * @author
 * 
 */
public class CreateCohortAction extends ActionSupport {

	private static final long serialVersionUID = -5786418155923424475L;

	/**
	 * Service-Klasse fuer Kohorten.
	 */
	private CohortService cohortService;

	/**
	 * Kohorte, die angelegt wird.
	 */
	private Cohort cohort;

	public void setCohortService(CohortService cohortService) {
		this.cohortService = cohortService;
	}

	public Cohort getCohort() {
		return cohort;
	}

	/**
	 * Laesst eine neue Kohorte erstellen und stellt sie bereit.
	 */
	@Override
	public String execute() throws Exception {
		cohort = cohortService.createCohort();
		return SUCCESS;
	}

}