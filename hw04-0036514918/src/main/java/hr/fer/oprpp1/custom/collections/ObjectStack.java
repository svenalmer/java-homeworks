package hr.fer.oprpp1.custom.collections;

public class ObjectStack<T> {
	
	private ArrayIndexedCollection<T> col;
	
	public ObjectStack() {
		col = new ArrayIndexedCollection<T>();
	}
	
	public boolean isEmpty() {
		return col.size() == 0;
	}
	
	public int size() {
		return col.size();
	}
	
	public void push(T value) {
		col.add(value);
	}
	
	public T peek() {
		return col.get(col.size() - 1);
	}
	
	@SuppressWarnings("unchecked")
	public T pop() {
		Object item = peek();
		col.remove(col.size() - 1);
		return (T) item;
	}
	
	public void clear() {
		col.clear();
	}
	
}
