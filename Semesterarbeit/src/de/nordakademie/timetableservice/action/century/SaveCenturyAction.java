package de.nordakademie.timetableservice.action.century;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.service.CenturyService;
import de.nordakademie.timetableservice.service.CohortService;

/**
 * Struts-Action zum Speichern einer neuen Zenturie. Validiert zunaechst die
 * eingegebenen Werte und zeigt eventuelle Fehlermeldungen an. Sind keine Fehler
 * aufgetreten wird die Zenturie gespeichert.
 * 
 * @author
 */
public class SaveCenturyAction extends ActionSupport implements Preparable {

	private static final long serialVersionUID = -2215522977587675194L;

	/**
	 * Service-Klasse fuer Zenturien.
	 */
	private CenturyService centuryService;

	/**
	 * Service-Klasse fuer Kohorten.
	 */
	private CohortService cohortService;

	/**
	 * Die zu speichernde Zenturie.
	 */
	private Century century;

	/**
	 * Liste mit den Kohorten-IDs, die der Nutzer in der Maske selektiert hat.
	 */
	private List<Long> selectedCohortIds = new LinkedList<Long>();

	/**
	 * Map der bereits angelegten Kohorten.<br>
	 * Key: ID der Kohorte<br>
	 * Value: Name der Kohorte
	 */
	private Map<Long, String> availableCohorts;

	/**
	 * Suffix des Zenturiennamens.
	 */
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

	/**
	 * Laesst eine neue Zenturie mit den eingegebenen Werten erstellen und
	 * speichern.
	 */
	@Override
	public String execute() throws Exception {
		centuryService.saveCentury(century, suffix, selectedCohortIds.get(0));
		return SUCCESS;
	}

	/**
	 * Validiert die eingegebenen Werte und erzeugt entsprechende
	 * Fehlermeldungen. Validiert werden:<br>
	 * - wurde eine Kohorte selektiert<br>
	 * - ist die Pausenzeit valide<br>
	 * <br>
	 * Falls die Werte valide sind wird geprueft, ob bereits eine Zenturie mit
	 * einem solchen Namen existiert.
	 */
	@Override
	public void validate() {
		if (selectedCohortIds.size() == 0) {
			addActionError(getText("error.century.cohortRequired"));
		}
		if (century.getBreakTime() == null || century.getBreakTime() < 0) {
			addActionError(getText("error.century.breakTimeRequired"));
		}
		// Ab jetzt wird zur DB gegangen, deswegen nur weitermachen, wenn
		// bisherige Werte valide sind
		if (getActionErrors().size() > 0) {
			return;
		}
		if (centuryService.checkNameExists(suffix, selectedCohortIds.get(0))) {
			addActionError(getText("error.century.existingCenturyName"));
		}
	}

	/**
	 * Ermittelt die bereits angelegten Kohorten und stellt sie bereit. Muss
	 * gemacht werden, um nach fehlgeschlagender Validierung wieder alle
	 * Kohorten anzuzeigen.
	 */
	@Override
	public void prepare() throws Exception {
		availableCohorts = cohortService.getAvailableCohorts();
	}
}