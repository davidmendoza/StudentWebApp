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

public class UpdateStudentServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Address address;
		Long id = Long.parseLong(request.getParameter("id"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String gender = request.getParameter("gender");
		String newCity = request.getParameter("newCity");
		int level = Integer.parseInt(request.getParameter("level"));
		Long addressId = Long.parseLong(request.getParameter("city"));
		RequestDispatcher rd;
		Session session = null;
		
		if (addressId == 0 && newCity.trim() == ""){
			request.setAttribute("message", "You have not entered an Address. Please edit the form again");
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
				
				Student student = (Student)session.get(Student.class, id);
				student.setFirstName(firstName);
				student.setLastName(lastName);
				student.setGender(gender);
				student.setLevel(level);
				student.setAddress(address);
				session.update(student);
				request.setAttribute("message", "Successfully updated"+student.getFirstName());
				
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