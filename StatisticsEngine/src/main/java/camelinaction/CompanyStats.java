package camelinaction;

import java.util.HashMap;

// Collection of stock ticker statistics for specific company
public class CompanyStats {
	
	String stock;
	ValueStats bidPriceStats;
	ValueStats bidQuantityStats;
	ValueStats askPriceStats;
	ValueStats askQuantityStats;
	
	public CompanyStats(String stock) {
		this.stock = stock;
		this.bidPriceStats = new ValueStats("bidPrice");
		this.bidQuantityStats = new ValueStats("bidQuantity");
		this.askPriceStats = new ValueStats("askPrice");
		this.askQuantityStats = new ValueStats("askQuantity");
	}
	
	public HashMap<String, String> updateStats(HashMap<String, String> ticker) {
		
		// Retrieve ticker values
		String bidPrice = ticker.get("bidPrice");
		String bidQuantity = ticker.get("bidQuantity");
		String askPrice = ticker.get("askPrice");
		String askQuantity = ticker.get("askQuantity");
		
		// Build new hash map of ticker values + relevant statistics
		HashMap<String, String> companyStatsMap = new HashMap<String, String>();
		companyStatsMap.put("stock", this.stock);
		companyStatsMap.put("bidPrice", bidPrice);
		companyStatsMap.put("bidQuantity", bidQuantity);
		companyStatsMap.put("askPrice", askPrice);
		companyStatsMap.put("askQuantity", askQuantity);
				
		// Update & return relevant statistics, add to hash map
		companyStatsMap.putAll(this.bidPriceStats.updateStats(bidPrice));
		companyStatsMap.putAll(this.bidQuantityStats.updateStats(bidQuantity));
		companyStatsMap.putAll(this.askPriceStats.updateStats(askPrice));
		companyStatsMap.putAll(this.askQuantityStats.updateStats(askQuantity));
			
		return companyStatsMap;
	}

}
