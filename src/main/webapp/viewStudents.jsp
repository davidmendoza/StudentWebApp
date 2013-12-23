<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.hibernateWeb.Domain.*" import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Students</title>
</head>
<body>
	<h3>Students in Database</h3>
	<%List<Student> result = (List)request.getAttribute("allStudents"); %>
	<table border='1'>
		<tr>
			<th>Id</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Gender</th>
			<th>Level</th>
			<th>Address</th>
			<th>Math</th>
			<th>Science</th>
			<th>English</th>
		</tr>
	<%for(Student st: result){
		out.println("<tr>");
		out.println("<td>"+st.getId()+"</td>");
		out.println("<td>"+st.getFirstName()+"</td>");
		out.println("<td>"+st.getLastName()+"</td>");
		out.println("<td>"+st.getGender()+"</td>");
		out.println("<td>"+st.getLevel()+"</td>");
		out.println("<td>"+st.getAddress().getCity()+"</td>");
		//out.println("<td>"+st.getGrade().getMath()+"</td>");
		//out.println("<td>"+st.getGrade().getScience()+"</td>");
		//out.println("<td>"+st.getGrade().getEnglish()+"</td>");
		out.println("</tr>");
	} %>
	</table>
	<a href="index.jsp">Back to Main Menu</a>
</body>
</html>