package de.nordakademie.timetableservice.action.cohort;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Cohort;

public class CreateCohortAction extends ActionSupport {

	private Cohort cohort;

	public Cohort getCohort() {
		return cohort;
	}

	@Override
	public String execute() throws Exception {
		cohort = new Cohort();
		return SUCCESS;
	}

}
