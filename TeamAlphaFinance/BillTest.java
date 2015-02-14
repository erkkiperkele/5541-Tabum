package expenses;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

public class BillTest {
	Calendar cal = new GregorianCalendar(2015,05,12);
	Date dt = cal.getTime();
	Bill bill = new Bill(3, "test bill", 155.0, dt, true, "weekly");
	
	@Test
	public void testToString() {
		assertEquals("3:test bill:155.0:05/12/2015:Paid:weekly", bill.toString());
	}


	@Test
	public void testGetRepetitionInterval() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetRepetitionInterval() {
		fail("Not yet implemented");
	}

}