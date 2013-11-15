<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>


<div id="content">
<s:form action="SaveCentury">
<s:actionerror/>
<s:fielderror/>
	<s:hidden name="century.id"/>
	
	<s:select
		name="selectedCohortIds"
		size="5"
		listKey="key"
		listValue="value"
		list="availableCohorts"
		key="label.century.cohort"
	/>
	
	<s:textfield name="suffix" key="label.century.suffix" maxlength="1"/>
	<s:textfield name="century.numberOfStudents" key="label.century.numberOfStudents"/>
	<s:textfield name="century.breakTime" key="label.century.breakTime"/>
	<s:submit value="Save"/>
	<s:submit value="Cancel" action="ShowCenturyList"/>
</s:form>
</div>
