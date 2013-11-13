package de.nordakademie.timetableservice.action.century;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.service.CenturyService;

public class SaveCenturyAction extends ActionSupport {

	private Century century;
	private CenturyService centuryService;

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
		centuryService.saveCentury(century);
		return super.execute();
	}

	@Override
	public void validate() {
		if (century.getId() == null) {
			if (centuryService.checkNameExists(century.getName())) {
				addFieldError("lecturer.name", getText("error.existingCenturyName"));
			}
		} else {
			if (centuryService.checkNameExistsForAnotherId(century.getId(), century.getName())) {
				addFieldError("lecturer.name", getText("error.existingCenturyName"));
			}
		}
	}
}
