<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 

<s:actionerror/>
<s:form acceptcharset="utf-8">
	<s:hidden name="eventId"/>
	<s:textfield name="name" key="label.event.name" />
	<s:radio name="eventType" list="%{@de.nordakademie.timetableservice.model.EventType@values()}" listValue="%{getText(name)}" key="label.event.eventType" />
	
	<s:textfield name="startDate" key="label.event.startDate" value="%{getText('format.date',{startDate})}"/>
	<s:textfield name="endDate" key="label.event.endDate" value="%{getText('format.date',{endDate})}"/>
	
	<s:submit key="button.searchFreeRooms.name" tooltip="button.searchFreeRooms.tooltip" action="SearchFreeRooms"/>	
	
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
		name="selectedLecturerIds"
		multiple="true"
		size="5"
		listKey="key"
		listValue="value"
		list="availableLecturers"
		key="label.event.lecturers"
		
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
	
	<s:textfield name="changeTime" key="label.event.changeTime"/>
	<s:textfield name="numberOfWeeklyRepetitions" key="label.numberOfWeeklyRepetitions"/>
	
	<s:submit value="Save" action="SaveNewEvent"/>
	<s:submit value="Cancel" action="ShowEventList"/>
</s:form>