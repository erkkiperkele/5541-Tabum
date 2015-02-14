package gui;

import java.awt.*; 

import javax.swing.*; 

import java.util.*; 
import java.awt.event.*; 

import javax.swing.event.*; 
import javax.swing.filechooser.*; 
import javax.swing.table.*;

import expenses.*;
import models.ExpenseModel;

public class MainWindow {
	private ExpenseModel model;
	
	private JTable expense_table;
	private JTable purchase_table;
	private JTable bill_table;
	
	JFrame frame;
	
	/**
	 * Launch the application
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MainWindow() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Create frame and tabbed pane
		frame = new JFrame("Alpha Finance Manager");
		
		frame.setMinimumSize(new Dimension(650, 320));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 630, 250);
        tabbedPane.setBackground(Color.white);
		frame.getContentPane().add(tabbedPane);
		
		// Initialize model and JTables
		model = new ExpenseModel();
		
		expense_table = new JTable(model);
		purchase_table = new JTable(model);
		bill_table = new JTable(model);

		// Set selection modes for tables
		expense_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		purchase_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bill_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// Hide first column in each table (col 0 contains a pointer to the object it represents)
		expense_table.removeColumn(expense_table.getColumnModel().getColumn(0));
		purchase_table.removeColumn(purchase_table.getColumnModel().getColumn(0));
		bill_table.removeColumn(bill_table.getColumnModel().getColumn(0));
		
		// Show the last columns for purchase and bill tables
		purchase_table.addColumn(new TableColumn(6));
		bill_table.addColumn(new TableColumn(6));
//		expense_table.addColumn(new TableColumn(6));
//		expense_table.removeColumn(expense_table.getColumnModel().getColumn(6));
		
		// Create and assign filters for purchase_table and expense_table
		RowFilter<ExpenseModel, Integer> purchase_filter = new RowFilter<ExpenseModel, Integer>() {
			public boolean include(Entry<? extends ExpenseModel, ? extends Integer> entry) {
				ExpenseModel m = entry.getModel();
				// Get pointer to instance in Object[0]
				Expense e = (Expense)m.getExpense(entry.getIdentifier());
				return (e instanceof Purchase);
			}
		};
		
		RowFilter<ExpenseModel, Integer> bill_filter = new RowFilter<ExpenseModel, Integer>() {
			public boolean include(Entry<? extends ExpenseModel, ? extends Integer> entry) {
				ExpenseModel m = entry.getModel();
				// Get pointer to instance in Object[0]
				Expense e = (Expense)m.getExpense(entry.getIdentifier());
				return (e instanceof Bill);
			}
		};
		
		TableRowSorter<ExpenseModel> purchase_sorter = new TableRowSorter<ExpenseModel>(model);
		purchase_sorter.setRowFilter(purchase_filter);
		purchase_table.setRowSorter(purchase_sorter);
		
		TableRowSorter<ExpenseModel> bill_sorter = new TableRowSorter<ExpenseModel>(model);
		bill_sorter.setRowFilter(bill_filter);
		bill_table.setRowSorter(bill_sorter);
		
		tabbedPane.addTab("Expenses", null, expense_table, null);
		tabbedPane.addTab("Purchases", null, purchase_table, null);
		tabbedPane.addTab("Bills", null, bill_table, null);
		tabbedPane.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				int sel = tabbedPane.getSelectedIndex();
			}
		});
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBounds(10, 260, 300, 30);
		frame.getContentPane().add(buttonsPanel);
		buttonsPanel.setLayout(new GridLayout(0, 5, 0, 0));
		
		JButton add_button = new JButton("+");
        add_button.setForeground(Color.green);
		buttonsPanel.add(add_button);
		add_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Get active table
				JTable active_table = (JTable)tabbedPane.getSelectedComponent();
				ExpenseModel model = (ExpenseModel)active_table.getModel();
				// Determine type of expense to create based on active tab
				String title = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
				// I don't know how to handle adding in the "Expenses" tab -- Richard
				if (title == "Purchases") model.addPurchase();
				else if (title == "Bills") model.addBill();
			}
		});
		
		JButton remove_button = new JButton("-");
        add_button.setForeground(Color.red);
        buttonsPanel.add(remove_button);
		remove_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Get active table and model
				JTable active_table = (JTable)tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
				ExpenseModel model = (ExpenseModel)active_table.getModel();
				
				// Get tab title
				String title = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
				
				// Get selected row
				int row = active_table.getSelectedRow();
				
				// Check if a row is selected in visible table
				if (row != -1) {
					// This was tricky. active_table.getSelectedRow() gets the selected row index in the JTable.
					// However, this does not reflect our model data, as the Puchases and Bills JTables are filtered.
					// getXXX searches the appropriate ArrayList, which should have the same order as displayed on the table
					if (title == "Purchases") model.removeDatum(model.getPurchase(row));
					else if (title == "Bills") model.removeDatum(model.getBill(row));
					else if (title == "Expenses") model.removeDatum(model.getExpense(row));
				}
			}
		});
		
		JButton save_button = new JButton("Save");
		buttonsPanel.add(save_button);
		save_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.writeToFiles();
			}
		});
	}
}
