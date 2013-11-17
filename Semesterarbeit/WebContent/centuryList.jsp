<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form action="CreateCentury">
	<s:submit key="button.register.century.name" />
	<table>
		<tr>
			<th><s:text name="label.century.name" /></th>
			<th><s:text name="label.century.numberOfStudents" /></th>
			<th><s:text name="label.century.breakTime" /></th>
			<th>&nbsp;</th>
		</tr>
		<s:iterator value="centuries">
			<tr>
				<td><s:property value="name" /></td>
				<td><s:property value="numberOfStudents" /></td>
				<td><s:property value="breakTime" /></td>
				<s:url id="timetableURL" action="ShowTimetable">
					<s:param name="entityId" value="id" />
					<s:param name="entity" value="'century'" />
				</s:url>
				<td><s:a href="%{timetableURL}">
						<s:text name="label.list.timetable" />
					</s:a></td>
			</tr>
		</s:iterator>
	</table>
</s:form>