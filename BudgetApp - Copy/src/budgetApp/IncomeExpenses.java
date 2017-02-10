package budgetApp;
import java.awt.Component;
import java.awt.Container;
import java.io.*;
import java.util.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Represents a budget object.
 * @author Kyle Platou
 * @version 1.0  
 */	


public class IncomeExpenses { 
	
	/**
	 * Keeps track of expense names
	 */
	LinkedList<String> nameList = new LinkedList<String>();
	LinkedList<Double> amountList = new LinkedList<Double>();
	LinkedList<Double> percentList = new LinkedList<Double>();
	LinkedList<Double>	savingsList = new LinkedList<Double>();
	double monthlyIncome = 0.0;
	double yearlyIncome = 0.0;
	double weeklyIncome = 0.0;
	double totalExpenses = 0.0; 
	double totalSaveAmount = 0.0;
	double incomeLessExpense = 0.0;

	/**
	 * Constructs IncomeExpenses class
	 */
	public IncomeExpenses(){		
		
		try {
		readData();	
		}
		catch(IOException ex){
			JOptionPane.showMessageDialog(null, "There is no saved data");
		}
		catch(ClassNotFoundException ex){
			JOptionPane.showMessageDialog(null, "No Class Found");
		}
	}
	
	/**
	 * Reads data from entries.dat file and populates appropriate lists.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void readData() throws ClassNotFoundException, IOException{
		File file = new File("entries.dat");		
		if(file.exists())
		{
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("entries.dat"));			
			nameList = (LinkedList<String>)(input.readObject());
			amountList = (LinkedList<Double>)(input.readObject());
			percentList = (LinkedList<Double>)(input.readObject());
			savingsList = (LinkedList<Double>)(input.readObject());
			yearlyIncome = (double)(input.readObject());
			monthlyIncome = (double)(input.readObject());
			weeklyIncome = (double)(input.readObject());
			totalExpenses =(double)(input.readObject());
			totalSaveAmount = (double)(input.readObject());
			input.close();
		}	
	}	
	
	/**
	 * Deletes an existing entries.dat file and writes LinkedList objects to new file.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void writeData() throws ClassNotFoundException, IOException{			
		File file = new File("entries.dat");
		if(file.exists()){
			file.delete();
		}		
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("entries.dat"));
		output.writeObject(nameList);
		output.writeObject(amountList);
		output.writeObject(percentList);
		output.writeObject(savingsList);
		output.writeObject(yearlyIncome);
		output.writeObject(monthlyIncome);
		output.writeObject(weeklyIncome);
		output.writeObject(totalExpenses);
		output.writeObject(totalSaveAmount);
		output.close();		
	}
	
	/**
	 * Adds expense names and expense amounts to appropriate lists. 
	 * @param name name of expense entered.
	 * @param expenseAmount amount of expense entered.
	 */
	public void addEntry(String name, String expenseAmount){
		nameList.addLast(name);
		amountList.addLast(Double.valueOf(expenseAmount));
		percentList.addLast(calculatePercent(expenseAmount));
		savingsList.addLast(calculateSaveAmount(expenseAmount));		
	}
	
	/**
	 * Deletes entries from appropriate lists based on selection.
	 * If no user selection the entry is deleted from the end of each LinkedList
	 * @param a Component that is selected by user to be deleted 
	 */
	public void deleteEntry(Component a){
		if(a != null){
			Container container = (Container)a;		
			Component[] components = container.getComponents();		
			JLabel  b = (JLabel) components[0];		
			System.out.print(a);	
		
		for(int i = 0; i < nameList.size(); i++)
			if(b.getText() == nameList.get(i)){
				nameList.remove(i);
				amountList.remove(i);
				percentList.remove(i);
				savingsList.remove(i);
				System.out.println("Entry deleted.");
			}
		}
		else{
			nameList.removeLast();
			amountList.removeLast();
			percentList.removeLast();
			savingsList.removeLast();
			System.out.println("Entry deleted.");
		}					
	}
	
	/**
	 * Calculates a percent that an expense is, of monthly income.
	 * @param amount Expense amount.
	 * @return double
	 */	
	public double calculatePercent(String amount){
		return (double)((Double.valueOf(amount) / monthlyIncome) * 100);
	}
	
	/**
	 * Calculates the amount to save per week for each monthly expense.
	 * @param expenseAmount Amount of expense.
	 * @return double
	 */
	public double calculateSaveAmount(String expenseAmount){
		return (double)((Double.valueOf(expenseAmount) * 12) / 52);
	}
	
	/**
	 * Calculates and sets the yearly, monthly and weekly income for a budget.
	 * @param income Weekly paycheck amount or weekly income
	 */
	public void setIncome(String income){		
		yearlyIncome = Double.valueOf(income) * 52;
		monthlyIncome = yearlyIncome / 12;
		weeklyIncome = Double.valueOf(income);
	}
	/**
	 * Totals all budget expenses
	 * @return double 
	 */
	public double calculateTotalExpenses(){
		totalExpenses = 0.0;
		ListIterator<Double> listIterator = amountList.listIterator();
		while(listIterator.hasNext()){
			totalExpenses += Double.valueOf(listIterator.next());
		}
		return totalExpenses; 
	}
	
	/**
	 * Calculates the total amount to save per week in order to afford expenses.
	 * @return double
	 */
	public double calculateTotalSaveAmount(){
		totalSaveAmount = 0.0;
		ListIterator<Double> listIterator = savingsList.listIterator();
		while(listIterator.hasNext()){
			totalSaveAmount += Double.valueOf(listIterator.next());
		}
		return totalSaveAmount;
	}
	
	/**
	 * Calculates income minus expenses
	 * @return double
	 */
	public double incomeLessExpense(){
		incomeLessExpense = monthlyIncome - totalExpenses;
		return incomeLessExpense; 
	}
	
	/**
	 * Recalculates the percent of income for each expense
	 */
	public void incomeRecalculate(){
		ListIterator<Double> listIterator = amountList.listIterator();		
		percentList.removeAll(percentList);		
		
		while(listIterator.hasNext()){
			double temp = Double.valueOf(listIterator.next());
		    percentList.add((double)((temp / monthlyIncome) * 100));		    
		}
		
	}
}
