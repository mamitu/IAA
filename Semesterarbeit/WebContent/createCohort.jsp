<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!-- author: mm, rs -->
<s:actionerror />
<div id="content">
	<s:form>
		<s:hidden name="cohort.id" />
		<s:radio name="fieldOfStudy"
			list="%{@de.nordakademie.timetableservice.model.FieldOfStudy@values()}"
			key="label.cohort.fieldOfStudy" />
		<s:textfield name="cohort.year" key="label.cohort.year" maxlength="2" />
		<s:submit action="SaveCohort" key="button.save.name"/>
		<s:submit action="ShowCohortList" key="button.cancel.name"/>
	</s:form>
</div>
