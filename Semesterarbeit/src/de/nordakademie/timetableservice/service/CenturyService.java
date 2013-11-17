package de.nordakademie.timetableservice.service;

import java.util.List;
import java.util.Map;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Cohort;
import de.nordakademie.timetableservice.model.Event;

public interface CenturyService {

	public boolean checkNameExists(String suffix, Long cohortId);

	public List<Century> findCenturiesByCohort(Cohort selectedCohort);

	public List<Century> findCenturiesByEvent(Event event);

	public Map<Long, String> getAvailableCenturies();

	public Century getNewCentury();

	public List<Century> load(List<Long> centuryIds);

	public Century load(Long id);

	public List<Century> loadAll();

	public void saveCentury(Century century);

	public void saveCentury(Century century, String suffix, Long cohortId);

}