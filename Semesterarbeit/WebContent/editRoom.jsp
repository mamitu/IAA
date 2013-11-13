<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:form action="SaveRoom">
<s:fielderror/>
	<s:hidden name="room.id"/>
	<s:textfield name="room.name" key="label.room.name"/>
	<s:textfield name="room.numberOfSeats" key="label.room.numberOfSeats"/>
	<s:textfield name="room.breakTime" key="label.room.breakTime"/>
	<s:radio name="room.roomType" list="%{@de.nordakademie.timetableservice.model.RoomType@values()}" key="label.room.roomType" />
	<s:submit value="Save"/>
	<s:submit value="Cancel" action="ShowRoomList"/>
</s:form>