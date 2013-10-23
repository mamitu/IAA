package de.nordakademie.timetableservice.service.impl;

import java.util.List;

import de.nordakademie.timetableservice.dao.LecturerDAO;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.service.LecturerService;

public class LecturerServiceImpl implements LecturerService {

	private LecturerDAO lecturerDAO;

	@Override
	public void saveLecturer(Lecturer lecturer) {
		lecturerDAO.save(lecturer);
	}

	@Override
	public Lecturer load(Long id) {
		return lecturerDAO.load(id);
	}

	public void setLecturerDAO(LecturerDAO lecturerDAO) {
		this.lecturerDAO = lecturerDAO;
	}

	@Override
	public List<Lecturer> loadAll() {
		return this.lecturerDAO.loadAll();
	}

}