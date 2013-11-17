<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form action="CreateLecturer">
	<s:submit key="button.register.lecturer.name" />

	<table>
		<tr>
			<th><s:text name="label.lecturer.firstName" /></th>
			<th><s:text name="label.lecturer.lastName" /></th>
			<th><s:text name="label.lecturer.breakTime" /></th>
			<th><s:text name="label.lecturer.emailAddress" /></th>
			<th>&nbsp;</th>
		</tr>
		<s:iterator value="lecturers">
			<tr>
				<td><s:property value="firstName" /></td>
				<td><s:property value="lastName" /></td>
				<td><s:property value="breakTime" /></td>
				<td><s:property value="emailAddress" /></td>
				<s:url id="timetableURL" action="ShowTimetable">
					<s:param name="entityId" value="id" />
					<s:param name="entity" value="'lecturer'" />
				</s:url>
				<td><s:a href="%{timetableURL}">
						<s:text name="label.list.timetable" />
					</s:a></td>
			</tr>
		</s:iterator>
	</table>
</s:form>