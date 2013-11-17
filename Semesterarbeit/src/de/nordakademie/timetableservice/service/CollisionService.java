package de.nordakademie.timetableservice.service;

import java.util.Date;
import java.util.List;

import de.nordakademie.timetableservice.model.Century;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.model.Room;

public interface CollisionService {

	public List<String> getCollisions(Long eventId, String eventName, Date startDate, Date endDate, Long breakTime, List<Century> centuriesToCheck,
			List<Lecturer> lecturersToCheck, List<Room> roomsToCheck);

}