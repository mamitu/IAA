package de.nordakademie.timetableservice.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.nordakademie.timetableservice.dao.CenturyDAO;
import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.service.CenturyService;
import de.nordakademie.timetableservice.service.CohortService;

/**
 * Implementation des Zenturien-Services
 * 
 * @author
 * 
 */
public class CenturyServiceImpl implements CenturyService {

	/**
	 * Data Access Object fuer Zenturien
	 */
	private CenturyDAO centuryDAO;

	/**
	 * Service-Klasse fuer Kohorten.
	 */
	private CohortService cohortService;

	public void setCenturyDAO(CenturyDAO centuryDAO) {
		this.centuryDAO = centuryDAO;
	}

	public void setCohortService(CohortService cohortService) {
		this.cohortService = cohortService;
	}

	@Override
	public boolean checkNameExists(String suffix, Long cohortId) {
		String centuryName = cohortService.load(cohortId).toString() + suffix;
		return centuryDAO.findCenturiesByName(centuryName).isEmpty() ? false : true;
	}

	@Override
	public List<Century> findCenturiesByCohortId(Long cohortId) {
		return centuryDAO.findCenturiesByCohort(cohortId);
	}

	@Override
	public Map<Long, String> getAvailableCenturies() {
		Map<Long, String> availableCenturies = new HashMap<Long, String>();
		for (Century century : loadAll()) {
			availableCenturies.put(century.getId(), century.toString());
		}
		return availableCenturies;
	}

	@Override
	public Century getNewCentury() {
		Century century = new Century();
		century.setBreakTime(Century.STANDARD_BREAKTIME);
		return century;
	}

	@Override
	public List<Century> load(List<Long> centuryIds) {
		List<Century> centuries = new LinkedList<Century>();
		for (Long id : centuryIds) {
			centuries.add(load(id));
		}
		return centuries;
	}

	@Override
	public Century load(Long id) {
		return centuryDAO.load(id);
	}

	@Override
	public List<Century> loadAll() {
		return this.centuryDAO.loadAll();
	}

	@Override
	public void saveCentury(Century century) {
		centuryDAO.save(century);
	}

	@Override
	public void saveCentury(Century century, String suffix, Long cohortId) {
		Cohort cohort = cohortService.load(cohortId);
		century.setName(cohort.toString() + suffix);
		cohort.associateCentury(century);
		cohortService.saveCohort(cohort);
		saveCentury(century);
	}
}