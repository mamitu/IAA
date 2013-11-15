package de.nordakademie.timetableservice.service.impl;

import java.util.Set;

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
	public void saveCohort(Cohort cohort) {
		cohortDAO.save(cohort);
	}

	@Override
	public Cohort load(Long id) {
		return cohortDAO.load(id);
	}

	@Override
	public Set<Cohort> loadAll() {
		return cohortDAO.loadAll();
	}

	@Override
	public boolean checkCohortExists(FieldOfStudy fieldOfStudy, int year) {
		// TODO Auto-generated method stub
		return false;
	}

}