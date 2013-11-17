<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form action="CreateCohort">
	<s:submit key="button.register.cohort.name" />
	<table>
		<tr>
			<th><s:text name="label.cohort.name" /></th>
			<th>&nbsp;</th>
		</tr>
		<s:iterator value="cohorts" var="cohort">
			<tr>
				<td><s:property value="#cohort.fieldOfStudy + #cohort.year" /></td>
				<s:url id="timetableURL" action="ShowTimetable">
					<s:param name="entityId" value="id" />
					<s:param name="entity" value="'cohort'" />
				</s:url>
				<td><s:a href="%{timetableURL}">
						<s:text name="label.list.timetable" />
					</s:a></td>
			</tr>
		</s:iterator>
	</table>
</s:form>