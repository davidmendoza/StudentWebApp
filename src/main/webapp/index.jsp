<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Students</title>
</head>
<body>
	<h2>Student Profile Application</h2>
	<a href="allStudents">View Students</a>
	<br/>	
	<a href="addStudentPageController">Add New Student</a>
	<% String message = (String)request.getAttribute("message");
	if( message != null){%>
	<br/>
		<%=message %>
	<% }%>
	
	
	
</body>
</html>