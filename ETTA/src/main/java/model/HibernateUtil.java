package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Utility class for creating the SessionFactory object needed for connecting to the database
 */
public class HibernateUtil {

	/**
	 * SessionFactory object needed to open session with the database
	 */
	private static SessionFactory factory = null;
	static Connection conn = null;

	/**
	 * Method for creating (if it doesn't already exist) and getting the SessionFactory object
	 * @param test boolean indicating whether a session factory is used for tests or not
	 * @return SessionFactory 
	 */

	public static SessionFactory getSessionFactory() {
		
			if (factory == null) {
				StandardServiceRegistry registry = null;
				try {
					// Create registry
					registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
					// Create SessionFactory
					factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
				} catch (Exception e) {
					System.out.println("Creation of session factory failed");
					StandardServiceRegistryBuilder.destroy(registry);
					e.printStackTrace();
					System.exit(-1);
				}
			}
			return factory;	
	}
	
	public static  Connection getConnection() throws ClassNotFoundException, SQLException {
		
		   Class.forName("org.sqlite.JDBC");
		   conn = DriverManager.getConnection("jdbc:sqlite:mydb.db");
		   
		   System.out.println("База Подключена!");
		   return conn;
	}
        
}