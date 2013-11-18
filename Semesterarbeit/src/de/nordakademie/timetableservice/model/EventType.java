package de.nordakademie.timetableservice.model;

/**
 * Enum fuer den Veranstaltungstyp
 * 
 * @author mm
 * 
 */
public enum EventType {

	SEMINAR("eventType.seminar", 0), EXAM("eventType.exam", 30), LECTURE("eventType.lecture", 0), ELECTIVE(
			"eventType.elective", 0);

	/**
	 * Der i18n Eintrag, um spaeter die richtige Uebersetzung zu laden
	 */
	private String name;

	/**
	 * Minimale Pausenzeit den Veranstaltungstyp
	 */
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