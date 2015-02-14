package expenses;

import java.util.Date;

/**
 * @author TeamAlphaFinance
 * Class derived from Expense to store records of Bill type
 */
public class Bill extends Expense {
	
	String repetitionInterval;

	/**
	 * Parameterized constructor
	 * @param id
	 * @param description
	 * @param amount
	 * @param dueDate
	 * @param paid
	 * @param repetitionInterval
	 */
	public Bill(int id, String description, double amount, Date dueDate, boolean paid, String repetitionInterval){
        super(id, description, amount, dueDate, paid);
		setRepetitionInterval(repetitionInterval);
	}
	
	/**
	 * String[] constructor
	 * @param obj
	 */
	public Bill(String[] line) {
		super(line);
		setRepetitionInterval(line[5]);
	}

	/**
	 * Default constructor
	 */
    public Bill() {

    }

    /**
	 * @return the repetitionInterval
	 */
	public String getRepetitionInterval() {
		return repetitionInterval;
	}

	/**
	 * @param repetitionInterval the repetitionInterval to set
	 */
	public void setRepetitionInterval(String repetitionInterval) {
		this.repetitionInterval = repetitionInterval;
	}
	
	 /**
     * toString method
     * @return String with the bill attributes values
     */
	public String toString() {
		return super.toString() + "\t" + getRepetitionInterval();
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
                (String)super.dt.format(this.getDate()),
                (Boolean)this.isPaid(),
                (String)this.getRepetitionInterval()
        };
    }
    
    public void change(Object val, int col) {
    	if (col <= 5) super.change(val, col);
    	else {
    		setRepetitionInterval((String) val);
    	}
    }
}
