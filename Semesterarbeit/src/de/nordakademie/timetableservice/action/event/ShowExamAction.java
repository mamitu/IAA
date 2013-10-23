package de.nordakademie.timetableservice.action.event;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import de.nordakademie.timetableservice.model.Exam;
import de.nordakademie.timetableservice.service.ExamService;
import de.nordakademie.timetableservice.service.LecturerService;

public class ShowExamAction extends ActionSupport implements Preparable {

	private ExamService examService;
	private LecturerService lecturerService;

	public void setLecturerService(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

	private Exam exam;
	private Long examId;

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public void setExamService(ExamService examService) {
		this.examService = examService;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	@Override
	public void prepare() throws Exception {
		if (examId != null) {
			exam = examService.load(examId);
		} else {
			exam = new Exam();
			exam.setLecturers(lecturerService.loadAll());
		}

	}
}
