package de.nordakademie.timetableservice.service;

import java.util.Set;

import de.nordakademie.timetableservice.business.Collision;
import de.nordakademie.timetableservice.model.Event;
import de.nordakademie.timetableservice.model.Lecturer;

public interface LecturerService {

	public void saveLecturer(Lecturer lecturer);

	public Lecturer load(Long id);

	public Set<Lecturer> loadAll();

	public Set<Lecturer> findLecturersByEvent(Event event);

	public boolean checkEmailExists(String emailAddress);

	public boolean checkEmailExistsForAnotherId(Long lecturerId, String emailAddress);

	public void getCollisionsWithOtherEvents(Event event, Set<Lecturer> lecturersToCheck, Set<Collision> collisions);

}