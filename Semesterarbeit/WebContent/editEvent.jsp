<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<s:actionerror />
<div id="content">
	<s:form acceptcharset="utf-8">
		<s:hidden name="eventId" />
		<s:textfield name="name" key="label.event.name" readonly="true" />
		<s:textfield value="%{getText(eventType)}"
			key="label.event.eventType" readonly="true" />
			<s:hidden value="%{eventType}" name="eventType"/>
		<s:textfield name="startDate" key="label.event.startDate"
			value="%{getText('format.date',{startDate})}" />
		<s:textfield name="endDate" key="label.event.endDate"
			value="%{getText('format.date',{endDate})}" />

		<s:select name="selectedRoomIds" multiple="true" size="5"
			listKey="key" listValue="value" list="availableRooms"
			key="label.event.rooms" />

		<s:select name="selectedLecturerIds" multiple="true" size="5"
			listKey="key" listValue="value" list="availableLecturers"
			key="label.event.lecturers" />

		<s:radio name="isCenturySelected" value="true" key="label.choice"
			list="#@java.util.LinkedHashMap@{true:getText('label.choice.perCentury'),false:getText('label.choice.perCohort')}" />

		<s:select name="selectedCenturyIds" multiple="true" size="5"
			listKey="key" listValue="value" list="availableCenturies"
			key="label.event.centuries" />

		<s:select name="selectedCohortIds" size="5" listKey="key"
			listValue="value" list="availableCohorts" key="label.cohort.name" />
		<s:textfield name="breakTime" value="%{breakTime}"
			key="label.event.breakTime" />

		<s:submit action="TrySaveExistingEvent" key="button.save.name"/>
		<s:submit action="ShowEventList" key="button.cancel.name"/>
	</s:form>
</div>
