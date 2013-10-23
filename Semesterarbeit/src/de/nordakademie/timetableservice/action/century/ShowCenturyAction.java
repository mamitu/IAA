package de.nordakademie.timetableservice.action.century;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.service.CenturyService;

public class ShowCenturyAction extends ActionSupport {

	private CenturyService centuryService;
	private Century century;
	private Long centuryId;

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

	public void setCenturyService(CenturyService centuryService) {
		this.centuryService = centuryService;
	}

	@Override
	public String execute() throws Exception {
		if (centuryId != null) {
			century = centuryService.load(centuryId);
		} else {
			century = new Century();
		}
		return SUCCESS;
	}

}
