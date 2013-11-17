package de.nordakademie.timetableservice.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;

import de.nordakademie.timetableservice.model.EventParticipant;

public abstract class EventParticipantDAO {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<? extends EventParticipant> findEntitiesWithDatesWithoutId(Date startDate, Date endDate, Long eventId) {
		List<? extends EventParticipant> entities = new LinkedList();
		getEntitiesWithStartDateBetweenExistingEvent(startDate, eventId, entities);
		getEntitiesWithEndDateBetweenExistingEvent(endDate, eventId, entities);
		getEntitiesWithDatesAroundExistingEvent(startDate, endDate, eventId, entities);
		return entities;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void getEntitiesWithDatesAroundExistingEvent(Date startDate, Date endDate, Long eventId, List<? extends EventParticipant> entities) {
		Session session = getSession();
		if (eventId == null) {
			List entityList = session
					.createQuery(
							"select entity from " + getTableName() + " entity join fetch entity.events event where :startDate <= event.startDate and :endDate >= event.endDate")
					.setTimestamp("endDate", endDate).setTimestamp("startDate", startDate).list();
			entities.addAll(entityList);
		} else {
			List entityList = session
					.createQuery(
							"select entity from " + getTableName()
									+ " entity join fetch entity.events event where event.id != :eventId and :startDate <= event.startDate and :endDate >= event.endDate")
					.setTimestamp("endDate", endDate).setTimestamp("startDate", startDate).setLong("eventId", eventId).list();
			entities.addAll(entityList);

		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void getEntitiesWithEndDateBetweenExistingEvent(Date endDate, Long eventId, List<? extends EventParticipant> entities) {
		Session session = getSession();
		if (eventId == null) {
			List entityList = session
					.createQuery("select entity from  " + getTableName() + "  entity join fetch entity.events event where :endDate between event.startDate and event.endDate")
					.setTimestamp("endDate", endDate).list();
			entities.addAll(entityList);
		} else {
			List entityList = session
					.createQuery(
							"select entity from  " + getTableName()
									+ "  entity join fetch entity.events event where event.id != :eventId and :endDate between event.startDate and event.endDate")
					.setTimestamp("endDate", endDate).setLong("eventId", eventId).list();
			entities.addAll(entityList);

		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void getEntitiesWithStartDateBetweenExistingEvent(Date startDate, Long eventId, List<? extends EventParticipant> entities) {
		Session session = getSession();
		if (eventId == null) {
			List entityList = session
					.createQuery("select entity from " + getTableName() + " entity join fetch entity.events event where :startDate between event.startDate and event.endDate")
					.setTimestamp("startDate", startDate).list();
			entities.addAll(entityList);
		} else {
			List entityList = session
					.createQuery(
							"select entity from " + getTableName()
									+ " entity join fetch entity.events event where event.id != :eventId and :startDate between event.startDate and event.endDate")
					.setTimestamp("startDate", startDate).setLong("eventId", eventId).list();
			entities.addAll(entityList);
		}
	}

	protected abstract Session getSession();

	protected abstract String getTableName();

}