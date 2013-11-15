package de.nordakademie.timetableservice.service;

import java.util.Set;

import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.model.FieldOfStudy;

public interface CohortService {

	public void saveCohort(Cohort cohort);

	public Cohort load(Long id);

	public Set<Cohort> loadAll();

	public boolean checkCohortExists(FieldOfStudy fieldOfStudy, int year);

}