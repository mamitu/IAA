<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- author: mm, rs -->
<s:text name="label.title.confirmSave" />
<s:form>
	<s:submit action="ConfirmSaveExistingEvent" key="button.save.name" />
	<s:submit action="EditExistingEvent" key="button.cancel.name"/>
	<table>
		<s:iterator value="collisions">
			<tr>
				<td><s:property /></td>
			</tr>
		</s:iterator>
	</table>

	<s:hidden name="eventId" />
	<s:hidden name="name" />
	<s:hidden name="startDate"
		value="%{getText('format.date',{startDate})}" />
	<s:hidden name="endDate" value="%{getText('format.date',{endDate})}" />
	<s:iterator var="row" value="selectedCenturyIds" status="status">
		<s:hidden name="selectedCenturyIds[%{#status.index}]" value="%{#row}" />
	</s:iterator>

	<s:iterator var="row" value="selectedLecturerIds" status="status">
		<s:hidden name="selectedLecturerIds[%{#status.index}]" value="%{#row}" />
	</s:iterator>

	<s:iterator var="row" value="selectedRoomIds" status="status">
		<s:hidden name="selectedRoomIds[%{#status.index}]" value="%{#row}" />
	</s:iterator>
	<s:iterator var="row" value="selectedCohortIds" status="status">
		<s:hidden name="selectedCohortIds[%{#status.index}]" value="%{#row}" />
	</s:iterator>
	<s:hidden name="isCenturySelected" />
	<s:hidden name="breakTime" />

</s:form>