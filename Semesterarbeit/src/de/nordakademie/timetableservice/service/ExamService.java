package de.nordakademie.timetableservice.service;

import java.util.List;

import de.nordakademie.timetableservice.model.Exam;

public interface ExamService {

	public void saveExam(Exam exam);

	public Exam load(Long id);

	public List<Exam> loadAll();

}