package de.nordakademie.timetableservice.action.century;

import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.service.CenturyService;
import de.nordakademie.timetableservice.service.CohortService;

/**
 * Struts-Action zum Anlegen einer neuen Zenturie. Ermittelt die bereits
 * angelegten Kohorten, um sie dem User zum Zuordnen zur Zenturie zur Verfuegung
 * zu stellen.
 * 
 * @author rs
 * 
 */
public class CreateCenturyAction extends ActionSupport implements Preparable {

	private static final long serialVersionUID = 742158603654350L;

	/**
	 * Service-Klasse fuer Kohorten.
	 */
	private CohortService cohortService;

	/**
	 * Service-Klasse fuer Zenturien.
	 */
	private CenturyService centuryService;

	/**
	 * Zenturie, die angelegt wird.
	 */
	private Century century;

	/**
	 * Map der bereits angelegten Kohorten.<br>
	 * Key: ID der Kohorte<br>
	 * Value: Name der Kohorte
	 */
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

	/**
	 * Laesst eine neue Zenturie erstellen und stellt sie bereit.
	 */
	@Override
	public String execute() throws Exception {
		century = centuryService.getNewCentury();
		return SUCCESS;
	}

	/**
	 * Ermittelt die bereits angelegten Kohorten und stellt sie bereit.
	 */
	@Override
	public void prepare() throws Exception {
		availableCohorts = cohortService.getAvailableCohorts();
	}

}