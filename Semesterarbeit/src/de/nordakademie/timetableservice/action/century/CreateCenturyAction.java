package de.nordakademie.timetableservice.action.century;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.service.CohortService;

public class CreateCenturyAction extends ActionSupport implements Preparable {

	private CohortService cohortService;
	private Century century;
	private Long centuryId;
	private Map<Long, String> availableCohorts;

	public Map<Long, String> getAvailableCohorts() {
		return availableCohorts;
	}

	public Century getCentury() {
		return century;
	}

	public void setCentury(Century century) {
		this.century = century;
	}

	public Long getCenturyId() {
		return centuryId;
	}

	public void setCenturyId(Long centuryId) {
		this.centuryId = centuryId;
	}

	public void setCohortService(CohortService cohortService) {
		this.cohortService = cohortService;
	}

	@Override
	public String execute() throws Exception {
		century = new Century();
		century.setBreakTime(Long.valueOf(15));
		return SUCCESS;
	}

	@Override
	public void prepare() throws Exception {
		availableCohorts = new HashMap<Long, String>();
		for (Cohort cohort : cohortService.loadAll()) {
			availableCohorts.put(cohort.getId(), cohort.toString());
		}
	}

}
