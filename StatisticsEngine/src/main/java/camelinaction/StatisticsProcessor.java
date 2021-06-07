package camelinaction;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

// Processor class for processing statistics from ticker data
public class StatisticsProcessor implements Processor {
	
	StatisticsEngine statEngine;
	
	public StatisticsProcessor (StatisticsEngine statEngine) {
		this.statEngine = statEngine;
	}
	
	public void process(Exchange e) throws Exception {
		HashMap<String, String> messageBody = e.getIn().getBody(HashMap.class);
		
		HashMap<String, String> statMap = statEngine.updateStats(messageBody);
		
		e.getIn().setBody(statMap);
		
	}

}
