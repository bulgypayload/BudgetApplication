package budgetApp;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/** 
 * Creates GUI extends JFrame 
 * @author Kyle Platou
 */

public class BudgetGUI extends JFrame{
	/**
	 * This is to tell about the serialVersionUID
	 */
	private static final long serialVersionUID = -6690830799630322478L;
	JLabel contentPane = new JLabel();
	JPanel incomePanel = new JPanel(new GridLayout(3, 0));
	JPanel income1Panel = new JPanel(new FlowLayout());
	JPanel backgroundPanel = new BackgroundPanel();
	JLabel directions = new JLabel("Please enter your paycheck amount.");
	JTextField textPay = new MyTextField(10);
	JButton recalculateButton = new MyButton("Recalculate");
	JPanel inputPanel = new JPanel(new FlowLayout());
	JPanel headerPanel = new JPanel(new GridLayout(0,4));
	JTextField expenseName = new MyTextField(15);	
	JTextField expenseAmount = new MyTextField(15);	
	JButton addButton = new MyButton("Add");
	JButton deleteButton = new MyButton("Delete");
	JPanel viewPanel = new JPanel(new GridLayout( 0, 1, 0, 0));
	JButton saveButton = new MyButton("Save Budget");
	IncomeExpenses myBudget = new IncomeExpenses();
	JLabel labelHead = new JLabel("Expense Name");
	JLabel labelHead1 = new JLabel("Expense Amount");
	JLabel labelHead2 = new JLabel("Expense Percentage");
	JLabel labelHead3 = new JLabel("Cost Per Week");
	Font header = new Font("SansSerf", Font.BOLD, 14);
	Font entries = new Font("SansSerf", Font.BOLD, 14);
	Font entries2 = new Font("SansSerf",Font.PLAIN, 14);
	Font fontFooter = new Font("SansSerf", Font.BOLD, 16);
	Color colorRed = new Color(255, 51, 51);	
	Color colorBlue = Color.BLUE;
	Color white = Color.WHITE;
	Color colorGreen = new Color(75, 255, 77);
	Component a ;
	
	
	/**
	 * Constructor to create BudgetGUI
	 */
	public BudgetGUI(){
		
		backgroundPanel.setLayout(new BorderLayout());
		//backgroundPanel.setBackground(Color.DARK_GRAY);
		
		expenseName.setText("Enter Expense Name");
		expenseAmount.setText("Enter Expense Amount");
		expenseName.setFont(entries2);
		expenseAmount.setFont(entries2);
		
		textPay.setToolTipText("Enter your weekly pay amount here");
		deleteButton.setToolTipText("Press to delete an expense entry");
		addButton.setToolTipText("Press to add an expense entry");
		expenseName.setToolTipText("Enter name of expense here.");
		expenseAmount.setToolTipText("Enter the amount of the expense here.");
		saveButton.setToolTipText("Press here to save this budget.");
		recalculateButton.setToolTipText("Click after changing pay amount");
		deleteButton.setBackground(Color.WHITE);
		addButton.setBackground(Color.WHITE);
		saveButton.setBackground(Color.WHITE);
		
		recalculateButton.setBackground(Color.WHITE);		
		textPay.setBackground(Color.WHITE);
		expenseName.setBackground(Color.WHITE);
		expenseAmount.setBackground(Color.WHITE);
//		income1Panel.setBackground(Color.DARK_GRAY);
//		inputPanel.setBackground(Color.DARK_GRAY);
//		headerPanel.setBackground(Color.DARK_GRAY);
//		viewPanel.setBackground(Color.DARK_GRAY);
		
		textPay.setFont(entries);
		expenseName.setForeground(Color.LIGHT_GRAY);
		expenseAmount.setForeground(Color.LIGHT_GRAY);
		
		labelHead.setFont(header);
		labelHead1.setFont(header);
		labelHead2.setFont(header);
		labelHead3.setFont(header);
		labelHead.setForeground(white);
		labelHead1.setForeground(white);
		labelHead2.setForeground(white);
		labelHead3.setForeground(white);
		directions.setForeground(white);
		income1Panel.add(directions);
		income1Panel.add(textPay);
		income1Panel.add(recalculateButton);
		income1Panel.setOpaque(false);
		incomePanel.add(income1Panel);
		incomePanel.add(inputPanel);
		incomePanel.add(headerPanel);
		incomePanel.setOpaque(false);
		inputPanel.add(addButton);
		inputPanel.add(deleteButton);
		inputPanel.add(expenseName);
		inputPanel.add(expenseAmount);
		inputPanel.setOpaque(false);
		headerPanel.add(labelHead);
		headerPanel.add(labelHead1);
		headerPanel.add(labelHead2);
		headerPanel.add(labelHead3);
		headerPanel.setOpaque(false);
		headerPanel.setBorder(new LineBorder(white, 2));
		viewPanel.setOpaque(false);
		
		
		
		backgroundPanel.add(incomePanel,BorderLayout.NORTH);		
		backgroundPanel.add(viewPanel, BorderLayout.CENTER);
		backgroundPanel.add(saveButton,BorderLayout.SOUTH);
		
		add(backgroundPanel);
		
		
		if(myBudget.nameList.isEmpty() == false){
			textPay.setText(String.format("%6.2f", myBudget.weeklyIncome));
			createEntryPanel(myBudget.nameList, myBudget.amountList, myBudget.percentList, myBudget.savingsList);
		}
		
		addButton.addActionListener(new AddListenerClass());
		deleteButton.addActionListener(new DeleteListenerClass());
		saveButton.addActionListener(new SaveListenerClass());
		textPay.addFocusListener(new TextFieldListenerClass());
		expenseName.addFocusListener(new TextFieldListenerClass());
		expenseAmount.addFocusListener(new TextFieldListenerClass());
		recalculateButton.addActionListener(new RecalculateListenerClass());
		expenseAmount.addKeyListener(new EnterKeyListenerClass());
		addWindowListener(new OnCloseAdapter());
		
	}
	
	/**
	 * Iterates through each parameter and adds them to the appropriate 
	 * JPanel in order to update the addition of a new expense entry
	 * @param nameList Expense names
	 * @param amountList Expense amounts
	 * @param percentList Expense percentage of income
	 * @param savingsList Amount to save each week to cover expense.
	 */
	public void createEntryPanel(LinkedList<String> nameList, LinkedList<Double> amountList, 
			LinkedList<Double> percentList, LinkedList<Double>savingsList){
		ListIterator<String> listIterator = nameList.listIterator();
		ListIterator<Double> listIterator1 = amountList.listIterator();
		ListIterator<Double> listIterator2 = percentList.listIterator();
		ListIterator<Double> listIterator3 = savingsList.listIterator();
		viewPanel.removeAll();		
		while(listIterator.hasNext()){
			JLabel label = new JLabel(listIterator.next());
			JLabel label1 = new JLabel("$" + String.format("%6.2f",listIterator1.next()));
			JLabel label2 = new JLabel(String.format("%6.2f", listIterator2.next()) + "%");
			JLabel label3 = new JLabel("$" + String.format("%6.2f",listIterator3.next()));
			JPanel entryPanel = new JPanel(new GridLayout(0,4));
			entryPanel.addFocusListener(new EntryFocusClass());
			entryPanel.addMouseListener(new MouseListenerClass());
			entryPanel.setBackground(Color.GREEN);
			label.setFont(entries);
			label1.setFont(entries);
			label2.setFont(entries);
			label3.setFont(entries);
			label.setForeground(white);
			label1.setForeground(colorRed);
			label2.setForeground(white);
			label3.setForeground(white);
			entryPanel.setBorder(new LineBorder(Color.GREEN,1));
			entryPanel.setOpaque(false);
			entryPanel.add(label);
			entryPanel.add(label1);
			entryPanel.add(label2);
			entryPanel.add(label3);
			viewPanel.add(entryPanel);
			}
		JLabel labelIncome = new JLabel("Monthly income: $" + String.format("%6.2f", myBudget.monthlyIncome));
		JLabel labelTotalExpenses = new JLabel("Total Expenses: $" + String.format("%6.2f", myBudget.calculateTotalExpenses()));
		JLabel labelIncomeLessExpenses = new JLabel("Profit: $" + String.format("%6.2f", myBudget.incomeLessExpense()));
		JLabel labelSave = new JLabel("Total week cost: $" + String.format("%6.2f",myBudget.calculateTotalSaveAmount()));
		JPanel footer = new JPanel(new GridLayout(0, 4));
		footer.setOpaque(false);
		labelIncome.setForeground(colorGreen);
		labelIncome.setFont(fontFooter);
		labelTotalExpenses.setForeground(colorRed);
		labelTotalExpenses.setFont(fontFooter);
		labelIncomeLessExpenses.setForeground(white);
		labelIncomeLessExpenses.setFont(fontFooter);
		labelSave.setForeground(white);
		labelSave.setFont(fontFooter);
		footer.add(labelIncome);
		footer.add(labelTotalExpenses);
		footer.add(labelIncomeLessExpenses);
		footer.add(labelSave);
		viewPanel.add(footer);	
		viewPanel.updateUI();
	}
	
	/**
	 * Clears the text from Name and amount text fields.
	 */
	public void clearText(){
		expenseName.setText("");
		expenseAmount.setText("");
	}
	
	/**
	 * Checks texts fields for input and displays dialog if fields are not filled in. 
	 * Calls add entry method and updates panel 
	 */
	public void addGUIEntry(){
		if(textPay.getText().isEmpty() || expenseName.getText().isEmpty() || expenseAmount.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Please fill out all available fields");
		}
		else if(expenseName.getText().equals("Enter Expense Name") || expenseAmount.getText().equals("Enter Expense Amount")){
			JOptionPane.showMessageDialog(null, "Please fill out all available fields");
		}
		else{
		myBudget.setIncome(textPay.getText());
		myBudget.addEntry(expenseName.getText(), expenseAmount.getText());
		createEntryPanel(myBudget.nameList, myBudget.amountList, myBudget.percentList, myBudget.savingsList);
		//viewPanel.updateUI();		
		clearText();
		}
		expenseName.requestFocusInWindow();
	}
	
	/**
	 * Calls deleteEntry method, updates view panel
	 */
	public void deleteGUIEntry(){
		if(myBudget.nameList.isEmpty() == false)
		{
		myBudget.deleteEntry(a);
		a = null;
		createEntryPanel(myBudget.nameList, myBudget.amountList, myBudget.percentList, myBudget.savingsList);
		//viewPanel.updateUI();			
		}
		expenseName.requestFocusInWindow();
		
	}
	
	/**
	 * Prompts user to save and calls writeData() based on response
	 * @return integer 
	 */
	public int saveInfo(){
		int input = JOptionPane.showConfirmDialog(null, "Save Budget?", "Save", JOptionPane.YES_NO_OPTION);
		
		if(input == JOptionPane.YES_OPTION){
			try{			
				myBudget.writeData();
			 }
			catch(IOException ex){
				JOptionPane.showMessageDialog(null, "Save could not be perfomed");
			}
			catch(ClassNotFoundException ex){
				JOptionPane.showMessageDialog(null, "Class not found");
			}
		}
		return input;
	}
	
	class AddListenerClass implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			addGUIEntry();			
		}
	}
	class EnterKeyListenerClass implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
				addGUIEntry();
			}			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {			
		}
		
	}
	class DeleteListenerClass implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){		
			deleteGUIEntry();			
		}
	}
	
	class SaveListenerClass implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			saveInfo();
		}
	}	
	
	class RecalculateListenerClass implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(textPay.getText().isEmpty() == false){
				myBudget.setIncome(textPay.getText());
				myBudget.incomeRecalculate();
				createEntryPanel(myBudget.nameList, myBudget.amountList, myBudget.percentList, myBudget.savingsList);
				//viewPanel.updateUI();
			} 
			else {
				JOptionPane.showMessageDialog(null, "Please enter in a pay amount in order to recalculate");
			}
		}
	}
	class TextFieldListenerClass implements FocusListener{		

		@Override
		public void focusGained(FocusEvent e) {
			
			if(e.getSource() == expenseName){
				expenseName.setText("");
				expenseName.setForeground(Color.BLACK);
				expenseName.setFont(entries);
				
			}
			else if(e.getSource() == expenseAmount){
				expenseAmount.setText("");
				expenseAmount.setForeground(Color.BLACK);
				expenseAmount.setFont(entries);
			}			
		}
		@Override
		public void focusLost(FocusEvent arg0) {
		}	
			
	}
	
	class MyButton extends JButton{
		
		private static final long serialVersionUID = 1L;
		public MyButton(String text){
			super(text);
		}
		@Override
		public JToolTip createToolTip(){
			return (new MyCustomToolTip(this));
		}
	}
	class MyTextField extends JTextField{
		
		private static final long serialVersionUID = 1L;
		public MyTextField(int size){
			super(size);
		}
		@Override
		public JToolTip createToolTip(){
			return(new MyCustomToolTip(this));
		}
	}
	
	class MyCustomToolTip extends JToolTip{
		
		private static final long serialVersionUID = 1L;

		public MyCustomToolTip(JComponent component){
			super();
			setComponent(component);
			setBackground(Color.WHITE);
			setForeground(Color.BLACK);
		}
	}
	class EntryFocusClass implements FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			((JComponent) e.getComponent()).setOpaque(true);
			 a = e.getComponent();
			viewPanel.updateUI();		
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			((JComponent)e.getComponent()).setOpaque(false);
			viewPanel.updateUI();
		}
	}
	class MouseListenerClass implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			e.getComponent().requestFocusInWindow();			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}		
	
	class OnCloseAdapter extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e){			
			saveInfo();
			System.exit(0);
		}
	}
	
}
	
	

