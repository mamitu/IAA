package de.nordakademie.timetableservice.action.century;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.service.CenturyService;

/**
 * Struts-Action zum Anzeigen aller angelegten Zenturien.
 * 
 * @author rs
 * 
 */
public class ShowCenturyListAction extends ActionSupport {

	private static final long serialVersionUID = 9172352177487043250L;

	/**
	 * Service-Klasse fuer Zenturien.
	 */
	private CenturyService centuryService;

	/**
	 * Liste aller angelegten Zenturien.
	 */
	private List<Century> centuries;

	public void setCenturyService(CenturyService centuryService) {
		this.centuryService = centuryService;
	}

	public List<Century> getCenturies() {
		return centuries;
	}

	/**
	 * Ermittelt alle angelegten Zenturien und stellt sie bereit.
	 */
	@Override
	public String execute() throws Exception {
		centuries = centuryService.loadAll();
		return SUCCESS;
	}

}