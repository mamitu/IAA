package de.nordakademie.timetableservice.model;

public enum EventType {

	SEMINAR("eventType.seminar", 0), EXAM("eventType.exam", 30), LECTURE("eventType.lecture", 0), ELECTIVE("eventType.elective", 0);

	private String name;
	private int minimalBreakTime;

	private EventType(String name, int minimalBreakTime) {
		this.name = name;
		this.minimalBreakTime = minimalBreakTime;
	}

	public String getName() {
		return name;
	}

	public int getMinimalBreakTime() {
		return minimalBreakTime;
	}

	@Override
	public String toString() {
		return name;
	}

}