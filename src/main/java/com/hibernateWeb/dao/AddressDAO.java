package com.hibernateWeb.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.hibernateWeb.Domain.Address;
import com.hibernateWeb.Util.HibernateUtil;

public class AddressDAO {
	
	public List<Address> getAddressList(){
		Session session = null;
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
}
