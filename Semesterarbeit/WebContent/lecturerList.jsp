<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:form action="ShowLecturer">
	<table>
		<tr>
			<th> <s:text name="label.lecturer.firstName"/> </th>
			<th> <s:text name="label.lecturer.lastName"/> </th>
			<th> <s:text name="label.lecturer.breakTime"/> </th>
			<th>&nbsp;</th>
		</tr>
		<s:iterator value="lecturers">
			<tr>
				<td><s:property value="firstName"/></td>
				<td><s:property value="lastName"/></td>
				<td><s:property value="breakTime"/></td>
				<s:url id="detailURL" action="ShowLecturer">
					<s:param name="lecturerId" value="id"/>
				</s:url>
				<td><s:a href="%{detailURL}"><s:text name="label.detail"/></s:a></td>
			</tr>
		</s:iterator>
	</table>
	<s:submit value="Register"/>
</s:form>