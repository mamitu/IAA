<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>


<div id="content">
<s:form action="SaveCohort">
<s:actionerror/>
	<s:hidden name="cohort.id"/>
	<s:radio name="fieldOfStudy" list="%{@de.nordakademie.timetableservice.model.FieldOfStudy@values()}" key="label.fieldOfStudy" />
	<s:textfield name="cohort.year" key="label.cohort.year" maxlength="2"/>
	<s:submit value="Save"/>
	<s:submit value="Cancel" action="ShowCohortList"/>
</s:form>
</div>
