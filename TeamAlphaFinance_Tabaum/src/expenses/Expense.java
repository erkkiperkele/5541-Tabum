package expenses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * @author TeamAlphaFinance
 * Superclass for all types of expenses
 * 
 */

public abstract class Expense {
    private int id;
	private String description;
	private Double amount;
	private Date date;
	private Boolean paid;
	
	SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 
	 * @return id
	 */
    public int getId() {
    	return id;
    }
    
    /**
     * 
     * @param id
     */
    public void setId(int id) {
    	this.id = id;
    }
    
    /**
     * 
     * @return description
     */
    public String getDescription() {
		return description;
	}
	
    /**
     * 
     * @param description
     */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getAmount() {
		return (Double)amount;
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getDate() {
		return date;
	}
    
    /**
     * 
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    /**
     * 
     * @return
     */
	public Boolean isPaid() {
		return (Boolean)paid;
	}
	
	/**
	 * 
	 * @param paid
	 */
	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	/**
	 * Default constructor
	 */
    public Expense(){

    }

    /**
     * Parameterized constructor
     * @param id
     * @param description
     * @param amount
     * @param date
     * @param paid
     */
    public Expense(int id, String description, double amount, Date date, boolean paid)
    {
        setId(id);
        setDescription(description);
        setAmount(amount);
        setDate(date);
        setPaid(paid);
    }
    
    /**
     * String[] constructor
     * @param line
     */
    public Expense(String[] line) {
    	setId(Integer.parseInt(line[0]));
    	setDescription(line[1]);
    	setAmount(Double.parseDouble(line[2]));
    	// Try and read the date
    	try {
			setDate(dt.parse(line[3]));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	// Since the storage format of Expense.paid is "0" or "1", this properly parses the String from line[] as an integer, and compares it to 0
    	setPaid(new Boolean(Integer.parseInt(line[4]) != 0));
    }

    /**
     * toString method
     * paid / unpaid Boolean is converted to String value 1 / 0
     * @return String with the attributes values
     * 
     */
    public String toString() {
        String paid = this.isPaid() ? "1" : "0";
        String out = this.getId() + "\t" + 
                this.getDescription() + "\t"
                + this.getAmount() + "\t"
                + dt.format(this.getDate()) + "\t"
                + paid;
        return out;
    }

	/** 
	 * @return an object array with attributes values
	 */
    public Object[] toObjectArray()
    {
        return new Object[] {
        		this,
                (Integer)this.getId(),
                (String)this.getDescription(),
                (Double)this.getAmount(),
                (String)this.dt.format(this.getDate()),
                (Boolean)this.isPaid()
        };
    }
    
    public int numberOfFields() {
    	return this.toObjectArray().length;
    }
    
    /**
     * Examines request from ExpenseTabelModel, and determines the proper setter to call
     * @param value
     * @param col
     */
    public void change(Object value, int col) {
    	if (col > 0 && col <= 5) {
    		switch(col) {
    		case 1:
    			setId((Integer) value);
    			return;
    		case 2:
    			setDescription((String) value);
    			return;
    		case 3:
    			setAmount((Double) value);
    			return;
    		case 4:
    			try {
					setDate(dt.parse((String)value));
				} catch (ParseException e) {
					e.printStackTrace();
				}
    			return;
    		case 5:
    			setPaid((Boolean) value);
    			return;
    		}
    	}
    }
}
