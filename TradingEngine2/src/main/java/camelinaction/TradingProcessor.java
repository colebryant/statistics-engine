package camelinaction;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class TradingProcessor implements Processor {
	
	TradingEngine tradeEngine;
	
	public TradingProcessor(TradingEngine tradeEngine) {
		this.tradeEngine = tradeEngine;
	}
	
	public void process (Exchange e) throws Exception {
		
		HashMap<String, String> statsMap = e.getIn().getBody(HashMap.class);
		
		tradeEngine.updateStats(statsMap);
		
		tradeEngine.report();
	}
	
}