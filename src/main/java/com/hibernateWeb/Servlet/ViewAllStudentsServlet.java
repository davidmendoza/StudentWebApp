package com.hibernateWeb.Servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

public class ViewAllStudentsServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		Session session = null;
		RequestDispatcher rd;
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			List<Student> result = session.createQuery("from Student").list();
			if (!result.isEmpty()){
				request.setAttribute("allStudents", result);
				 rd = request.getRequestDispatcher("viewStudents.jsp");
			} else {
				request.setAttribute("message", "There was an error in the database. Please try again. ");
				rd = request.getRequestDispatcher("index.jsp");
			}
			rd.forward(request, response);
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			session.close();
			
		}
		
		
	}
}
