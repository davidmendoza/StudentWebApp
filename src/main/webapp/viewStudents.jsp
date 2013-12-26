<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.hibernateWeb.Domain.*" import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Students</title>
</head>
<body>
	<h3>Students in Database</h3>
	
	<table border='1'>
		<tr>
			<th>Id</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Gender</th>
			<th>Level</th>
			<th>Address</th>
			<th>Delete Student</th>
			<th>Update Details</th>
		</tr>
	<c:forEach var="student" items="${allStudents}">
		<tr>
			<td>${student.id }</td>
			<td>${student.firstName }</td>
			<td>${student.lastName }</td>
			<td>${student.gender }
			<td>${student.level }</td>
			<td>${student.address.city }</td>
			<td><a href="deleteStudent?id=${student.id }">Delete</a></td>
			<td><a href="studentPage?mode=update&id=${student.id }">Update</a>
		</tr>
	</c:forEach>
	</table>
	<a href="index.jsp">Back to Main Menu</a>
	<%--List<Student> result = (List)request.getAttribute("allStudents"); --%>
	<%--for(Student st: result){
		out.println("<tr>");
		out.println("<td>"+st.getId()+"</td>");
		out.println("<td>"+st.getFirstName()+"</td>");
		out.println("<td>"+st.getLastName()+"</td>");
		out.println("<td>"+st.getGender()+"</td>");
		out.println("<td>"+st.getLevel()+"</td>");
		out.println("<td>"+st.getAddress().getCity()+"</td>");
		out.println("<td>"+"<a href=\"deleteStudent?id="+st.getId()+"\">Delete</a></td>");
		out.println("<td>"+"<a href=\"studentPage?mode=update&id="+st.getId()+"\">Update</a></td>");
		out.println("</tr>");
	} --%>
</body>
</html>