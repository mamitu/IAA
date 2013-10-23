package de.nordakademie.timetableservice.action.event;

import com.opensymphony.xwork2.ActionSupport;

import de.nordakademie.timetableservice.model.Seminar;

public class ShowSeminarAction extends ActionSupport {

	private SeminarService seminarService;
	private Seminar seminar;
	private Long seminarId;

	public Seminar getSeminar() {
		return seminar;
	}

	public void setSeminar(Seminar seminar) {
		this.seminar = seminar;
	}

	public Long getSeminarId() {
		return seminarId;
	}

	public void setSeminarId(Long seminarId) {
		this.seminarId = seminarId;
	}

	public void setSeminarService(SeminarService seminarService) {
		this.seminarService = seminarService;
	}

	@Override
	public String execute() throws Exception {
		if (seminarId != null) {
			seminar = seminarService.load(seminarId);
		} else {
			seminar = new Seminar();
		}
		return SUCCESS;
	}

}
