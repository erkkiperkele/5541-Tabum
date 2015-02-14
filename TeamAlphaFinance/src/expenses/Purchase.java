package expenses;


import java.text.ParseException;
import java.util.Date;

/**
 * @author TeamAlphaFinance
 * Class derived from Expense to store records of Purchase type
 */

//WARNING: Need to be manually kept in sync with the constant in StringToClass responsible for deserializing text files!
public class Purchase extends Expense {
	
	Date dueDate;

	/**
	 * Parameterized constructor
	 * @param id
	 * @param description
	 * @param amount
	 * @param date
	 * @param paid
	 * @param dueDate
	 */
	public Purchase(int id, String description, double amount, Date date, boolean paid, Date dueDate){
		super(id, description, amount, date, paid);
		setDueDate(dueDate);
	}
	
	/**
	 * String[] constructor
	 * @param line
	 */
	public Purchase(String[] line) {
		super(line);
		// Try and read DueDate
		try {
			setDueDate(super.dt.parse(line[5]));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

    //Need to create an empty purchase if we want to be able to display an empty line that the user can edit...
	/**
	 * Default constructor
	 */
    public Purchase() {

    }

    /**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	 /**
     * toString method
     * @return String with the purchase attributes values
     */
	public String toString() {
		return super.toString() + "\t" + super.dt.format(this.getDueDate());
	}

	/**
	 * @return an object array with attributes values 
	 */
    public Object[] toObjectArray()
    {
        return new Object[] {
        		this,
                (Object)this.getId(),
                (Object)this.getDescription(),
                (Object)this.getAmount(),
                (Object)super.dt.format(this.getDate()),
                (Object)this.isPaid(),
                (Object)super.dt.format(this.getDueDate()),
        };
    }
    
    public void change(Object val, int col) {
    	if (col <= 5) super.change(val, col);
    	else {
    		try {
				setDueDate(super.dt.parse((String)val));
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    }
}
