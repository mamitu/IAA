package de.nordakademie.timetableservice.action.event;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Lecture;

public class ShowLectureAction extends ActionSupport {

	private LectureService lectureService;
	private Lecture lecture;
	private Long lectureId;

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}

	public Long getLectureId() {
		return lectureId;
	}

	public void setLectureId(Long lectureId) {
		this.lectureId = lectureId;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	@Override
	public String execute() throws Exception {
		if (lectureId != null) {
			lecture = lectureService.load(lectureId);
		} else {
			lecture = new Lecture();
		}
		return SUCCESS;
	}

}
