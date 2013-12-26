package com.hibernateWeb.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.hibernateWeb.Domain.Address;
import com.hibernateWeb.Domain.Student;
import com.hibernateWeb.Util.HibernateUtil;

public class LoadStudentServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Session session = null;
		RequestDispatcher rd;
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			String mode = request.getParameter("mode");
			Map<String, String> titles = new HashMap<String, String>();
			if(mode.equals("update")) {
				titles.put("title", "Update Student Details");
				titles.put("submit", "Update Student");
				titles.put("url","updateStudent");
				
				Long id = Long.parseLong(request.getParameter("id"));
				Student st = (Student)session.get(Student.class, id);
				request.setAttribute("student", st);
			}
			if(mode.equals("new")){
				titles.put("title", "Add New Student");
				titles.put("submit", "Add Student");
				titles.put("url","addStudent");
			}
			
			List<Address> cities = session.createQuery("from Address").list();
			
			request.setAttribute("cities", cities);
			request.setAttribute("titles", titles);
			rd = request.getRequestDispatcher("addStudent.jsp");
			rd.forward(request, response);
			
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			session.close();
			
		}
	}
}