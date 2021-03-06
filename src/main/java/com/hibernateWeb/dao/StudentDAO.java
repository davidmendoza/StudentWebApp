package com.hibernateWeb.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.hibernateWeb.Domain.Address;
import com.hibernateWeb.Domain.Student;
import com.hibernateWeb.Util.HibernateUtil;
import com.hibernateWeb.beans.StudentBean;

public class StudentDAO {
	
	Session session = null;
	
	public void addStudent(StudentBean studentBean, Long addressId, String newCity){
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			
			Address address = null;
			if (addressId == 0 || newCity != ""){
				address = new Address(newCity);
			} else {
				address = (Address) session.get(Address.class, addressId);
			}
			
			Student student = new Student();			
			student.setFirstName(studentBean.getFirstName());
			student.setLastName(studentBean.getLastName());
			student.setGender(studentBean.getGender());
			student.setLevel(studentBean.getLevel());
			student.setAddress(address);
			session.persist(student);
			
		}  catch(HibernateException e) {
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
	
	public List<Student> getStudentList(){
		
		List<Student> result = null;
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			result = session.createQuery("from Student").list();
			
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			session.close();
		}
		
		return result;
	}
	
	public void deleteStudent(Long id){
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			Student st = (Student)session.get(Student.class, id);
			session.delete(st);
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
	
	public Student getStudent(Long id){
		
		Student student = null;
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			student = (Student)session.get(Student.class, id);
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			session.close();
		}
		
		return student;
	}
	
	public void updateStudent(StudentBean studentBean, Long addressId, String newCity){
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			Address address = null;
			Student student = (Student)session.get(Student.class, studentBean.getId());
			
			if (addressId == 0 || newCity != ""){
				address = new Address(newCity);
			} else {
				address = (Address) session.get(Address.class, addressId);
			}
			
			student.setFirstName(studentBean.getFirstName());
			student.setLastName(studentBean.getLastName());
			student.setGender(studentBean.getGender());
			student.setLevel(studentBean.getLevel());
			student.setAddress(address);
			session.persist(student);
			
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
