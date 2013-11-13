package de.nordakademie.timetableservice.model;

public enum EventType {

	SEMINAR("eventType.seminar", 0), EXAM("eventType.exam", 30), LECTURE("eventType.lecture", 0), ELECTIVE("eventType.elective", 0);

	@Override
	public String toString() {
		return name;
	}

	public int getMinimalChangeTime() {
		return minimalChangeTime;
	}

	private String name;
	private int minimalChangeTime;

	private EventType(String name, int minimalChangeTime) {
		this.name = name;
		this.minimalChangeTime = minimalChangeTime;
	}

	public String getName() {
		return name;
	}

}