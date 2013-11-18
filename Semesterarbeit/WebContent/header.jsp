<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="header">
	<s:text name="label.title"></s:text>
</div>

<div id="navigation">
	<ul>
		<li><s:a action="Overview">
				<s:text name="label.menu.overview" />
			</s:a></li>
		<li><s:a action="ShowLecturerList">
				<s:text name="label.menu.editLecturers" />
			</s:a></li>
		<li><s:a action="ShowCohortList">
				<s:text name="label.menu.editCohorts" />
			</s:a></li>
		<li><s:a action="ShowCenturyList">
				<s:text name="label.menu.editCenturies" />
			</s:a></li>
		<li><s:a action="ShowRoomList">
				<s:text name="label.menu.editRooms" />
			</s:a></li>
		<li><s:a action="ShowEventList">
				<s:text name="label.menu.editEvents" />
			</s:a></li>
	</ul>
</div>

<div id="shadow"></div>
