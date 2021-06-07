package camelinaction;

import java.util.ArrayList;
import java.util.HashMap;


// Class responsible for reporting on statistics of stocks in trading engine's portfolio(s)
// Prints to standard output
public class ReportingEngine {
	
	// SINGLETON
	private static ReportingEngine single_instance = null;
	
	public static ReportingEngine getInstance() {
        if (single_instance == null)
            single_instance = new ReportingEngine();
  
        return single_instance;
	}
	
	public void report(Component rootComponent) {
		
		System.out.println("================================");
		System.out.println("--------Reporting Engine--------");
		System.out.println("================================");
		
        ComponentIterator iterator = rootComponent.createIterator();
        
        for (Component c = iterator.rootComponent; iterator.hasMore(); c = iterator.getNext()) {
            System.out.println(c.getName());
            System.out.println("================================");
            
            ArrayList<HashMap<String, String>> stats = c.getStats();
            
            int size = stats.size();
            
            for (int i = 0; i < size; i++) {
            	
            	HashMap<String, String> stat = stats.get(i);
            	
            	System.out.println(stat.get("stock") + " Statistics:");
                System.out.println("--------------------------------");
                System.out.println("Number of shares: " + stat.get("shares"));
            	
                System.out.println("-----------BID PRICE------------");
                System.out.println("Most recent: $" + stat.get("bidPrice"));
                System.out.println("Minimum: $" + stat.get("bidPriceMin"));
                System.out.println("Maximum: $" + stat.get("bidPriceMax"));
                System.out.println("Average: $" + stat.get("bidPriceAverage"));
                System.out.println("Simple moving average (n=10): $" + stat.get("bidPriceSMA"));
                System.out.println("Variance: " + stat.get("bidPriceVariance"));
                System.out.println("Standard Deviation: " + stat.get("bidPriceSTD"));
                
                System.out.println("----------BID QUANTITY----------");
                System.out.println("Most recent: " + stat.get("bidQuantity"));
                System.out.println("Minimum: " + stat.get("bidQuantityMin"));
                System.out.println("Maximum: " + stat.get("bidQuantityMax"));
                System.out.println("Average: " + stat.get("bidQuantityAverage"));
                System.out.println("Simple moving average (n=10): " + stat.get("bidQuantitySMA"));
                System.out.println("Variance: " + stat.get("bidQuantityVariance"));
                System.out.println("Standard Deviation: " + stat.get("bidQuantitySTD"));
                
                System.out.println("-----------ASK PRICE------------");
                System.out.println("Most recent: $" + stat.get("askPrice"));
                System.out.println("Minimum: $" + stat.get("askPriceMin"));
                System.out.println("Maximum: $" + stat.get("askPriceMax"));
                System.out.println("Average: $" + stat.get("askPriceAverage"));
                System.out.println("Simple moving average (n=10): $" + stat.get("askPriceSMA"));
                System.out.println("Variance: " + stat.get("askPriceVariance"));
                System.out.println("Standard Deviation: " + stat.get("askPriceSTD"));
                
                System.out.println("----------ASK QUANTITY----------");
                System.out.println("Most recent: " + stat.get("askQuantity"));
                System.out.println("Minimum: " + stat.get("askQuantityMin"));
                System.out.println("Maximum: " + stat.get("askQuantityMax"));
                System.out.println("Average: " + stat.get("askQuantityAverage"));
                System.out.println("Simple moving average (n=10): " + stat.get("askQuantitySMA"));
                System.out.println("Variance: " + stat.get("askQuantityVariance"));
                System.out.println("Standard Deviation: " + stat.get("askQuantitySTD"));
                
                System.out.println("================================");            	
            }
        }
	}

}
