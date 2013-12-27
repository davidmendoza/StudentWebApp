package com.hibernateWeb.Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.hibernateWeb.Domain.Student;
import com.hibernateWeb.Domain.Teacher;
import com.hibernateWeb.Util.HibernateUtil;

public class AddStudentTeacherServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd;
		Session session = null;
		
		List<Student> studentList = new ArrayList<Student>();
		String studentIds[] = request.getParameterValues("studentId");
		Long id = Long.parseLong(request.getParameter("teacherId"));
		
		if (studentIds != null) {
			try{
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.getTransaction().begin();
				Teacher teacher = (Teacher)session.get(Teacher.class, id);
				
				for (int i = 0; i < studentIds.length; i++){
					Student student = (Student)session.get(Student.class, Long.parseLong(studentIds[i]));
					teacher.getStudents().add(student);
					student.getTeachers().add(teacher);
				}
				
			} catch(HibernateException e) {
				if (session!= null) { 
					session.getTransaction().rollback();
				}
				System.err.println("\tThere was an error in the database: "+e);
			} finally {
				if (session!= null) { 
					session.getTransaction().commit();
				}
				request.setAttribute("message", "Success!");
				rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			}
		} else {
			request.setAttribute("message", "You have not selected any student");
			rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}
	
	}
}
