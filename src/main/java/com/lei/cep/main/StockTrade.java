package com.lei.cep.main;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.lei.cep.event.PriceEvent;

/**
 * Stock Trade CEP with Esper
 * 
 * <p/>See http://esper.codehaus.org/tutorials/tutorial/tutorial.html
 *
 * @author <a href=mailto:lei.lee.zou@gmail.com">stones333</a>
 * @version 1.0
 */

public class StockTrade {

	private static final Log log = LogFactory.getLog(StockTrade.class);

	 public static class StockTradeListener implements UpdateListener {
		 private double avgPrice=-1.0;
		 private double minPrice=-1.0;
		 private double targetPrice=0.0;
		 private double dropPercetage=0.0;
		 private boolean buyNow=false;
		 
		 public void update(EventBean[] newEvents, EventBean[] oldEvents) {
	        	
			 if (newEvents == null || newEvents.length == 0) return;

			 EventBean event = newEvents[0];
			 avgPrice = event.get("AvgPrice") == null ? 0.0 :  (double)(((Double)event.get("AvgPrice")).doubleValue());
			 minPrice = event.get("MinPrice") == null ? 0.0 :  (double)(((Double)event.get("MinPrice")).doubleValue());
			 
			 if (avgPrice>0 && minPrice>0) {
				 log.info("minPrice=" + minPrice + " avgPrice=" + avgPrice);
				 if (minPrice<=targetPrice && minPrice<= (avgPrice* (100.00-dropPercetage) /100.00  ) ) {
					 buyNow=true;
					 log.info("buyNow=" + buyNow);
				 }
			 }
		 }

		 public StockTradeListener(double targetPrice, double dropPercetage) {
			super();
			this.targetPrice = targetPrice;
			this.dropPercetage = dropPercetage;
		}

		public boolean isBuyNow() { return buyNow; }
	}

	public boolean runStockTrade(String symbal, long interval, 
			double [] arrayPrice, double targetPrice, double dropPercetage ) throws InterruptedException {


		log.info("symbal=" + symbal);
		log.info("interval=" + interval);
		log.info("targetPrice=" + targetPrice);
		log.info("percetageDrop=" + dropPercetage);
		log.info("arrayPrice=" + Arrays.toString(arrayPrice));
		
        // Configuration
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.lei.cep.event");
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        // Creating a Statement
        String expression = "select avg(price) as AvgPrice, min(price) as MinPrice from PriceEvent.win:time(30 sec)";
        EPStatement statement = epService.getEPAdministrator().createEPL(expression);

        // Adding a Listener
        StockTradeListener listener = new StockTradeListener(targetPrice, dropPercetage);
        statement.addListener(listener);

        for (int i=0; i<arrayPrice.length; i++) {
            // Sending events
            Thread.sleep(interval);
            PriceEvent event = new PriceEvent(symbal, arrayPrice[i]);
            epService.getEPRuntime().sendEvent(event);
            if ( listener.isBuyNow() ) return true; 
        }

		return false;
	}

}
