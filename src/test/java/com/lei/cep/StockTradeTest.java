package com.lei.cep;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lei.cep.main.StockTrade;
import junit.framework.TestCase;

/**
 * Stock Trade CEP with Esper
 * 
 * <p/>See http://esper.codehaus.org/tutorials/tutorial/tutorial.html
 *
 * @author <a href=mailto:lei.lee.zou@gmail.com">stones333</a>
 * @version 1.0
 */

public class StockTradeTest extends TestCase { 
	private static final Log log = LogFactory.getLog(StockTrade.class);
	
	public void testStockTradeNotBuy() {
		
		log.info("start testStockTradeNotBuy() ");
		StockTrade test = new StockTrade();
		double  [] arrayPrice = new double[] {100.00, 99.00, 100.50, 
				101.01, 100.00, 102.01, 101.00, 99.50, 100, 101, 102, 
				100, 99.99, 98.87,  
				93.99, 95.09, 90.99, 89.8, 93.9, };
		boolean buy = false;
		try {
			buy = test.runStockTrade("GOOG", 1000, arrayPrice, 90.00, 10.00);
		} catch (InterruptedException e) {
			e.printStackTrace();
			buy = true;
		}
		
		log.info("end testStockTradeNotBuy() buy GOOG? " + ( buy ? "yes" : "no") );
		assertTrue( buy==false );
	}


	public void testStockTradeBuy() {
		
		log.info("start testStockTradeBuy() ");
		
		StockTrade test = new StockTrade();
		double  [] arrayPrice = new double[] {100.00, 99.00, 100.50, 
				106.01, 103.00, 107.01, 107.00, 108.50, 109, 105,
				105.01, 102.00, 103.91, 105.00, 107.50, 102, 101,
				104.01, 101.00, 102.91, 101.00, 99.50, 106, 101,
				102, 100, 98, 89, 93, };
		boolean buy = false;
		try {
			buy = test.runStockTrade("GOOG", 1000, arrayPrice, 90.00, 10.00);
		} catch (InterruptedException e) {
			e.printStackTrace();
			buy = false;
		}
		log.info("end testStockTradeBuy() buy GOOG? " + ( buy ? "yes" : "no") );
		assertTrue( buy==true );
	}


}
