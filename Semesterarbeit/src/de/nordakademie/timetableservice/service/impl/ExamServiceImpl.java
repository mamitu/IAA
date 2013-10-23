package de.nordakademie.timetableservice.service.impl;

import java.util.List;

import de.nordakademie.timetableservice.dao.ExamDAO;
import de.nordakademie.timetableservice.model.Exam;
import de.nordakademie.timetableservice.service.ExamService;

public class ExamServiceImpl implements ExamService {

	private ExamDAO examDAO;

	@Override
	public void saveExam(Exam exam) {
		examDAO.save(exam);
	}

	@Override
	public Exam load(Long id) {
		return examDAO.load(id);
	}

	public void setExamDAO(ExamDAO examDAO) {
		this.examDAO = examDAO;
	}

	@Override
	public List<Exam> loadAll() {
		return this.examDAO.loadAll();
	}

}