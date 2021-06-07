package camelinaction;

import java.util.ArrayList;
import java.util.HashMap;

// Class which represents a stock
public class Stock extends Component {
	
    private int shares;
    
    private HashMap<String, String> statistics;
    
	
    public Stock(String name, int shares) {
        this.name = name;
        this.shares = shares;
    }

    public ComponentIterator createIterator() {
        return new ComponentIterator(this);
    }
    
    public void updateStats(HashMap<String, String> statistics) {
    	this.statistics = statistics;
    }
    
    public ArrayList<HashMap<String,String>> getStats() {
    	
    	HashMap<String,String> myStatistics = statistics;
    	// Add number of shares to statistics
    	myStatistics.put("shares", Integer.toString(shares));
    	
    	ArrayList<HashMap<String,String>> stat = new ArrayList<HashMap<String,String>>();
    	
    	stat.add(myStatistics);
    	return stat;
    }
    
    public int getShares() {
    	return shares;
    }
}
