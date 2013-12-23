<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.hibernateWeb.Domain.*" import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>Add New Student</h3>
	<form action="addStudent" method="post">
	First Name: <input type="text" name="firstName" required/><br/>
	Last Name: <input type="text" name="lastName" required/><br/>
	<input type="radio" name="gender" value="Male" required>Male
	<input type="radio" name="gender" value="Female" required>Female <br/>
	Year Level: <select name="level" required>
		<option value="1">1st Year</option>
		<option value="2">2nd Year</option>
		<option value="3">3rd Year</option>
		<option value="4">4th Year</option>
	</select> <br/>
	<%List<Address> cities = (List)request.getAttribute("cities");%> 
	City: <select name="city">
	
		<%for(Address add: cities){ %>
			<option value="<%=add.getId()%>"><%=add.getCity()%> </option>
		<%}%>
	</select> <br/>
	<input type="submit" value="Add Student"/>
	</form>
	<a href="index.jsp"/>Back to Main Menu</a>
</body>
</html>