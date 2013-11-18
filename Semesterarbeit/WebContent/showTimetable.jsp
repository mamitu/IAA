<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!-- author: mm -->
<table>
	<tr>
		<th><s:text name="label.event.name" /></th>
		<th><s:text name="label.event.startDate" /></th>
		<th><s:text name="label.event.endDate" /></th>
	</tr>
	<s:iterator value="events" var="event">
		<tr>
			<td><s:property value="#event.name" /></td>
			<td><s:property
					value="%{getText('format.date',{#event.startDate})}" /></td>
			<td><s:property
					value="%{getText('format.date',{#event.endDate})}" /></td>
		</tr>
	</s:iterator>
</table>