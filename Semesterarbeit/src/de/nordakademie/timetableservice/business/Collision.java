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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((collisionType == null) ? 0 : collisionType.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collision other = (Collision) obj;
		if (collisionType != other.collisionType)
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

}