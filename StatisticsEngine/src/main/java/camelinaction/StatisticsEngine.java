package camelinaction;

import java.util.HashMap;

// Class which encapsulates statistics calculation on incoming stock tickers
public class StatisticsEngine {
	
	// Make SINGLETON
	private static StatisticsEngine single_instance = null;
	
	CompanyStats MSFT_Stats;
	CompanyStats ORCL_Stats;
	CompanyStats IBM_Stats;
	
	private StatisticsEngine() {
		this.MSFT_Stats = new CompanyStats("MSFT");
		this.ORCL_Stats = new CompanyStats("ORCL");
		this.IBM_Stats = new CompanyStats("IBM");
	}
	
	public static StatisticsEngine getInstance() {
        if (single_instance == null)
            single_instance = new StatisticsEngine();
  
        return single_instance;
	}
	
	public HashMap<String, String> updateStats(HashMap<String, String> ticker) {
		if (ticker.get("stock").equals("MSFT")) {
			return MSFT_Stats.updateStats(ticker);
		} else if (ticker.get("stock").equals("ORCL")) {
			return ORCL_Stats.updateStats(ticker);
		} else {
			return IBM_Stats.updateStats(ticker);
		}
	}
	
}
