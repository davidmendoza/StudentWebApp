<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.hibernateWeb.Domain.*" import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add/Edit Teacher</title> 
</head>
<body>
	<h3>${titles.title}</h3>
	<form action="${titles.url }" method="post">
	<input type="hidden" name="id" value=${teacher.id }></input>
	First Name: <input type="text" name="firstName" required value=${teacher.firstName } ></input>  <br/>
	Last Name: <input type="text" name="lastName" required value=${teacher.lastName } ></input><br/>
	<input type="radio" name="gender" value="Male" required ${teacher.gender == 'Male' ? 'checked' : '' }>Male
	<input type="radio" name="gender" value="Female" required ${teacher.gender == 'Female' ? 'checked' : '' }>Female <br/>
	City: <select name="city" required">
		<option selected value="0">Choose a City</option>
		<c:if test="${teacher.address.id > 0}">
			<option ${teacher.address.id > '0' ? 'selected' : '' } value=${teacher.address.id }> ${teacher.address.city } </option>
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