package de.nordakademie.timetableservice.action.cohort;

import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.service.CohortService;

public class ShowCohortListAction extends ActionSupport {

	private CohortService cohortService;
	private Set<Cohort> cohorts;

	public Set<Cohort> getCohorts() {
		return cohorts;
	}

	public void setCohortService(CohortService cohortService) {
		this.cohortService = cohortService;
	}

	@Override
	public String execute() throws Exception {
		cohorts = cohortService.loadAll();
		return SUCCESS;
	}

}
