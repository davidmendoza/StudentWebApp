package com.hibernateWeb.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.hibernateWeb.Domain.Grades;
import com.hibernateWeb.Domain.Student;
import com.hibernateWeb.Util.HibernateUtil;

public class EditGradesServlet extends HttpServlet{
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Session session = null;
		RequestDispatcher rd;
		Long id = Long.parseLong(request.getParameter("id"));
		int math = Integer.parseInt(request.getParameter("math"));
		int science = Integer.parseInt(request.getParameter("science"));
		int english = Integer.parseInt(request.getParameter("english"));
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Student student = (Student)session.get(Student.class, id);
			
			Grades grade = student.getGrade();
			if (grade == null){
				Grades newGrade = new Grades();
				newGrade.setEnglish(english);
				newGrade.setMath(math);
				newGrade.setScience(science);
				student.setGrade(newGrade);
				session.save(newGrade);
			} else {
				grade.setEnglish(english);
				grade.setMath(math);
				grade.setScience(science);
				student.setGrade(grade);
			}
			session.update(student);
		} catch(HibernateException e) {
			if (session!= null) { 
				session.getTransaction().rollback();
			}
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			if (session!= null) { 
				session.getTransaction().commit();
			}
			rd = request.getRequestDispatcher("allStudents?mode=grades");
			rd.forward(request, response);
		}
		
	}
}
