<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:form action="CreateRoom">
	<s:submit key="button.register.room.name"/>
	<table>
		<tr>
			<th> <s:text name="label.room.name"/> </th>
			<th> <s:text name="label.room.numberOfSeats"/> </th>
			<th> <s:text name="label.room.breakTime"/> </th>
			<th> <s:text name="label.room.roomType"/> </th>
			<th>&nbsp;</th>
		</tr>
		<s:iterator value="rooms">
			<tr>
				<td><s:property value="name"/></td>
				<td><s:property value="numberOfSeats"/></td>
				<td><s:property value="breakTime"/></td>
				<td><s:property value="%{getText(roomType)}"/></td>
				<s:url id="timetableURL" action="ShowTimetable">
					<s:param name="entityId" value="id"/>
					<s:param name="entity" value="'room'"/>
				</s:url>
				<td><s:a id="timetable2" href="%{timetableURL}"><s:text name="label.list.timetable"/></s:a></td>
				
			</tr>
		</s:iterator>
	</table>
</s:form>