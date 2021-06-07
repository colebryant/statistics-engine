package camelinaction;

import java.util.HashMap;

public class TradingEngine {
	
	private Component rootComponent;
	private ReportingEngine reportingEngine;
	
	public TradingEngine(Component rootComponent) {
		this.rootComponent = rootComponent;
		this.reportingEngine = ReportingEngine.getInstance();
	}
	
	public void updateStats(HashMap<String, String> statsMap) {
		
        ComponentIterator iterator = rootComponent.createIterator();
        
        for (Component c = iterator.rootComponent; iterator.hasMore(); c = iterator.getNext()) {
            c.updateStats(statsMap);
        }
	}
	
	public void report() {
		reportingEngine.report(rootComponent);
	}

}
