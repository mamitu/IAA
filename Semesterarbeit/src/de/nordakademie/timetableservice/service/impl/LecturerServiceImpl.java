package de.nordakademie.timetableservice.service.impl;

import java.util.List;
import java.util.Set;

import de.nordakademie.timetableservice.business.Collision;
import de.nordakademie.timetableservice.dao.LecturerDAO;
import de.nordakademie.timetableservice.model.Event;
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
	public Set<Lecturer> loadAll() {
		return this.lecturerDAO.loadAll();
	}

	@Override
	public Set<Lecturer> findLecturersByEvent(Event event) {
		return lecturerDAO.findLecturersByEvent(event.getId());
	}

	@Override
	public boolean checkEmailExists(String emailAddress) {
		return lecturerDAO.findLecturersByEmailAddress(emailAddress).isEmpty() ? false : true;
	}

	@Override
	public void getCollisions(Event event, List<Lecturer> lecturersToCheck, List<Collision> collisions) {
		// TODO Auto-generated method stub
	}

}