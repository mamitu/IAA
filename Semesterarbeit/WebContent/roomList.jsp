<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:form action="ShowRoom">
	<table>
		<tr>
			<th> <s:text name="label.room.name"/> </th>
			<th> <s:text name="label.room.numberOfSeats"/> </th>
			<th> <s:text name="label.room.breakTime"/> </th>
			<th>&nbsp;</th>
		</tr>
		<s:iterator value="rooms">
			<tr>
				<td><s:property value="name"/></td>
				<td><s:property value="numberOfSeats"/></td>
				<td><s:property value="breakTime"/></td>
				<s:url id="detailURL" action="ShowRoom">
					<s:param name="roomId" value="id"/>
				</s:url>
				<td><s:a href="%{detailURL}"><s:text name="label.detail"/></s:a></td>
			</tr>
		</s:iterator>
	</table>
	<s:submit value="Register"/>
</s:form>