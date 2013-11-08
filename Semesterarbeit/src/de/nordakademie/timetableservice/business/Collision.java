package de.nordakademie.timetableservice.business;

public class Collision {

	private String message;

	private CollisionType collisionType;

	private String entity;

	public Collision(CollisionType collisiontype, String entity, String message) {
		this.collisionType = collisiontype;
		this.entity = entity;
		this.message = message;
	}

	@Override
	public String toString() {
		return collisionType + ": " + message;
	}

	public String getMessage() {
		return message;
	}

	public CollisionType getCollisionType() {
		return collisionType;
	}

	public String getEntity() {
		return entity;
	}

}