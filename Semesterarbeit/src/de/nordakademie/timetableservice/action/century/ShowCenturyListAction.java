package de.nordakademie.timetableservice.action.century;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.service.CenturyService;

public class ShowCenturyListAction extends ActionSupport {

	private static final long serialVersionUID = 9172352177487043250L;
	private CenturyService centuryService;
	private List<Century> centuries;

	public void setCenturyService(CenturyService centuryService) {
		this.centuryService = centuryService;
	}

	public List<Century> getCenturies() {
		return centuries;
	}

	@Override
	public String execute() throws Exception {
		centuries = centuryService.loadAll();
		return SUCCESS;
	}

}