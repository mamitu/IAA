package de.nordakademie.timetableservice.service;

import java.util.List;
import java.util.Map;

import de.nordakademie.timetableservice.model.Century;

public interface CenturyService {

	public boolean checkNameExists(String suffix, Long cohortId);

	public List<Century> findCenturiesByCohortId(Long cohortId);

	public Map<Long, String> getAvailableCenturies();

	public Century getNewCentury();

	public List<Century> load(List<Long> centuryIds);

	public Century load(Long id);

	public List<Century> loadAll();

	public void saveCentury(Century century);

	public void saveCentury(Century century, String suffix, Long cohortId);

}