package com.hibernateWeb.Servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.hibernateWeb.Domain.Student;
import com.hibernateWeb.Util.HibernateUtil;

public class SampleServlet extends HttpServlet{
	
	private static final long serialVersionUID = 5656524316479481290L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		Session session = null;
		PrintWriter out = response.getWriter();
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			List<Student> result = session.createQuery("from Student").list();
			String docType =
		              "<!doctype html public \"-//w3c//dtd html 4.0 " +
		              "transitional//en\">\n";
			out.println(docType+"<html><body>");
			out.println("<h3>Students in Database: </h3>");
			out.println("<table border='1'>");
			out.println("<tr>");
			out.println("<th>Student Number</th>");
			out.println("<th>First Name</th>");
			out.println("<th>Last Name</th>");
			out.println("<th>Address</ht>");
			out.println("</tr>");
			
			for(Student st: result){
				out.println("<tr>");
				out.println("<td>"+st.getId()+"</td>");
				out.println("<td>"+st.getFirstName()+"</td>");
				out.println("<td>"+st.getLastName()+"</td>");
				out.println("<td>"+st.getAddress().getArea()+", "+st.getAddress().getCity());
				out.println("</tr>");
			}
			out.println("</table></body></html>");
			out.flush();
			out.close();
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			session.close();
			
		}
		
		
	}
}
