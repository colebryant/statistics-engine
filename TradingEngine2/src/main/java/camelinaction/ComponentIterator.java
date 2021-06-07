package camelinaction;

public class ComponentIterator {
	
    Component rootComponent;
    Component currentPosition;

    public ComponentIterator(Component component) {
        this.rootComponent = component;
        this.currentPosition = component;
    }

    public Component getNext() {
        this.currentPosition = currentPosition.next;
        return currentPosition;
    }

    public boolean hasMore() {
        return currentPosition != null;
    }

    public void reset() {
        this.currentPosition = rootComponent;
    }

}
