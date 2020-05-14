package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Data access object class for Persons. Used in accessing the table in the database.
 */

public class PersonDAO {
	
	/**
	 * Transaction object to carry out database transactions
	 */
	private Transaction transaction = null;
	
	public static Statement statmt;
	/**
	 * Construction without parameters
	 */
	public PersonDAO() {
		
	}
	
	 /**
	 * Method for creating a new Person in the database
	 * @param person Person to be added to the database
	 * @return success Boolean indicating the success or failure of the database transaction
	 */

	public boolean createPerson(Person person) {
		/*try {
			Connection db = DriverManager.getConnection("jdbc:sqlite:mydb.db");
			statmt = db.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//INSERT INTO people (name, birthday, email) VALUES
		
		
		try {
			statmt.execute("INSERT INTO Person (name, birthday, email) VALUES ('" + person.getName() + "', '" + 
		person.getEmail() +"', " + person.getBirthday() + "); ");
			success = true;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			System.out.println(session);
			transaction = session.beginTransaction();
			System.out.println("creating: 56");
			session.saveOrUpdate(person);
			System.out.println("creating: 58");
			transaction.commit();
			System.out.println("creating: 60");
			success = true;
			System.out.println("creating: " + person.getPerson_id());
			System.out.println("creating: " + person.getName());
			System.out.println("creating: " + person.getBirthday());
			//session.close();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}

		return success;
	}
	
	/**
	 * Method for reading one specific Person in the database with id number
	 * @param person_id the id of the Person to be read
	 * @return person the read Person
	 */
	public Person readPerson(int person_id) {
		Person person = new Person();
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			person = (Person)session.get(Person.class, person_id);		
			transaction.commit();
			System.out.println("reading one:" + person.getName());
		}
		catch(Exception e){
			if (transaction!= null) transaction.rollback();
			throw e;
		}
		return person;
	}
	
	/**
	 * Method for reading one specific Person in the database with name
	 * @param name String the name of the Person to be read
	 * @return person Person read from the database
	 */
	public Person readPerson(String name) {
		System.out.println("trying to read one");
		Person person = new Person();
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			List<Person>  result = session.createQuery( "from Person where name='" + name + "'" ).list();
			if (result.size() != 0) {
				person = result.get(0);		
				transaction.commit();
				System.out.println("result " + result);
				System.out.println("reading one:" + person.getName());
			} else {
				person = null;
			}
		}
		catch(Exception e){
			if (transaction!= null) transaction.rollback();
			throw e;
		}
		return person;
	}
	
	/**
	 * Method for reading all Persons in the database
	 * @return Person[] array with all the people in the database
	 */
	public Person[] readPeople() {
		ArrayList<Person> list = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Person> result = session.createQuery("from Person").getResultList();
			System.out.println("result " + result);
			for(Person person : result) {
				list.add(person);
				System.out.println(person.getBirthday());
				System.out.println("reading all: " + person.getName());
			}
			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		Person[] people = new Person[list.size()];
		return (Person[])list.toArray(people);
	}

	/**
	 * Method for updating a Person in the database
	 * @param person the updated Person
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean updatePerson(Person person) {
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.update(person);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}

	/**
	 * Method for deleting a Person from the database
	 * @param person_id (int) the id of the Person to be deleted
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean deletePerson(int person_id) {
		boolean success = false;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			Person person = (Person)session.get(Person.class, person_id);
			session.delete(person);
			transaction.commit();
			session.close();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}

}
