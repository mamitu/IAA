package de.nordakademie.timetableservice.action.century;

import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.service.CenturyService;

public class ShowCenturyListAction extends ActionSupport {

	private CenturyService centuryService;
	private Set<Century> centuries;

	@Override
	public String execute() throws Exception {
		centuries = centuryService.loadAll();
		return SUCCESS;
	}

	public Set<Century> getCenturies() {
		return centuries;
	}

	public void setCenturies(Set<Century> centuries) {
		this.centuries = centuries;
	}

	public void setCenturyService(CenturyService centuryService) {
		this.centuryService = centuryService;
	}

}
