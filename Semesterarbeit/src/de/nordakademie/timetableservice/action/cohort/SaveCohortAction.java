package de.nordakademie.timetableservice.action.cohort;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.model.FieldOfStudy;
import de.nordakademie.timetableservice.service.CohortService;

public class SaveCohortAction extends ActionSupport {

	private static final long serialVersionUID = -2138016758609585534L;
	private CohortService cohortService;
	private Cohort cohort;
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

	@Override
	public String execute() throws Exception {
		cohortService.saveCohort(cohort, translateFieldOfStudy());
		return SUCCESS;
	}

	@Override
	public void validate() {
		if (fieldOfStudy == null) {
			addActionError("error.fieldOfStudyRequired");
		}
		if (cohortService.checkCohortExists(translateFieldOfStudy(), cohort.getYear())) {
			addActionError(getText("error.cohort.existingCohort"));
		}
	}

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