package de.nordakademie.timetableservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.nordakademie.timetableservice.dao.CohortDAO;
import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.model.FieldOfStudy;
import de.nordakademie.timetableservice.service.CohortService;

public class CohortServiceImpl implements CohortService {

	private CohortDAO cohortDAO;

	public void setCohortDAO(CohortDAO cohortDAO) {
		this.cohortDAO = cohortDAO;
	}

	@Override
	public boolean checkCohortExists(FieldOfStudy fieldOfStudy, int year) {
		return cohortDAO.findCohortsByFieldOfStudyAndYear(fieldOfStudy, year).isEmpty() ? false : true;
	}

	@Override
	public Cohort createCohort() {
		return new Cohort();
	}

	@Override
	public Map<Long, String> getAvailableCohorts() {
		Map<Long, String> availableCohorts = new HashMap<Long, String>();
		for (Cohort cohort : loadAll()) {
			availableCohorts.put(cohort.getId(), cohort.toString());
		}
		return availableCohorts;
	}

	@Override
	public Cohort load(Long id) {
		return cohortDAO.load(id);
	}

	@Override
	public List<Cohort> loadAll() {
		return cohortDAO.loadAll();
	}

	@Override
	public void saveCohort(Cohort cohort) {
		cohortDAO.save(cohort);
	}

	@Override
	public void saveCohort(Cohort cohort, FieldOfStudy fieldOfStudy) {
		cohort.setFieldOfStudy(fieldOfStudy);
		saveCohort(cohort);
	}

}