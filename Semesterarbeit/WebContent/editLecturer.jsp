<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<div id="content">
<s:form action="SaveLecturer" acceptcharset="utf-8">
	<s:fielderror/>
	<s:hidden name="lecturer.id"/>
	<s:textfield name="lecturer.firstName" key="label.lecturer.firstName"/>
	<s:textfield name="lecturer.lastName" key="label.lecturer.lastName"/>
	<s:textfield name="lecturer.breakTime" key="label.lecturer.breakTime"/>
	<s:textfield name="lecturer.emailAddress" key="label.lecturer.emailAddress"/>
	<s:submit value="Save"/>
	<s:submit value="Cancel" action="ShowLecturerList"/>
</s:form>
</div>
