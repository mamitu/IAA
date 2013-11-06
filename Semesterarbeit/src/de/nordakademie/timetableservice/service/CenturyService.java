package de.nordakademie.timetableservice.service;

import java.util.List;
import java.util.Set;

import de.nordakademie.timetableservice.business.Collision;
import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Event;

public interface CenturyService {

	public void saveCentury(Century century);

	public Century load(Long id);

	public Set<Century> loadAll();

	public Set<Century> findCenturiesByEvent(Event event);

	public void getCollisions(Event event, List<Century> centuriesToCheck, List<Collision> collisions);

}