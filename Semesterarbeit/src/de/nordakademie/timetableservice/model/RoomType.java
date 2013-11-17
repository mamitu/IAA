package de.nordakademie.timetableservice.model;

public enum RoomType {

	STANDARD("roomType.standard", 0), LABORATORY("roomType.laboratory", 0), COMPUTER_LAB("roomType.computer_lab", 15), AUDIMAX("roomType.audimax", 0);

	private String name;
	private int minimalBreakTime;

	private RoomType(String name, int minimalBreakTime) {
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