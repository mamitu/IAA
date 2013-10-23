package de.nordakademie.timetableservice.service;

import java.util.List;

import de.nordakademie.timetableservice.model.Century;

public interface CenturyService {

	public void saveCentury(Century century);

	public Century load(Long id);

	public List<Century> loadAll();

}