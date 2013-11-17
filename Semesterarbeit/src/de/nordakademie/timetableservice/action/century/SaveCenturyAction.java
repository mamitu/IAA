package de.nordakademie.timetableservice.action.century;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.service.CenturyService;
import de.nordakademie.timetableservice.service.CohortService;

public class SaveCenturyAction extends ActionSupport implements Preparable {

	private static final long serialVersionUID = -2215522977587675194L;
	private CenturyService centuryService;
	private CohortService cohortService;
	private Century century;
	private List<Long> selectedCohortIds = new LinkedList<Long>();
	private Map<Long, String> availableCohorts;
	private String suffix;

	public void setCenturyService(CenturyService centuryService) {
		this.centuryService = centuryService;
	}

	public void setCohortService(CohortService cohortService) {
		this.cohortService = cohortService;
	}

	public void setCentury(Century century) {
		this.century = century;
	}

	public Century getCentury() {
		return century;
	}

	public List<Long> getSelectedCohortIds() {
		return this.selectedCohortIds;
	}

	public void setSelectedCohortIds(List<Long> selectedCohortIds) {
		this.selectedCohortIds = selectedCohortIds;
	}

	public Map<Long, String> getAvailableCohorts() {
		return availableCohorts;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public String execute() throws Exception {
		centuryService.saveCentury(century, suffix, selectedCohortIds.get(0));
		return SUCCESS;
	}

	@Override
	public void validate() {
		if (selectedCohortIds.size() == 0) {
			addActionError(getText("error.century.cohortRequired"));
		}
		if (century.getBreakTime() == null || century.getBreakTime() < 0) {
			addActionError(getText("error.century.breakTimeRequired"));
		}
		if (getActionErrors().size() > 0) {
			return;
		}
		if (centuryService.checkNameExists(suffix, selectedCohortIds.get(0))) {
			addActionError(getText("error.century.existingCenturyName"));
		}
	}

	@Override
	public void prepare() throws Exception {
		availableCohorts = cohortService.getAvailableCohorts();
	}
}