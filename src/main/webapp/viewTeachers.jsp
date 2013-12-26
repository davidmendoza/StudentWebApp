<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.hibernateWeb.Domain.*" import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Teachers</title>
</head>
<body>
	<h3>Teachers in Database</h3>
	
	<table border='1'>
		<tr>
			<th>Id</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Gender</th>
			<th>Address</th>
			<th>Delete Teacher</th>
		</tr>
	<c:forEach var="teacher" items="${allTeachers}">
		<tr>
			<td>${teacher.id }</td>
			<td>${teacher.firstName }</td>
			<td>${teacher.lastName }</td>
			<td>${teacher.gender }
			<td>${teacher.address.city }</td>
			<td><a href="deleteTeacher?id=${teacher.id }">Delete</a></td>
		</tr>
	</c:forEach>
	</table>
	<a href="index.jsp">Back to Main Menu</a>
</body>
</html>