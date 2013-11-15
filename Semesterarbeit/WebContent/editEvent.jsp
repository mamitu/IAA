<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 

<div id="content">
<s:actionerror/>
<s:form  acceptcharset="utf-8">
	<s:hidden name="eventId"/>
	<s:textfield name="event.name" key="label.event.name" readonly="true" />
	<s:textfield name="eventType" value="%{getText(event.eventType)}" key="label.event.eventType" readonly="true" />
	
	<s:textfield name="startDate" key="label.event.startDate" value="%{getText('format.date',{event.startDate})}"/>
	<s:textfield name="endDate" key="label.event.endDate" value="%{getText('format.date',{event.endDate})}"/>

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
	
	<s:radio name="isCenturySelected" key="label.choice" list="#@java.util.LinkedHashMap@{true:'Pro Zenturie',false:'Pro Kohorte'}"/>

	<s:select
		name="selectedCenturyIds"
		multiple="true"
		size="5"
		listKey="key"
		listValue="value"
		list="availableCenturies"
		key="label.event.centuries"
	/>

	<s:select
		name="selectedCohortIds"
		size="5"
		listKey="key"
		listValue="value"
		list="availableCohorts"
		key="label.cohort.name"
	/>
	<s:textfield name="changeTime" value="%{event.changeTime}" key="label.event.changeTime"/>
	
	<s:submit value="Save" action="SaveExistingEvent"/>
	<s:submit value="Cancel" action="ShowEventList"/>
</s:form>
</div>
