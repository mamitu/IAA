package de.nordakademie.timetableservice.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.nordakademie.timetableservice.dao.LecturerDAO;
import de.nordakademie.timetableservice.model.Lecturer;
import de.nordakademie.timetableservice.service.LecturerService;

public class LecturerServiceImpl implements LecturerService {

	private LecturerDAO lecturerDAO;

	@Override
	public boolean checkEmailExists(String emailAddress) {
		return lecturerDAO.findLecturersByEmailAddress(emailAddress).isEmpty() ? false : true;
	}

	@Override
	public Lecturer createNewLecturer() {
		Lecturer lecturer = new Lecturer();
		lecturer.setBreakTime(Lecturer.STANDARD_BREAKTIME);
		return lecturer;
	}

	@Override
	public Map<Long, String> getAvailableLecturers() {
		Map<Long, String> availableLecturers = new HashMap<Long, String>();
		for (Lecturer lecturer : loadAll()) {
			availableLecturers.put(lecturer.getId(), lecturer.toString());
		}
		return availableLecturers;
	}

	@Override
	public List<Lecturer> load(List<Long> lecturerIds) {
		List<Lecturer> lecturers = new LinkedList<Lecturer>();
		for (Long id : lecturerIds) {
			lecturers.add(load(id));
		}
		return lecturers;
	}

	@Override
	public Lecturer load(Long id) {
		return lecturerDAO.load(id);
	}

	@Override
	public List<Lecturer> loadAll() {
		return this.lecturerDAO.loadAll();
	}

	@Override
	public void saveLecturer(Lecturer lecturer) {
		lecturerDAO.save(lecturer);
	}

	public void setLecturerDAO(LecturerDAO lecturerDAO) {
		this.lecturerDAO = lecturerDAO;
	}

}