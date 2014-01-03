package com.hibernateWeb.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
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
	
	private static final Map<String, Integer> OPTIONS = createOptions();
	private static final int NEW_TEACHER = 1;
	private static final int VIEW_TEACHERS = 2;
	private static final int DELETE_TEACHER = 3;
	private static final int MANAGE_STUDENTS = 4;
	private static final int ADD_STUDENT_TO_TEACHER = 5;
	private static final int REMOVE_STUDENT_FROM_TEACHER = 6;
	private static final String MANAGE_STUDENT_TEACHER_URL = "manageStudentTeacher.jsp";
	private static final String VIEW_TEACHERS_URL = "viewTeachers.jsp";
	private static final String ADD_TEACHER_URL = "addTeacher.jsp;";
	private static final String INDEX_URL = "index.jsp";
	private static AddressDAO addressDao = new AddressDAO();
	private static TeacherDAO teacherDao = new TeacherDAO();
	
	private static Map<String, Integer> createOptions(){
		Map<String, Integer> options = new HashMap<String, Integer>();
		options.put("new", 1);
		options.put("view", 2);
		options.put("delete", 3);
		options.put("manageStudents", 4);
		options.put("addStudentTeacher", 5);
		options.put("removeStudent", 6);
		return Collections.unmodifiableMap(options);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TeacherBean tb = new TeacherBean();
		RequestDispatcher view;
		
		try {
			tb.setFirstName(request.getParameter("firstName"));
			tb.setLastName(request.getParameter("lastName"));
			tb.setGender(request.getParameter("gender"));
			String newCity = request.getParameter("newCity").trim();
			Long addressId = Long.parseLong(request.getParameter("city"));
			
			if (addressId == 0 && newCity == "") {
				request.setAttribute("message", "You have not entered an Address. Please fill up the form again");
			} else {				
				teacherDao.addTeacher(tb, addressId, newCity);
				request.setAttribute("message", "Successfully added Teacher "+tb.getFirstName());
			}
			
		} catch(Exception e){
			System.out.println("error "+ e);
			request.setAttribute("message", "There was a problem in the system. Please try again.");
		} finally {
			view = request.getRequestDispatcher("index.jsp");
			view.forward(request, response);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher view;
		String option = request.getParameter("mode");
		String url = null;
		int mode = OPTIONS.get(option);
		try {
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
		} catch(Exception e) {
			System.out.println("error "+e);
			request.setAttribute("message", "There was a problem with the system. Please try again.");
			url = INDEX_URL;
		}
		view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}
}
