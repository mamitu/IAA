package de.nordakademie.timetableservice.service;

import de.nordakademie.timetableservice.model.Lecturer;

public interface LecturerService {

	public void saveLecturer(Lecturer lecturer);

	public Lecturer load(Long id);

}