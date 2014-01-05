package com.hibernateWeb.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.hibernateWeb.Domain.Grades;
import com.hibernateWeb.Domain.Student;
import com.hibernateWeb.Util.HibernateUtil;

public class GradesDAO {
	
	Session session = null;
	
	public void saveGrades(Long id, int math, int english, int science){
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			
			Student student = (Student) session.get(Student.class, id);
					
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
				session.close();
			}
		}
	}
}
