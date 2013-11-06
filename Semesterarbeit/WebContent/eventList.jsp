<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:form>
<s:submit key="label.button.registerEvent" action="ShowEvent"/>
	<table>
		<tr>
			<th> <s:text name="label.event.name"/> </th>
			<th> <s:text name="label.event.startDate"/> </th>
			<th> <s:text name="label.event.endDate"/> </th>
			<th> <s:text name="label.event.rooms"/> </th>
			<th> <s:text name="label.event.lecturers"/> </th>
			<th> <s:text name="label.event.centuries"/> </th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		</tr>
		<s:iterator value="events" var="event">
			<tr>
				<td><s:property value="#event.name"/></td>
				<td><s:property value="startDate"/></td>
				<td><s:property value="endDate"/></td>
				<td>				
				
				<s:iterator value="#event.lecturers" var="lecturer">
				  	<s:property value="#lecturer.name"/>
				</s:iterator>
				
				</td>
				<s:url id="detailURL" action="ShowEvent">
					<s:param name="eventId" value="id"/>
				</s:url>
				<td><s:a href="%{detailURL}"><s:text name="label.list.detail"/></s:a></td>
				
				<s:url id="deleteURL" action="DeleteEvent">
					<s:param name="eventId" value="id"/>
				</s:url>
				<td><s:a href="%{deleteURL}"><s:text name="label.list.delete"/></s:a></td>
			</tr>
		</s:iterator>
	</table>
</s:form>