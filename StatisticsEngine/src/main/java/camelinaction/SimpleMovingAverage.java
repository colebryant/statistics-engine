package camelinaction;

import java.util.ArrayList;

// Concrete strategy to calculate simple moving average with n = 10
public class SimpleMovingAverage extends StatisticStrategy {
	
	int n = 10;
	
	public Double calculateAverage(Double sum, ArrayList<Double> values) {
		
		int size = Math.min(n, values.size());
	
		Double lastNSum = 0.0;
		
		for(int i = 0; i < size; i++) {
			lastNSum += values.get(size - i - 1);
		}
		
		return lastNSum / size;
	}
	
	public Double transform(Double average, ArrayList<Double> values) {
		return average;
	}

}
