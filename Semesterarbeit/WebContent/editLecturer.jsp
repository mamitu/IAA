<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:form action="SaveLecturer">
	<s:fielderror/>
	<s:hidden name="lecturer.id"/>
	<s:textfield name="lecturer.firstName" key="label.lecturer.firstName"/>
	<s:textfield name="lecturer.lastName" key="label.lecturer.lastName"/>
	<s:textfield name="lecturer.breakTime" key="label.lecturer.breakTime"/>
	<s:submit value="Save"/>
	<s:submit value="Cancel" action="ShowLecturerList"/>
</s:form>