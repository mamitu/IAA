<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:form action="ShowCentury">
	<table>
		<tr>
			<th> <s:text name="label.century.name"/> </th>
			<th> <s:text name="label.century.numberOfStudents"/> </th>
			<th> <s:text name="label.century.breakTime"/> </th>
			<th>&nbsp;</th>
		</tr>
		<s:iterator value="centuries">
			<tr>
				<td><s:property value="name"/></td>
				<td><s:property value="numberOfStudents"/></td>
				<td><s:property value="breakTime"/></td>
				<s:url id="detailURL" action="ShowCentury">
					<s:param name="centuryId" value="id"/>
				</s:url>
				<td><s:a href="%{detailURL}"><s:text name="label.list.detail"/></s:a></td>
			</tr>
		</s:iterator>
	</table>
	<s:submit value="Register"/>
</s:form>