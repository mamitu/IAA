package de.nordakademie.timetableservice.action.century;

import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.service.CenturyService;
import de.nordakademie.timetableservice.service.CohortService;

public class CreateCenturyAction extends ActionSupport implements Preparable {

	private static final long serialVersionUID = 742158603654350L;
	private CohortService cohortService;
	private CenturyService centuryService;
	private Century century;
	private Map<Long, String> availableCohorts;

	public void setCohortService(CohortService cohortService) {
		this.cohortService = cohortService;
	}

	public void setCenturyService(CenturyService centuryService) {
		this.centuryService = centuryService;
	}

	public Century getCentury() {
		return century;
	}

	public Map<Long, String> getAvailableCohorts() {
		return availableCohorts;
	}

	@Override
	public String execute() throws Exception {
		century = centuryService.getNewCentury();
		return SUCCESS;
	}

	@Override
	public void prepare() throws Exception {
		availableCohorts = cohortService.getAvailableCohorts();
	}

}