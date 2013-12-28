package com.hibernateWeb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.hibernateWeb.Domain.Student;
import com.hibernateWeb.Domain.Teacher;
import com.hibernateWeb.Domain.Teacher;
import com.hibernateWeb.Util.HibernateUtil;
import com.hibernateWeb.beans.TeacherBean;

public class TeacherDAO {

Session session = null;
	
	public void addTeacher(TeacherBean teacherBean){
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
		
			Teacher teacher = new Teacher();
			teacher.setFirstName(teacherBean.getFirstName());
			teacher.setLastName(teacherBean.getLastName());
			teacher.setGender(teacherBean.getGender());
			teacher.setAddress(teacherBean.getAddress());
			session.save(teacher);
			
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
	
	public List<Teacher> getTeacherList(){
		
		List<Teacher> result = null;
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			result = session.createQuery("from Teacher").list();
			
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			session.close();
		}
		
		return result;
	}
	
	public void deleteTeacher(Long id){
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
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
		}
	}
	
	public Teacher getTeacher(Long id){
		
		Teacher Teacher = null;
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Teacher = (Teacher)session.get(Teacher.class, id);
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			session.close();
		}
		
		return Teacher;
	}
	
	public Map<String, List<Student>> getStudentMap(Long id){
		
		Map<String, List<Student>> studentMap = new HashMap<String, List<Student>>();
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			List<Student> notMyStudents = session.createQuery("from Student").list();
			
			List<Student> allStudents = new ArrayList<Student>(notMyStudents);
			String hql = ("select s from Teacher t JOIN t.students s where t.id = :id");
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			List<Student> myStudents = query.list();
			notMyStudents.removeAll(myStudents);
			
			studentMap.put("notMyStudents", notMyStudents);
			studentMap.put("myStudents", myStudents);
			studentMap.put("allStudents", allStudents);
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			session.close();
		}
		return studentMap;
	}
	
	public void addStudentToTeacher(Long teacherId, String[] studentIds){
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Teacher teacher = (Teacher)session.get(Teacher.class, teacherId);
			
			for (int i = 0; i < studentIds.length; i++){
				Student student = (Student)session.get(Student.class, Long.parseLong(studentIds[i]));
				teacher.getStudents().add(student);
				student.getTeachers().add(teacher);
			}
			
		} catch(HibernateException e) {
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
	
	public void removeStudent(Long teacherId, Long studentId){
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Teacher teacher = (Teacher)session.get(Teacher.class, teacherId);
			Student student = (Student)session.get(Student.class, studentId);
			teacher.getStudents().remove(student);
			student.getTeachers().remove(teacher);
		} catch(HibernateException e) {
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
