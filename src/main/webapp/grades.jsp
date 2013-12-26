<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add/Edit Grades</title>
</head>
<body>
<h3>Add/Edit Grades</h3>
<form action="grades">
	<p>Grades of ${student.lastName }, ${student.firstName }</p>
	<input type="hidden" name="id" value=${student.id }></input>
	Math: <input type="text" maxlength="2" size="5" name="math" value="${student.grade.math }"/>
	English: <input type="text" maxlength="2" size="5" name="english" value="${student.grade.english }"/>
	Science: <input type="text" maxlength="2" size="5" name="science" value="${student.grade.science }"/>
	<br/><input type="submit" value="Enter grade"/>
</form>
</body>
</html>