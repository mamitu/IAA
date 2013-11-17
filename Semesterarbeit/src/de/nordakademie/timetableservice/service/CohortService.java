package de.nordakademie.timetableservice.service;

import java.util.List;
import java.util.Map;

import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.model.FieldOfStudy;

public interface CohortService {

	public boolean checkCohortExists(FieldOfStudy fieldOfStudy, int year);

	public Cohort createCohort();

	public Map<Long, String> getAvailableCohorts();

	public Cohort load(Long id);

	public List<Cohort> loadAll();

	public void saveCohort(Cohort cohort);

	public void saveCohort(Cohort cohort, FieldOfStudy fieldOfStudy);

}