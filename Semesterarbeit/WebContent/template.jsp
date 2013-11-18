<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link rel="stylesheet" type="style/css" href="Style/style.css">
<link rel="icon" href="Style/favicon32.jpg" type="image/jpg" />
<link rel="shortcut icon" href="Style/favicon32.jpg" type="image/jpg" />
<title><s:text name="label.title" /></title>
</head>
<body>
	<!-- Header -->
	<tiles:insertAttribute name="header" />
	<!-- Content -->
	<tiles:insertAttribute name="content" />
	<!-- Footer -->
	<tiles:insertAttribute name="footer" />
</body>
</html>