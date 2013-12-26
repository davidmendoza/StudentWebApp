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
import com.hibernateWeb.Domain.Teacher;
import com.hibernateWeb.Util.HibernateUtil;

public class AddTeacherServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		Address address;
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String gender = request.getParameter("gender");
		String newCity = request.getParameter("newCity");
		Long addressId = Long.parseLong(request.getParameter("city"));
		RequestDispatcher rd;
		Session session = null;
		if (addressId == 0 && newCity.trim() == ""){
			request.setAttribute("message", "You have not entered an Address. Please fill up the form again");
			rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		} else {
			try{
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.getTransaction().begin();
				
				if (addressId == 0 || newCity.trim() != ""){
					address = new Address(newCity);
					session.save(address);
				} else {
					address = (Address)session.get(Address.class, addressId);
				}
				Teacher teacher = new Teacher();
				teacher.setFirstName(firstName);
				teacher.setLastName(lastName);
				teacher.setGender(gender);
				teacher.setAddress(address);
				session.save(teacher);
				request.setAttribute("message", "Successfully added "+teacher.getFirstName());
				
			}  catch(HibernateException e) {
				if (session!= null) { 
					session.getTransaction().rollback();
				}
				System.err.println("\tThere was an error in the database: "+e);
			} finally {
				if (session!= null) { 
					session.getTransaction().commit();
				}
				rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			}
		}
	}
}

