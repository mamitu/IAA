package de.nordakademie.timetableservice.action.cohort;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.service.CohortService;

public class ShowCohortListAction extends ActionSupport {

	private static final long serialVersionUID = -2761910095892820938L;
	private CohortService cohortService;
	private List<Cohort> cohorts;

	public void setCohortService(CohortService cohortService) {
		this.cohortService = cohortService;
	}

	public List<Cohort> getCohorts() {
		return cohorts;
	}

	@Override
	public String execute() throws Exception {
		cohorts = cohortService.loadAll();
		return SUCCESS;
	}

}