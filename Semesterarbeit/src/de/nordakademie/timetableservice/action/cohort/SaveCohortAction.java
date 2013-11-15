package de.nordakademie.timetableservice.action.cohort;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.model.FieldOfStudy;
import de.nordakademie.timetableservice.service.CohortService;

public class SaveCohortAction extends ActionSupport {

	private CohortService cohortService;
	private Cohort cohort;

	private String fieldOfStudy;

	public void setCohortService(CohortService cohortService) {
		this.cohortService = cohortService;
	}

	public void setCohort(Cohort cohort) {
		this.cohort = cohort;
	}

	@Override
	public String execute() throws Exception {
		cohort.setFieldOfStudy(translateFieldOfStudy());
		cohortService.saveCohort(cohort);
		return super.execute();
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

	@Override
	public void validate() {
		if (fieldOfStudy == null) {
			addActionError("error.fieldOfStudyRequired");
		}
		if (cohort.getYear() == 0) {
			addActionError("error.yearRequired");
		}

		if (cohortService.checkCohortExists(cohort.getFieldOfStudy(), cohort.getYear())) {
			addActionError(getText("error.existingCohort"));
		}
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}
}
