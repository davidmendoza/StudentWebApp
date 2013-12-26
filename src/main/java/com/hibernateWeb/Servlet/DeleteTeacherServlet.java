package com.hibernateWeb.Servlet;

import java.io.IOException;

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

public class DeleteTeacherServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Session session = null;
		RequestDispatcher rd;
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Long id = Long.parseLong(request.getParameter("id"));
			Teacher teacher = (Teacher)session.get(Teacher.class, id);
			session.delete(teacher);
		} catch(HibernateException e) {
			if (session!= null) { 
				session.getTransaction().rollback();
			}
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			if (session!= null) { 
				session.getTransaction().commit();
			}
			rd = request.getRequestDispatcher("viewTeachers");
			rd.forward(request, response);
		}
	}
}

