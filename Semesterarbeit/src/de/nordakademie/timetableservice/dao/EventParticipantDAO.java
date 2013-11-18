package de.nordakademie.timetableservice.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;

import de.nordakademie.timetableservice.model.EventParticipant;

/**
 * Abstraktes Data Access Object, dass Methoden fuer die konkreten Unterklassen
 * der Teilnehmer einer Veranstaltung bereitstellt.
 * 
 * @author
 * 
 */
public abstract class EventParticipantDAO {

	/**
	 * Ermittelt alle Entitaeten, die zu den uebergebenen Startdatum und
	 * Enddatum schon eine Veranstaltung haben. Falls die eventId nicht null
	 * ist, werden alle anderen Veranstaltungen ausser die uebergebene
	 * betrachtet.
	 * 
	 * @param startDate
	 *            Startdatum
	 * @param endDate
	 *            Enddatum
	 * @param eventId
	 *            ID der Veranstaltung, die nicht betrachtet werden soll
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<? extends EventParticipant> findEntitiesWithDatesWithoutId(Date startDate, Date endDate, Long eventId) {
		List<? extends EventParticipant> entities = new LinkedList();
		getEntitiesWithStartDateBetweenExistingEvent(startDate, eventId, entities);
		getEntitiesWithEndDateBetweenExistingEvent(endDate, eventId, entities);
		getEntitiesWithDatesAroundExistingEvent(startDate, endDate, eventId, entities);
		return entities;
	}

	/**
	 * Ermittelt die Entitaeten, die Veranstaltungen haben, die vor dem
	 * uebergebenen Startdatum beginnen und nach dem uebergebeben Enddatum
	 * aufhoeren.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getEntitiesWithDatesAroundExistingEvent(Date startDate, Date endDate, Long eventId,
			List<? extends EventParticipant> entities) {
		Session session = getSession();
		if (eventId == null) {
			List entityList = session
					.createQuery(
							"select entity from "
									+ getTableName()
									+ " entity join fetch entity.events event where :startDate <= event.startDate and :endDate >= event.endDate")
					.setTimestamp("endDate", endDate).setTimestamp("startDate", startDate).list();
			entities.addAll(entityList);
		} else {
			List entityList = session
					.createQuery(
							"select entity from "
									+ getTableName()
									+ " entity join fetch entity.events event where event.id != :eventId and :startDate <= event.startDate and :endDate >= event.endDate")
					.setTimestamp("endDate", endDate).setTimestamp("startDate", startDate).setLong("eventId", eventId)
					.list();
			entities.addAll(entityList);

		}
	}

	/**
	 * Ermittelt die Entitaeten, wo das uebergebene Enddatum in einer
	 * existierenden Veranstaltung liegt.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getEntitiesWithEndDateBetweenExistingEvent(Date endDate, Long eventId,
			List<? extends EventParticipant> entities) {
		Session session = getSession();
		if (eventId == null) {
			List entityList = session
					.createQuery(
							"select entity from  "
									+ getTableName()
									+ "  entity join fetch entity.events event where :endDate between event.startDate and event.endDate")
					.setTimestamp("endDate", endDate).list();
			entities.addAll(entityList);
		} else {
			List entityList = session
					.createQuery(
							"select entity from  "
									+ getTableName()
									+ "  entity join fetch entity.events event where event.id != :eventId and :endDate between event.startDate and event.endDate")
					.setTimestamp("endDate", endDate).setLong("eventId", eventId).list();
			entities.addAll(entityList);

		}
	}

	/**
	 * Ermittelt die Entitaeten, wo das uebergebene Startdatum in einer
	 * existierenden Veranstaltung liegt.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getEntitiesWithStartDateBetweenExistingEvent(Date startDate, Long eventId,
			List<? extends EventParticipant> entities) {
		Session session = getSession();
		if (eventId == null) {
			List entityList = session
					.createQuery(
							"select entity from "
									+ getTableName()
									+ " entity join fetch entity.events event where :startDate between event.startDate and event.endDate")
					.setTimestamp("startDate", startDate).list();
			entities.addAll(entityList);
		} else {
			List entityList = session
					.createQuery(
							"select entity from "
									+ getTableName()
									+ " entity join fetch entity.events event where event.id != :eventId and :startDate between event.startDate and event.endDate")
					.setTimestamp("startDate", startDate).setLong("eventId", eventId).list();
			entities.addAll(entityList);
		}
	}

	/**
	 * Gibt die Session zurueck
	 * 
	 * @return
	 */
	protected abstract Session getSession();

	/**
	 * Ermittelt, aus welcher Tabelle gelesen werden soll
	 * 
	 * @return
	 */
	protected abstract String getTableName();

}