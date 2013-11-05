<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 

<s:form  acceptcharset="utf-8">
	<s:hidden name="event.id"/>
	<s:textfield name="event.name" key="label.event.name"/>
	<s:radio name="event.eventType" list="%{@de.nordakademie.timetableservice.model.EventType@values()}" key="label.event.eventType" />
	<s:textfield name="event.startDate" key="label.event.startDate"/>
	<s:textfield name="event.endDate" key="label.event.endDate"/>
	
	<s:select
		name="selectedLecturerIds"
		multiple="true"
		size="5"
		listKey="key"
		listValue="value"
		list="availableLecturers"
		key="label.event.lecturers"
	/>
	
	<s:select
		name="selectedRoomIds"
		multiple="true"
		size="5"
		listKey="key"
		listValue="value"
		list="availableRooms"
		key="label.event.rooms"
	/>
	<s:select
		name="selectedCenturyIds"
		multiple="true"
		size="5"
		listKey="key"
		listValue="value"
		list="availableCenturies"
		key="label.event.centuries"
	/>
	<s:submit value="Save" action="SaveEvent"/>
	<s:submit value="Cancel" action="ShowEventList"/>
</s:form>