package com.hibernateWeb.controller;

import java.io.IOException;
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
import com.hibernateWeb.beans.StudentBean;
import com.hibernateWeb.dao.AddressDAO;
import com.hibernateWeb.dao.GradesDAO;
import com.hibernateWeb.dao.StudentDAO;

public class StudentController extends HttpServlet{
	
	private static final String NEW_STUDENT = "new";
	private static final String VIEW_STUDENTS = "view";
	private static final String DELETE_STUDENT = "delete";
	private static final String UPDATE_STUDENT = "update";
	private static final String VIEW_GRADES = "grades";
	private static final String SAVE_GRADES ="saveGrades";
	private static final String ADD_STUDENT_URL = "addStudent.jsp;";
	private static final String VIEW_STUDENTS_URL = "viewStudents.jsp";
	private static final String VIEW_GRADES_URL = "viewGrades.jsp";
	private static final String INDEX_URL = "index.jsp";
	private static StudentDAO studentDao = new StudentDAO();
	private static AddressDAO addressDao = new AddressDAO();
	private static GradesDAO gradesDao = new GradesDAO();
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StudentBean sb = new StudentBean();
		RequestDispatcher view;
		
		Long id = Long.parseLong(request.getParameter("id"));
		sb.setFirstName(request.getParameter("firstName"));
		sb.setLastName(request.getParameter("lastName"));
		sb.setGender(request.getParameter("gender"));
		sb.setLevel(Integer.parseInt(request.getParameter("level")));
		String newCity = request.getParameter("newCity");
		Long addressId = Long.parseLong(request.getParameter("city"));
		
		if (addressId == 0 && newCity.trim() != ""){
			
			if (addressId == 0 || newCity.trim() != ""){
				sb.setAddress(addressDao.addAddress(newCity));
			} else {
				sb.setAddress(addressDao.getAddress(addressId));
			}
			
			if (id == 0){
				studentDao.addStudent(sb);
				request.setAttribute("message", "Successfully added Student "+sb.getFirstName());
			} else {
				sb.setId(id);
				studentDao.updateStudent(sb);
				request.setAttribute("message", "Successfully updated Student "+sb.getFirstName());
			}
		
		} else {
			request.setAttribute("message", "You have not entered an Address. Please fill up the form again");
		}
		
		view = request.getRequestDispatcher("index.jsp");
		view.forward(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher view;
		Map<String, String> titles = new HashMap<String, String>();
		String mode = request.getParameter("mode");
		String url = null;
		switch(mode){
			
		case NEW_STUDENT:
			titles.put("title", "Add New Student");
			titles.put("submit", "Add Student");
			List<Address> addCities = addressDao.getAddressList();
			request.setAttribute("cities", addCities);
			request.setAttribute("titles", titles);
			url = ADD_STUDENT_URL;
		break;
		
		case VIEW_STUDENTS:
			List<Student> students = studentDao.getStudentList();
			if (!students.isEmpty()){
				request.setAttribute("allStudents", students);
				url = VIEW_STUDENTS_URL;
			} else {
				request.setAttribute("message", "No students registered in the database. Please add a new student");
				url = INDEX_URL;
			}
		break;
		
		case DELETE_STUDENT:
			Long deleteId = Long.parseLong(request.getParameter("id"));
			studentDao.deleteStudent(deleteId);
			url = "students?mode=view";
		break;
		
		case UPDATE_STUDENT:
			titles.put("title", "Update Student Details");
			titles.put("submit", "Update Student");
			Long updateId = Long.parseLong(request.getParameter("id"));
			Student student = studentDao.getStudent(updateId);
			List<Address> updateCities = addressDao.getAddressList();
			request.setAttribute("cities", updateCities);
			request.setAttribute("titles", titles);
			request.setAttribute("student", student);
			url = ADD_STUDENT_URL;
		break;
		
		case VIEW_GRADES:
			List<Student> studentList = studentDao.getStudentList();
			if (!studentList.isEmpty()){
				request.setAttribute("allStudents", studentList);
				url = VIEW_GRADES_URL;
			} else {
				request.setAttribute("message", "No students registered in the database. Please add a new student");
				url = INDEX_URL;
			}
		break;
		
		case SAVE_GRADES:
			Long id = Long.parseLong(request.getParameter("id"));
			try{
				int math = Integer.parseInt(request.getParameter("math"));
				int science = Integer.parseInt(request.getParameter("science"));
				int english = Integer.parseInt(request.getParameter("english"));
				
				Student studentGrade = studentDao.getStudent(id);
				gradesDao.saveGrades(studentGrade, math, english, science);
				request.setAttribute("message", "Saved grade!");
			} catch (Exception e) {
				request.setAttribute("message", "Please enter a valid grade.");
			}
			url = "students?mode=grades";
		break;
		
		default:
			url = INDEX_URL;
		break;
		}
		
		view = request.getRequestDispatcher(url);
		view.forward(request, response);
		
	}
}
