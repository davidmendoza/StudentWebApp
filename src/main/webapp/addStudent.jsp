<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.hibernateWeb.Domain.*" import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${titles.title}</title> 
</head>
<body>
	<h3>${titles.title}</h3>
	<form action="${titles.url }" method="post">
	<input type="hidden" name="id" value=${student.id }></input>
	First Name: <input type="text" name="firstName" required value=${student.firstName } ></input>  <br/>
	Last Name: <input type="text" name="lastName" required value=${student.lastName } ></input><br/>
	<input type="radio" name="gender" value="Male" required ${student.gender == 'Male' ? 'checked' : '' }>Male
	<input type="radio" name="gender" value="Female" required ${student.gender == 'Female' ? 'checked' : '' }>Female <br/>
	Year Level: <select name="level" required>
		<option disabled selected>Choose Year Level</option>
		<option value="1" ${student.level == '1' ? 'selected' : '' }>1st Year</option>
		<option value="2" ${student.level == '2' ? 'selected' : '' }>2nd Year</option>
		<option value="3" ${student.level == '3' ? 'selected' : '' }>3rd Year</option>
		<option value="4" ${student.level == '4' ? 'selected' : '' }>4th Year</option>
	</select> <br/>
	City: <select name="city" required">
		<option selected value="0">Choose a City</option>
		<c:if test="${student.address.id > 0}">
			<option ${student.address.id > '0' ? 'selected' : '' } value=${student.address.id }> ${student.address.city } </option>
		</c:if> 
	<c:forEach var="city" items="${cities}">
		<option value="${city.id }">${city.city }</option>
	</c:forEach>
	</select> <br/>
	Your city is not in the list? Enter yours below:<br/> <input type="text" name="newCity"/> <br/>
	<input type="submit" value="${titles.submit}"/>
	</form>
	<a href="index.jsp"/>Back to Main Menu</a>
</body>


</html>