package com.hibernateWeb.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.hibernateWeb.Domain.Address;
import com.hibernateWeb.Domain.Student;
import com.hibernateWeb.Util.HibernateUtil;

public class AddressDAO {
	
	Session session = null;
	
	public List<Address> getAddressList(){
		
		List<Address> cities = null;
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			cities = session.createQuery("from Address").list();
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			session.close();
		}	
		return cities;
	}
	
	public Address getAddress(Long id){
		
		Address address = null;
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			address = (Address)session.get(Address.class,id);
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			session.close();
		}
		
		return address;
	}
	
	public Address addAddress(String city){
		
		Address address = null;
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			address = new Address(city);
			session.save(address);
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
		
		return address;
	}
	
}
