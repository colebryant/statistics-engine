package camelinaction;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Component {
	
	String name;
    Component next;

    public void append(Component newComponent) {
        Component current = this;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newComponent;
    }
    
    public String getName() {
    	return name;
    }

    public abstract ComponentIterator createIterator();
    
    public abstract void updateStats(HashMap<String, String> statsMap);
    
    public abstract ArrayList<HashMap<String,String>> getStats();
}
