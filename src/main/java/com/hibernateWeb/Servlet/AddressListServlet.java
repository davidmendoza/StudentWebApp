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

import com.hibernateWeb.Domain.Address;
import com.hibernateWeb.Util.HibernateUtil;

public class AddressListServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Session session = null;
		RequestDispatcher rd;
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			List<Address> cities = session.createQuery("from Address").list();
			
			if (!cities.isEmpty()){
				request.setAttribute("cities", cities);
				rd = request.getRequestDispatcher("addStudent.jsp");
			} else {
				request.setAttribute("error", "There was an error in the database. Please try again. ");
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
