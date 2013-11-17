package de.nordakademie.timetableservice.action.cohort;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.service.CohortService;

public class CreateCohortAction extends ActionSupport {

	private static final long serialVersionUID = -5786418155923424475L;
	private CohortService cohortService;
	private Cohort cohort;

	public void setCohortService(CohortService cohortService) {
		this.cohortService = cohortService;
	}

	public Cohort getCohort() {
		return cohort;
	}

	@Override
	public String execute() throws Exception {
		cohort = cohortService.createCohort();
		return SUCCESS;
	}

}