<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:form action="SaveCentury">
	<s:hidden name="century.id"/>
	<s:textfield name="century.name" key="label.century.name"/>
	<s:textfield name="century.numberOfStudents" key="label.century.numberOfStudents"/>
	<s:textfield name="century.breakTime" key="label.century.breakTime"/>
	<s:submit value="Save"/>
	<s:submit value="Cancel" action="ShowCenturyList"/>
</s:form>