package com.lei.cep.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import com.lei.cep.event.PriceEvent;
import com.lei.util.MathUtil;

/**
 * Stock Trade CEP with Esper
 * 
 * <p/>See http://esper.codehaus.org/tutorials/tutorial/tutorial.html
 *
 * @author <a href=mailto:lei.lee.zou@gmail.com">stones333</a>
 * @version 1.0
 */

public class StockAvg {


	private static final Log log = LogFactory.getLog(StockAvg.class);

	public double runAvg(String name, long sleepTime, double [] arrayPrice ) throws InterruptedException {
		
		log.info("name=" + name);
		log.info("sleepTime=" + sleepTime);
		
        // Configuration
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.lei.cep.event");
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        // Creating a Statement
        String expression = "select avg(price) as AvgPrice from PriceEvent.win:time(30 sec)";
        EPStatement statement = epService.getEPAdministrator().createEPL(expression);

        // Adding a Listener
        AvgListener listener = new AvgListener();
        statement.addListener(listener);

        for (int i=0; i<arrayPrice.length; i++) {
            // Sending events
            Thread.sleep(sleepTime);
            PriceEvent event = new PriceEvent(name, arrayPrice[i]);
            epService.getEPRuntime().sendEvent(event);
        }
        
        double avg = listener.getAvgPrice();
        log.info("end of runAvg" );
        return avg;
	}

	
	public double runAvgTwo(String name, long sleepTime, double [] arrayPrice ) throws InterruptedException {
		
		log.info("name=" + name);
		log.info("sleepTime=" + sleepTime);
		
        // Configuration
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.lei.cep.event");
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        // Creating a Statement
        String expression = "select sum(price)/count(*) as AvgPrice from PriceEvent.win:time(30 sec)";
        EPStatement statement = epService.getEPAdministrator().createEPL(expression);

        // Adding a Listener
        AvgListener listener = new AvgListener();
        statement.addListener(listener);

        for (int i=0; i<arrayPrice.length; i++) {
            // Sending events
            Thread.sleep(sleepTime);
            PriceEvent event = new PriceEvent(name, arrayPrice[i]);
            epService.getEPRuntime().sendEvent(event);
        }
        
        double avg = listener.getAvgPrice();
        log.info("end of runAvg" );
        return avg;
	}

	public double runAvg(double [] arrayPrice ) throws InterruptedException {
		return runAvg("AAPL", 1000, arrayPrice );
	}
	


	 public static class AvgListener implements UpdateListener {
		 private double avgPrice;
		 
		 public void update(EventBean[] newEvents, EventBean[] oldEvents) {
	        	
			 if (newEvents == null || newEvents.length == 0) return;

			 EventBean event = newEvents[0];
			 avgPrice = event.get("AvgPrice") == null ? 0.0 :  (double)(((Double)event.get("AvgPrice")).doubleValue());
	         //System.out.println("avg=" + avgPrice);
			 log.info("avg=" + avgPrice);

		 }

		public double getAvgPrice() {
			return avgPrice;
		}
		 
		 
	}

	
	public static void main(String[] args) throws InterruptedException {

		
		 StockAvg test = new StockAvg();
		 //test.run();
		 
		 double  [] array = new double[] {527.00, 528.00, 527.50};
		 test.runAvg(array);
		 
		 double avg = MathUtil.getAvg(array);
		 log.info("avgPrice should be " + avg);

	}

}
