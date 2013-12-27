package com.hibernateWeb.Util;

import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class TestSearch {
	
	public static void main (String[] args) {
		Scanner sc = new Scanner(System.in);
		String hql;
		int response = 99;
		Session session = null;
			System.out.print("\tEnter hql query :$ ");
			hql = sc.nextLine();
			try{
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.getTransaction().begin();
				Query query = session.createQuery(hql);
				List<?> result = query.list();
				if (!result.isEmpty()) {
					System.out.println("\tClass: "+result.get(0).getClass());
					System.out.println("\tQuery Success!"+result.size()+" rows returned");
				} else {
					System.out.println("\tno results in query");
				}
				
			} catch(HibernateException e) {
				System.err.println("There was a problem with the database "+ e);
			} catch(IndexOutOfBoundsException e) {
				System.err.println("Fail "+ e);
			} catch(IllegalArgumentException e) {
				System.err.println("Fail "+ e);
			} catch (IllegalStateException e) {
				System.err.println("Fail "+ e);
			} finally {
				System.out.println("Another query? [1]Yes [0]No");
				response = sc.nextInt();
				session.close();
			}

	}
}
