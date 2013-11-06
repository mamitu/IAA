package de.nordakademie.timetableservice.business;

public class Collision {

	private String message;

	private CollisionType collisionType;

	public Collision(CollisionType collisiontype, String message) {
		this.collisionType = collisiontype;
		this.message = message;
	}

	@Override
	public String toString() {
		return collisionType + ": " + message;
	}

}
