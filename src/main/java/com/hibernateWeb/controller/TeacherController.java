package com.hibernateWeb.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hibernateWeb.Domain.Address;
import com.hibernateWeb.Domain.Student;
import com.hibernateWeb.Domain.Teacher;
import com.hibernateWeb.beans.TeacherBean;
import com.hibernateWeb.dao.AddressDAO;
import com.hibernateWeb.dao.TeacherDAO;

public class TeacherController extends HttpServlet{
	
	private static final String NEW_TEACHER = "new";
	private static final String VIEW_TEACHERS = "view";
	private static final String DELETE_TEACHER = "delete";
	private static final String MANAGE_STUDENTS = "manageStudents";
	private static final String ADD_STUDENT_TO_TEACHER = "addStudentTeacher";
	private static final String REMOVE_STUDENT_FROM_TEACHER = "removeStudent";
	private static final String MANAGE_STUDENT_TEACHER_URL = "manageStudentTeacher.jsp";
	private static final String VIEW_TEACHERS_URL = "viewTeachers.jsp";
	private static final String ADD_TEACHER_URL = "addTeacher.jsp;";
	private static final String INDEX_URL = "index.jsp";
	private static AddressDAO addressDao = new AddressDAO();
	private static TeacherDAO teacherDao = new TeacherDAO();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TeacherBean tb = new TeacherBean();
		RequestDispatcher view;
		
		tb.setFirstName(request.getParameter("firstName"));
		tb.setLastName(request.getParameter("lastName"));
		tb.setGender(request.getParameter("gender"));
		String newCity = request.getParameter("newCity");
		Long addressId = Long.parseLong(request.getParameter("city"));
		
		if (addressId == 0 && newCity.trim() != "") {
			
			if (addressId == 0 || newCity.trim() != ""){
				tb.setAddress(addressDao.addAddress(newCity));
			} else {
				tb.setAddress(addressDao.getAddress(addressId));
			}
			
			teacherDao.addTeacher(tb);
			request.setAttribute("message", "Successfully added Teacher "+tb.getFirstName());
			
		} else {
			request.setAttribute("message", "You have not entered an Address. Please fill up the form again");
		}
		
		view = request.getRequestDispatcher("index.jsp");
		view.forward(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher view;
		String mode = request.getParameter("mode");
		String url = null;
		
		switch(mode){
		
		case NEW_TEACHER:
			List<Address> addCities = addressDao.getAddressList();
			request.setAttribute("cities", addCities);
			url = ADD_TEACHER_URL;
		break;
		
		case VIEW_TEACHERS:
			List<Teacher> teachers = teacherDao.getTeacherList();
			if (!teachers.isEmpty()){
				request.setAttribute("allTeachers", teachers);
				url = VIEW_TEACHERS_URL;
			} else {
				request.setAttribute("message", "No teachers registered in the database. Please add a new teacher");
				url = INDEX_URL;
			}
		break;
		
		case DELETE_TEACHER:
			Long deleteId = Long.parseLong(request.getParameter("id"));
			teacherDao.deleteTeacher(deleteId);
			url = "teachers?mode=view";
		break;
		
		case MANAGE_STUDENTS:
			Long id = Long.parseLong(request.getParameter("id"));
			Teacher teacher = teacherDao.getTeacher(id);	
			
			Map<String, List<Student>> studentMap = teacherDao.getStudentMap(id);
			
			if (!studentMap.get("allStudents").isEmpty()){
				request.setAttribute("myStudents", studentMap.get("myStudents"));
				request.setAttribute("teacher", teacher);
				request.setAttribute("notMyStudents", studentMap.get("notMyStudents"));
				url = MANAGE_STUDENT_TEACHER_URL;
			} else {
				request.setAttribute("message", "No students registered in the database. Please add a new student");
				url = INDEX_URL;
			}
		break;
		
		case ADD_STUDENT_TO_TEACHER:
			String studentIds[] = request.getParameterValues("studentId");
			Long teacherId = Long.parseLong(request.getParameter("teacherId"));
			
			if (studentIds != null) {
				teacherDao.addStudentToTeacher(teacherId, studentIds);
				request.setAttribute("message", "You have added new Students!");
			} else {
				request.setAttribute("message", "You have not selected any student");
			}
			url = INDEX_URL;
		break;
		
		case REMOVE_STUDENT_FROM_TEACHER:
			Long teacherId1 = Long.parseLong(request.getParameter("tId"));
			Long studentId = Long.parseLong(request.getParameter("sId"));
			teacherDao.removeStudent(teacherId1, studentId);
			url = ("teachers?mode=manageStudents&id="+teacherId1);
		break;
		
		default:
			url = INDEX_URL;
		break;
		
		}
		
		view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}
}
