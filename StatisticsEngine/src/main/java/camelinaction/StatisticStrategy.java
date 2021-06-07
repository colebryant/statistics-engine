package camelinaction;

import java.util.ArrayList;

// STRATEGY / TEMPLATE METHOD: Abstract base class for statistic calculating strategies.
// Includes template methods
public abstract class StatisticStrategy {
	
	public Double execute(Double sum, ArrayList<Double> values) {
		Double average = calculateAverage(sum, values);
		Double answer = transform(average, values);
		// Round up answer to 2 decimal places
		return Math.round(answer * 100.0) / 100.0;
	}
	
	public Double calculateAverage(Double sum, ArrayList<Double> values) {
		return sum / values.size();
	}
	
	public abstract Double transform(Double average, ArrayList<Double> values);
	
}
