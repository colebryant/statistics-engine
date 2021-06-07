package camelinaction;

public interface IComponentIterator {

	public interface IIterator {

	    Component getNext();
	    boolean hasMore();
	    void reset();

	}
	
}
