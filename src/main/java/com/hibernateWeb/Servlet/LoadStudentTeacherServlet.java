package com.hibernateWeb.Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.hibernateWeb.Domain.Student;
import com.hibernateWeb.Domain.Teacher;
import com.hibernateWeb.Util.HibernateUtil;

public class LoadStudentTeacherServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Session session = null;
		RequestDispatcher rd = null;
		Long id = Long.parseLong(request.getParameter("id"));
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Teacher teacher = (Teacher)session.get(Teacher.class, id);
			List<Student> notMyStudents = session.createQuery("from Student").list();
			String hql = ("select s from Teacher t JOIN t.students s where t.id = :id");
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			List<Student> myStudents = query.list();
			notMyStudents.removeAll(myStudents);
			
			
			if (!notMyStudents.isEmpty()){
				request.setAttribute("myStudents", myStudents);
				request.setAttribute("teacher", teacher);
				request.setAttribute("notMyStudents", notMyStudents);
				//request.setAttribute("students", students);
				rd = request.getRequestDispatcher("manageStudentTeacher.jsp");
			} else {
				request.setAttribute("message", "No students registered in the database. Please add a new student");
				rd = request.getRequestDispatcher("index.jsp");
			}
			
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			rd.forward(request, response);
			session.close();
			
		}
	}
}

