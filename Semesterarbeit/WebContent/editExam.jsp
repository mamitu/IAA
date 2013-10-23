<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 

<s:form>
	<s:hidden name="exam.id"/>
	<s:textfield name="exam.name" key="label.event.name"/>
	<s:textfield name="exam.startDate" key="label.event.startDate"/>
	<s:textfield name="exam.endDate" key="label.event.endDate"/>
	<s:select name="exam.lecturers" multiple="true" size="5"
		list="exam.lecturers" label="label.event.lecturers"/>
	<s:submit value="Save" action="SaveExam"/>
	<s:submit value="Cancel" action="ShowCenturyList"/>
</s:form>