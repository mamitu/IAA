package de.nordakademie.timetableservice.model;

public enum RoomType {

	STANDARD("roomType.standard", 0), LABORATORY("roomType.laboratory", 0), COMPUTER_LAB("roomType.computer_lab", 15), AUDIMAX("roomType.audimax", 0);

	private RoomType(String name, int minimalChangeTime) {
		this.name = name;
		this.minimalChangeTime = minimalChangeTime;
	}

	public int getMinimalChangeTime() {
		return minimalChangeTime;
	}

	public String getName() {
		return name;
	}

	private int minimalChangeTime;
	private String name;

}