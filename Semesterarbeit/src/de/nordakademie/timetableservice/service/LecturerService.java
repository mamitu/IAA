package de.nordakademie.timetableservice.service;

import java.util.List;
import java.util.Map;

import de.nordakademie.timetableservice.model.Lecturer;

public interface LecturerService {

	public boolean checkEmailExists(String emailAddress);

	public Lecturer createNewLecturer();

	public Map<Long, String> getAvailableLecturers();

	public List<Lecturer> load(List<Long> lecturerIds);

	public Lecturer load(Long id);

	public List<Lecturer> loadAll();

	public void saveLecturer(Lecturer lecturer);

}