<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!-- author: mm -->
<s:actionerror />
<div id="content">
	<s:form acceptcharset="utf-8">
		<s:hidden name="lecturer.id" />
		<s:textfield name="lecturer.firstName" key="label.lecturer.firstName" />
		<s:textfield name="lecturer.lastName" key="label.lecturer.lastName" />
		<s:textfield name="lecturer.breakTime" key="label.lecturer.breakTime" />
		<s:textfield name="lecturer.emailAddress"
			key="label.lecturer.emailAddress" />
		<s:submit action="SaveLecturer" key="button.save.name"/>
		<s:submit action="ShowLecturerList" key="button.cancel.name"/>
	</s:form>
</div>
