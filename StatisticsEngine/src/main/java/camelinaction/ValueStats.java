package camelinaction;

import java.util.ArrayList;
import java.util.HashMap;

// Data class which encapsulates statistics for a specific ticker value
public class ValueStats {

	String valueName;
	ArrayList<Double> values;
	Double sum;
	
	StatisticStrategy statStrategy;
	
	Double min;
	Double max;
	
	public ValueStats(String valueName) {
		this.valueName = valueName;
		this.values = new ArrayList<Double>();
		this.sum = 0.0;
	}
	
	private void setStatStrategy(StatisticStrategy statStrategy) {
		this.statStrategy = statStrategy;
	}
	
	public HashMap<String, String> updateStats(String tickerValue) {
		
		Double value = Double.parseDouble(tickerValue);

		// Update history of values
		this.values.add(value);
		// Update sum
		this.sum += value;
		
		// Update min
		if (min == null) {
			min = value;
		} else if (value < min) {
			min = value;
		}
		
		// Update max
		if (max == null) {
			max = value;
		} else if (value > max) {
			max = value;
		}
		
		// Calculate average
		this.setStatStrategy(new Average());
		Double average = statStrategy.execute(sum, values);
		
		// Calculate simple moving average
		this.setStatStrategy(new SimpleMovingAverage());
		Double sma = statStrategy.execute(sum, values);
		
		// Calculate variance
		this.setStatStrategy(new Variance());
		Double variance = statStrategy.execute(sum, values);
		
		// Calculate standard deviation
		this.setStatStrategy(new StandardDeviation());
		Double std = statStrategy.execute(sum, values);
		
		
		// Build hash map of statistics
		HashMap<String, String> statsMap = new HashMap<String, String>();
		
		statsMap.put(valueName + "Min", min.toString());
		statsMap.put(valueName + "Max", max.toString());
		statsMap.put(valueName + "Average", average.toString());
		statsMap.put(valueName + "SMA", sma.toString());
		statsMap.put(valueName + "Variance", variance.toString());
		statsMap.put(valueName + "STD", std.toString());
		
		return statsMap;
	}
	
}
