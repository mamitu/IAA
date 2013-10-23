<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:form>
<s:submit key="label.button.registerExam" action="ShowExam"/>
<s:submit key="label.button.registerLecture" action="ShowLecture"/>
<s:submit key="label.button.registerSeminar" action="ShowSeminar"/>
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
		<s:iterator value="events">
			<tr>
				<td><s:property value="name"/></td>
				<td><s:property value="startDate"/></td>
				<td><s:property value="endDate"/></td>
				<s:iterator value="events.rooms">
				  <tr><s:property value="name"/></tr>
				</s:iterator>
				<s:iterator value="events.lecturers">
				  <tr><s:property value="name"/></tr>
				</s:iterator>
				<s:iterator value="events.centuries">
				  <tr><s:property value="name"/></tr>
				</s:iterator>
				<s:url id="detailURL" action="ShowEvent">
					<s:param name="eventId" value="id"/>
				</s:url>
				<td><s:a href="%{detailURL}"><s:text name="label.list.detail"/></s:a></td>
				<td><s:a action="DeleteAction"><s:text name="label.list.delete"/></s:a></td>
			</tr>
		</s:iterator>
	</table>
</s:form>