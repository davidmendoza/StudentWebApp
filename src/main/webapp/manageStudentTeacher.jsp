<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.hibernateWeb.Domain.*" import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>Manage Students of ${teacher.lastName }, ${teacher.firstName }</h3>
	Select a student/s from the list:
	<form action="teachers">
		<input type="hidden" name="mode" value="addStudentTeacher"/>
		<table border="1">
			<tr>
				<th></th>
				<th>Last Name</th>
				<th>First Name</th>
				<th>Year Level</th>
			</tr>
		<c:forEach var="student" items="${notMyStudents}">
			<tr>
				<td><input type="checkbox" name="studentId" value="${student.id }"/></td>
				<td>${student.lastName }</td>
				<td>${student.firstName }</td>
				<td>${student.level }</td>
			<tr>
		</c:forEach>
		</table>
		<input type="hidden" name="teacherId" value="${teacher.id }"/>
		<input type="submit" value="Add students"/>

	<br/>
	Your currently enrolled students:
	<table border="1">
		<tr>
			<th>Last Name</th>
			<th>First Name</th>
			<th>Year Level</th>
			<th>Remove Student</th>
		</tr>
		<c:forEach var="student" items="${myStudents }">
		<tr>
			<td>${student.lastName }</td>
			<td>${student.firstName }</td>
			<td>${student.level }</td>
			<td><a href="teachers?mode=removeStudent&sId=${student.id }&tId=${teacher.id }">Remove</a></td>
		</tr>
		</c:forEach>
	</table>
	
	
	</form>
	<a href="index.jsp">Back to Main Menu</a>
	<p>
	
</body>
</html>