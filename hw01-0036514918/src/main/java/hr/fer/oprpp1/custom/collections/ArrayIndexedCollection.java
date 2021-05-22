package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * A collection implemented using an array.
 */
public class ArrayIndexedCollection extends Collection {
	
	/** The size. */
	private int size = 0;
	
	/** The elements. */
	private Object[] elements;
	
	/** The Constant DEFAULT_CAPACITY. */
	private final static int DEFAULT_CAPACITY = 16;

	/**
	 * Instantiates a new <code>ArrayIndexedCollection</code> with the given capacity.
	 *
	 * @param initialCapacity the initial capacity
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity cannot be less than 1!");
		}
		elements = new Object[initialCapacity];
	}
	
	/**
	 * Instantiates a new <code>ArrayIndexedCollection</code> with the default capacity of 16.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Instantiates a new <code>ArrayIndexedCollection</code> and fills it with elements from the other collection.
	 * If <code>initialCapacity</code> is less than <code>other.size()</code>, then the capacity is set to <code>other.size</code>.
	 *
	 * @param other the other collection
	 * @param initialCapacity the initial capacity
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (other == null) {
			throw new NullPointerException("The other collection cannot be null!");
		}
		if (initialCapacity < other.size()) {
			initialCapacity = other.size();
		}
		elements = new Object[initialCapacity];
		
		addAll(other);
		size = other.size();
	}
	
	/**
	 * Instantiates a new <code>ArrayIndexedCollection</code> and fills it with elements from the other collection.
	 *
	 * @param other the other collection
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}
	
	/**
	 * Number of elements in the <code>ArrayIndexedCollection</code>.
	 *
	 * @return the size
	 */
	@Override
	int size() {
		return size;
	}
	
	/**
	 * Adds the given value to the <code>ArrayIndexedCollection</code> at the first free index.
	 * 
	 * @throws NullPointerException
	 * @param value the value
	 */
	@Override
	void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Cannot add null as an element!");
		}
		
		if (size == elements.length) ensureCapacity();
		elements[size] = value;
		size++;
	}
	
	/**
	 * Returns value at the given index.
	 *
	 * @param index the index
	 * @return the value
	 */
	Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		return elements[index];
	}
	
	/**
	 * Checks if <code>ArrayIndexedCollection</code> contains given value.
	 *
	 * @param value the value
	 * @return <code>true</code> if the collection contains the value, <code>false</code> otherwise.
	 */
	@Override
	boolean contains(Object value) {
		boolean result = false;
		for (int i = 0; i < size; i++) {
			if (value.equals(elements[i])) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Allocates a new array and fills it with elements from the <code>ArrayIndexedCollection</code>.
	 *
	 * @return the array
	 */
	@Override
	Object[] toArray() {
		Object[] returnArray = new Object[size];
		for (int i = 0; i < size; i++) {
			returnArray[i] = elements[i];
		}
		return returnArray;
	}
	
	/**
	 * Removes all elements from the <code>ArrayIndexedCollection</code>.
	 */
	@Override	
	void clear() {
		Arrays.fill(elements, null);
		size = 0;
	}
	
	/**
	 * Inserts given value at the given position. All elements at <code>position</code> and greater positions
	 * are shifted one place toward the end.
	 * 
	 * @throws IndexOutOfBoundsException
	 * @param value the value
	 * @param position the position
	 */
	void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Can not insert an element to that position");
		}
		
		for (int i = size - 1; i >= position; i--) {
			elements[i + 1] = elements[i];
		}
		
		elements[position] = value;
		size++;
	}
	
	/**
	 * Returns the index of the given value.
	 *
	 * @param value the value
	 * @return the index if the value is found, -1 otherwise.
	 */
	int indexOf(Object value) {
		int ind = -1;
		for (int i = 0; i < size; i++) {
			if (value.equals(elements[i])) {
				ind = i;
				break;
			}
		}
		
		return ind;
	}
		
	/**
	 * Removes the element at the given index.
	 * 
	 * @throws IndexOutOfBoundsException
	 * @param index the index
	 */
	void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Illegal index to remove an obbject from!");
		}
		
		for (int i = index; i < size; i++) {
			elements[i] = elements[i + 1];
		}
		size--;
	}
	
	/**
	 * Removes the given value from the collection.
	 *
	 * @param value the value
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	@Override
	boolean remove(Object value) {
		boolean result = false;
		for (int i = 0; i < size; i++) {
			if (value.equals(elements[i])) {
				result = true;
				remove(i);
				break;
			}
		}
		return result;
	}
	
	/**
	 * Calls method <code>processor.process()</code> for each element in the <code>ArrayIndexedCollection</code>.
	 *
	 * @param processor the processor
	 */
	@Override
	void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Ensures the capacity of the backing array. Every time it's called, the capacity gets doubled.
	 */
	private void ensureCapacity() {
		Object[] elementsNew = new Object[2 * elements.length];
		for (int i = 0; i < size; i++) {
			elementsNew[i] = elements[i];
		}
		
		
		elements = elementsNew;
	}

}