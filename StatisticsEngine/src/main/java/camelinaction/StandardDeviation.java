package camelinaction;

import java.util.ArrayList;

import java.lang.Math;

//Concrete strategy to calculate standard deviation. Note that since this is population std,
//dividing by n instead of n - 1. Standard deviation returned as 0 for only 1 observation
public class StandardDeviation extends StatisticStrategy {
	
	public Double transform(Double average, ArrayList<Double> values) {
		
		Double diffSum = 0.0;
		
		int size = values.size();
		
		for (int i = 0; i < size; i++) {
			diffSum += Math.pow(values.get(i) - average, 2);
		}
		
		Double variance = diffSum / size;
		
		return Math.sqrt(variance);
	}
}
