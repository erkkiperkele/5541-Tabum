package models;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.lang.StringBuilder;

import javax.swing.table.AbstractTableModel;

//import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringUtils;

import expenses.*;

public class ExpenseModel extends AbstractTableModel {
	
	// Data for JTable: An ArrayList<Expense> and a String[] for column names
	ArrayList<Expense> expenses;
	ArrayList<Purchase> purchases;
	ArrayList<Bill> bills;
	
	// Will change when user changes tabs
	ArrayList<Expense> active_data_set;
	
	String[] purchase_cols;
	String[] bill_cols;
	
	// Will change when user changes tabs
	String[] active_cols;

	// Data for the model
	// TODO: Have reading files alter this value
	// TOOD: Implement adding functionality using this
	int max_purchase_id;
	int max_bill_id;
	
	
	public ExpenseModel() {
		super();
		expenses = new ArrayList<Expense>();
		purchases = new ArrayList<Purchase>();
		bills = new ArrayList<Bill>();
		max_purchase_id = max_bill_id = 0;
		readPurchasesFromFile();
		readBillsFromFile();
		active_cols = purchase_cols;
	}
	
	private void readPurchasesFromFile() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("Purchase.txt")));
			
			// Read the column names
			purchase_cols = bufferedReader.readLine().split("\t");
			
			// Read the rows
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] split_line = line.split("\t");
				Purchase new_purchase = new Purchase(split_line);
				int purch_id = new_purchase.getId();
				if (purch_id > max_purchase_id) max_purchase_id = purch_id; 
				purchases.add(new_purchase);
				expenses.add(new_purchase);
			}
			bufferedReader.close();
			// Notify listeners
			fireTableStructureChanged();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writePurchasesToFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(new File("Purchase.txt")));
			
			// Write the column names
			out.write(StringUtils.join(purchase_cols, "\t"));
			out.write("\n");
			
			// Write the purchases
			for (int i = 0; i < purchases.size(); i++) {
				out.write(purchases.get(i).toString());
				out.write("\n");
			}
			
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readBillsFromFile() {
		try {
			FileReader fileReader = new FileReader(new File("Bill.txt"));
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			// Read the column names
			bill_cols = bufferedReader.readLine().split("\t");
			
			// Read the rows
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] split_line = line.split("\t");
				Bill new_bill = new Bill(split_line);
				int bill_id = new_bill.getId();
				if (bill_id > max_bill_id) max_bill_id = bill_id;
				bills.add(new_bill);
				expenses.add(new_bill);
			}
			bufferedReader.close();
			// Notify listeners
			fireTableStructureChanged();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeBillsToFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(new File("Bill.txt")));
			
			// Write the column names
			out.write(StringUtils.join(bill_cols, "\t"));
			out.write("\n");
			
			// Write the purchases
			for (int i = 0; i < bills.size(); i++) {
				out.write(bills.get(i).toString());
				out.write("\n");
			}
			
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeToFiles() {
		writePurchasesToFile();
		writeBillsToFile();
	}
	
	public void readFromFiles() {
		readPurchasesFromFile();
		readBillsFromFile();
	}

	// Required to implement AbstractTableModel
	@Override
	public int getColumnCount() {
		return active_cols.length;
	}

	@Override
	public Class<?> getColumnClass(int column) {
	    return getValueAt(0, column).getClass();
	}

	@Override
	public int getRowCount() {
		return expenses.size();
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex <= 1) return false;
		else return true;
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Expense datum = (Expense)getExpense(rowIndex);
		Object[] oa = datum.toObjectArray();
		// Cast expense to the proper type, then call toObjectArray(), and reference an index
		return oa[columnIndex];
	}

	public void setValueAt(Object value, int row, int col) {
		// This should work because the getters of editable fields in Expenses return Objects, not primitives
		// Because of this privacy leaking, changing a cell in the table should automatically change the instance
		// Get an Object[] containing pointers to the Expense's fields:
		expenses.get(row).change(value, col);
		
		// Notify listeners
//		fireTableCellUpdated(row, col);
	}

	public Expense getExpense(int index) {
		return expenses.get(index);
	}
	
	public Purchase getPurchase(int index) {
		return purchases.get(index);
	}
	
	public Bill getBill(int index) {
		return bills.get(index);
	}
	
	public void removeDatum(Expense e) {
		// Get row where e was stored
		int row = expenses.indexOf(e);
		// Try and remove it from all ArrayLists
		expenses.remove(e);
		if (e instanceof Purchase) purchases.remove(e);
		else if (e instanceof Bill) bills.remove(e);
		fireTableRowsDeleted(row, row);
	}
	
	public void addPurchase() {
		// Add a generic purchase to the table
		Purchase new_purchase = new Purchase(++max_purchase_id, "New Purchase", 0.0, new Date(), false, new Date());
		expenses.add(new_purchase);
		purchases.add(new_purchase);
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}
	
	public void addBill() {
		// Add a generic bill to the table
		Bill new_bill = new Bill(++max_bill_id, "New Bill", 0.0, new Date(), false, "Monthly");
		expenses.add(new_bill);
		bills.add(new_bill);
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}
}
