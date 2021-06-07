package camelinaction;

import java.util.ArrayList;

// Concrete strategy to calculate average
public class Average extends StatisticStrategy {
	
	public Double transform(Double average, ArrayList<Double> values) {
		return average;
	}
}
