package com.hibernateWeb.Servlet;


import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.hibernateWeb.Domain.Teacher;
import com.hibernateWeb.Util.HibernateUtil;

public class ViewAllTeachersServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		Session session = null;
		RequestDispatcher rd = null;
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			List<Teacher> result = session.createQuery("from Teacher").list();
			if (!result.isEmpty()){
				request.setAttribute("allTeachers", result);
				
				rd = request.getRequestDispatcher("viewTeachers.jsp");
				
			} else {
				request.setAttribute("message", "No teachers registered in the database. Please add a new teacher");
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

