package de.nordakademie.timetableservice.action.century;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.service.CenturyService;
import de.nordakademie.timetableservice.service.CohortService;

public class SaveCenturyAction extends ActionSupport implements Preparable {

	private Century century;
	private Cohort cohort;
	private CenturyService centuryService;
	private CohortService cohortService;
	private List<Long> selectedCohortIds;
	private Map<Long, String> availableCohorts;
	private String suffix;

	public Map<Long, String> getAvailableCohorts() {
		return availableCohorts;
	}

	public void setSelectedCohortIds(List<Long> selectedCohortIds) {
		this.selectedCohortIds = selectedCohortIds;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setCohortService(CohortService cohortService) {
		this.cohortService = cohortService;
	}

	public Century getCentury() {
		return century;
	}

	public void setCentury(Century century) {
		this.century = century;
	}

	public void setCenturyService(CenturyService centuryService) {
		this.centuryService = centuryService;
	}

	@Override
	public String execute() throws Exception {
		cohort = cohortService.load(selectedCohortIds.get(0));
		century.setName(cohort.toString() + suffix);
		cohort.associateCentury(century);
		cohortService.saveCohort(cohort);
		centuryService.saveCentury(century);
		return super.execute();
	}

	@Override
	public void validate() {
		if (selectedCohortIds.size() == 0) {
			addActionError("error.cohortRequired");
		}
		if (century.getBreakTime() == null) {
			addActionError("error.breakTimeRequired");
		}
		if (suffix == null) {
			addActionError("error.suffixRequired");
		}

	}

	@Override
	public void prepare() throws Exception {
		selectedCohortIds = new LinkedList<Long>();
		availableCohorts = new HashMap<Long, String>();
		for (Cohort cohort : cohortService.loadAll()) {
			availableCohorts.put(cohort.getId(), cohort.toString());
		}
	}
}
