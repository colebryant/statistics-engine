package camelinaction;

import java.util.ArrayList;
import java.util.HashMap;

public class Portfolio extends Component {
	
    Stock rootStock;

    public Portfolio(String name, Stock rootStock) {
    	this.name = name;
        this.rootStock = rootStock;
    }

    public ComponentIterator createIterator() {
        return new ComponentIterator(this);
    }
    
    public void updateStats(HashMap<String, String> statsMap) {
    	
        ComponentIterator iterator = rootStock.createIterator();
        
        for (Component c = iterator.rootComponent; iterator.hasMore(); c = iterator.getNext()) {
        	if (c.getName().equals(statsMap.get("stock"))) {
                c.updateStats(statsMap);	
        	}
        }	
    }
    
    public ArrayList<HashMap<String,String>> getStats() {
    	
        ComponentIterator iterator = rootStock.createIterator();
    	
    	ArrayList<HashMap<String,String>> stats = new ArrayList<HashMap<String,String>>();
    	
        for (Component c = iterator.rootComponent; iterator.hasMore(); c = iterator.getNext()) {
        	stats.addAll(c.getStats());
        }
        
        return stats;
    }

}
