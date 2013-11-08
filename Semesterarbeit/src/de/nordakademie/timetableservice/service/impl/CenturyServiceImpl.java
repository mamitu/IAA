package de.nordakademie.timetableservice.service.impl;

import java.util.List;
import java.util.Set;

import de.nordakademie.timetableservice.business.Collision;
import de.nordakademie.timetableservice.business.CollisionType;
import de.nordakademie.timetableservice.dao.CenturyDAO;
import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;
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
	public Set<Century> loadAll() {
		return this.centuryDAO.loadAll();
	}

	@Override
	public Set<Century> findCenturiesByEvent(Event event) {
		return centuryDAO.findCenturiesByEvent(event.getId());
	}

	@Override
	public void getCollisionsWithOtherEvents(Event event, List<Century> centuriesToCheck, List<Collision> collisions) {
		Set<Century> centuriesWithExistingEvent = centuryDAO.findCenturiesWithDatesWithoutId(event.getStartDate(), event.getEndDate(), event.getId());
		if (!centuriesWithExistingEvent.isEmpty()) {
			for (Century century : centuriesToCheck) {
				if (centuriesWithExistingEvent.contains(century)) {
					collisions.add(new Collision(CollisionType.ERROR, century.toString(), "label.collision.existingEventForEntity"));
				}
			}
		}
	}

}