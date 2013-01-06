package com.lei.cep;

import com.lei.cep.main.StockAvg;
import com.lei.util.MathUtil;

import junit.framework.TestCase;

/**
 * Stock Trade CEP with Esper
 * 
 * <p/>See http://esper.codehaus.org/tutorials/tutorial/tutorial.html
 *
 * @author <a href=mailto:lei.lee.zou@gmail.com">stones333</a>
 * @version 1.0
 */

public class StockAvgTest extends TestCase {

	public void testStockAvg() {
		
		StockAvg test = new StockAvg();
		double  [] array = new double[] {527.00, 528.00, 527.50};
		double avg = 0.0;
		try {
			avg = test.runAvg(array);
		} catch (InterruptedException e) {
			e.printStackTrace();
			avg = 0.0;
		}

		double avg2 = MathUtil.getAvg(array);
		assertTrue( avg==avg2 );
	}
	
	
	public void testStockAvgTwo() {
		
		StockAvg test = new StockAvg();
		double  [] array = new double[] {600.00, 599.00, 600.50, 601.01, 600.00, 602.01, 601.00, 599.50};
		double avg = 0.0;
		try {
			avg = test.runAvg("GOOG", 2000, array);
		} catch (InterruptedException e) {
			e.printStackTrace();
			avg = 0.0;
		}

		double avg2 = MathUtil.getAvg(array);
		assertTrue( avg==avg2 );
	}

	public void testStockAvgThree() {
		
		StockAvg test = new StockAvg();
		double  [] array = new double[] {600.00, 599.00, 600.50, 601.01, 600.00, 602.01, 601.00, 599.50};
		double avg = 0.0;
		try {
			avg = test.runAvgTwo("GOOG", 2000, array);
		} catch (InterruptedException e) {
			e.printStackTrace();
			avg = 0.0;
		}

		double avg2 = MathUtil.getAvg(array);
		assertTrue( avg==avg2 );
	}

}
