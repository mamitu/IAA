package de.nordakademie.timetableservice.business;

public enum CollisionType {

	ERROR("label.collision.error"), WARNING("label.collision.warning");

	private CollisionType(String name) {
		this.name = name;
	}

	private String name;

	@Override
	public String toString() {
		return name;
	}
}