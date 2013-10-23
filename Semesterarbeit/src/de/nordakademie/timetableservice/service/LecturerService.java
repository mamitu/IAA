package de.nordakademie.timetableservice.service;

import java.util.List;

import de.nordakademie.timetableservice.model.Lecturer;

public interface LecturerService {

	public void saveLecturer(Lecturer lecturer);

	public Lecturer load(Long id);

	public List<Lecturer> loadAll();

}