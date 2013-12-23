package com.hibernateWeb.Servlet;

import java.io.IOException;

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

public class AddStudentServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Student student = new Student();
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String gender = request.getParameter("gender");
		int level = Integer.parseInt(request.getParameter("level"));
		Long addressId = Long.parseLong(request.getParameter("city"));
		RequestDispatcher rd;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Address address = (Address)session.get(Address.class, addressId);
			student.setFirstName(firstName);
			student.setLastName(lastName);
			student.setGender(gender);
			student.setLevel(level);
			student.setAddress(address);
			session.save(student);
			request.setAttribute("message", "Successfully added "+student.getFirstName());
			rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}  catch(HibernateException e) {
			if (session!= null) { 
				session.getTransaction().rollback();
			}
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			if (session!= null) { 
				session.getTransaction().commit();
			}
		}
	}
}
