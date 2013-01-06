package com.lei.cep.event;

/**
 * Stock Trade CEP with Esper
 * 
 * <p/>See http://esper.codehaus.org/tutorials/tutorial/tutorial.html
 *
 * @author <a href=mailto:lei.lee.zou@gmail.com">stones333</a>
 * @version 1.0
 */

public class PriceEvent {

	private String symbol;
    private double price;
	
    
    public PriceEvent(String symbol, double price) {
		super();
		this.symbol = symbol;
		this.price = price;
	}

	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }

	public String getSymbol() { return symbol; }
	public void setSymbol(String symbol) { this.symbol = symbol; }

	@Override
	public String toString() {
		return "PriceEvent [symbol=" + symbol + ", price=" + price + "]";
	}
    
    
}
