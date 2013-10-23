package de.nordakademie.timetableservice.service.impl;

import java.util.List;

import de.nordakademie.timetableservice.dao.CenturyDAO;
import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.service.CenturyService;

public class CenturyServiceImpl implements CenturyService {

	private CenturyDAO centuryDAO;

	@Override
	public void saveCentury(Century century) {
		centuryDAO.save(century);
	}

	@Override
	public Century load(Long id) {
		return centuryDAO.load(id);
	}

	public void setCenturyDAO(CenturyDAO centuryDAO) {
		this.centuryDAO = centuryDAO;
	}

	@Override
	public List<Century> loadAll() {
		return this.centuryDAO.loadAll();
	}

}