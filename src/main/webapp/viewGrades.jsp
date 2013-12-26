<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.hibernateWeb.Domain.*" import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Grades</title>
</head>
<body>
	<h3>Student Grades</h3>
	
	<table border='1'>
		<tr>
			<th>Id</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Level</th>
			<th>Math </th>
			<th>English</th>
			<th>Science</th>
			<th>Edit Grades</th>
		</tr>
		
	<c:forEach var="student" items="${allStudents}">
		<form action="gradesPage?id=${student.id }">
		<tr>
			<td>${student.id }<input type="hidden" value="${student.id }" name="id"/></td>
			<td>${student.firstName }</td>
			<td>${student.lastName }</td>
			<td>${student.level }</td>
			<td><input type="text" value="${student.grade.math }" size="5" maxlength="2" name="math" /></td>
			<td><input type="text" value="${student.grade.english }" size="5" maxlength="2" name="english" /></td>
			<td><input type="text" value="${student.grade.science }" size="5" maxlength="2" name="science" /></td>
			<td><input type="submit" value="submit"/></td>
		</tr>
		</form>
	</c:forEach>
		
	</table>
	<a href="index.jsp">Back to Main Menu</a>
</body>
</html>