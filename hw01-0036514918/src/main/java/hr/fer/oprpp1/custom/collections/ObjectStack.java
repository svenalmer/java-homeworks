package hr.fer.oprpp1.custom.collections;

public class ObjectStack {
	
	private ArrayIndexedCollection col;
	
	public ObjectStack() {
		col = new ArrayIndexedCollection();
	}
	
	public boolean isEmpty() {
		return col.size() == 0;
	}
	
	public int size() {
		return col.size();
	}
	
	public void push(Object value) {
		col.add(value);
	}
	
	public Object peek() {
		return col.get(col.size() - 1);
	}
	
	public Object pop() {
		Object item = peek();
		col.remove(col.size() - 1);
		return item;
	}
	
	public void clear() {
		col.clear();
	}
	
}
