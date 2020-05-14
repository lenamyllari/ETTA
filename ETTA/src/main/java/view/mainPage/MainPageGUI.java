package view.mainPage;

import java.text.DateFormat;
import java.util.Locale;

import org.joda.time.LocalDate;

import controller.MainViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.EventDAO;

/**
 * GUI class relating to the main page. Displays the balance amount, today's date and today's events.
 * @author Lena
 */
public class MainPageGUI{

	/**
	 * Reference to the used MainViewController
	 */
	MainViewController controller;

	
	/**
	 * EventDAO used for accessing the database
	 */
	EventDAO eventDAO = new EventDAO();
	
	/**
	 * The reference of Label for amount of the balance will be injected by the FXML loader
	 */
	@FXML
	private Label amountBalance;
	
	/**
	 * The reference of Label for today's date will be injected by the FXML loader
	 */
	@FXML
	private Label todaysDate;
	
	/** 
	 * Constructor
	 * Creates the MainViewController, passes itself as a parameter to the controller
	 */ 
	public MainPageGUI() {
		controller = new MainViewController(this);
		
	}
	
	/** 
	 * Method that sets and displays the balance amount on the page 
	 * @param amount the balance amount
	 */
	public void setBalance(double amount) {
		String balanceString = String.format("%.2f", amount);
		amountBalance.setText(balanceString);
	}
	
	/** 
	 * Method that initializes the view, gets the balance, displays balance and today's date, 
	 * gets the events for today  from the eventDAO to display them on the page
	 */
	@FXML
	public void initialize() {
		controller.getBalance();
		//gets today's date
		Locale locale = Locale.getDefault();
	    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		LocalDate localDate = LocalDate.now();
		String text = localDate.toString();
		java.util.Date sqlDate = java.sql.Date.valueOf(text);
		todaysDate.setText(df.format(sqlDate));
		
	}

}
