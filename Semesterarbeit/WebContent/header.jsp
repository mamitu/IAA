<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
</head>
<body>

<s:a action="Overview"><s:text name="label.menu.overview"/></s:a>
<s:a action="ShowLecturerList"><s:text name="label.menu.editLecturers"/></s:a>
<s:a action="ShowCenturyList"><s:text name="label.menu.editCenturies"/></s:a>
<s:a action="ShowRoomList"><s:text name="label.menu.editRooms"/></s:a>
<s:a action="ShowEventList"><s:text name="label.menu.editEvents"/></s:a>
	</body>
</html>