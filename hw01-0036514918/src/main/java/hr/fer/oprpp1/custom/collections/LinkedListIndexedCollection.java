package hr.fer.oprpp1.custom.collections;

// TODO: Auto-generated Javadoc
/**
 * A collection of elements implemented using a double linked list.
 */
public class LinkedListIndexedCollection extends Collection {
	
	/**
	 * A node in the linked list.
	 */
	private static class ListNode {
		
		Object value;
		ListNode next;
		ListNode prev;
		
		/**
		 * Instantiates a new list node.
		 *
		 * @param value the value
		 */
		public ListNode(Object value) {
			this.value = value;
			next = null;
			prev = null;
		}
	}

	private int size = 0;
	private ListNode first;
	private ListNode last;

	/**
	 * Instantiates a new empty <code>LinkedListIndexedCollection</code>. 
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
	}
	
	/**
	 * Instantiates a new <code>LinkedListIndexedCollection</code> and fills it with elements from the given collection.
	 *
	 * @param other the other collection
	 */
	public LinkedListIndexedCollection(Collection other) {
		addAll(other);
	}
	
	/**
	 * Returns the size of the<code>LinkedListIndexedCollection</code>. 
	 *
	 * @return the size
	 */
	@Override
	int size() {
		return size;
	}
	
	/**
	 * Adds given value to the <code>LinkedListIndexedCollection</code> at the end of the list.
	 *
	 * @throws NullPointerException
	 * @param value the value
	 */
	@Override
	void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Can't add null as an element!"); 
		}
		
		ListNode newNode = new ListNode(value);
		
		if (first == null) {
			first = newNode;
			last = newNode;
		} else {
			last.next = newNode;
			newNode.prev = last;
			last = newNode;
		}
		
		size++;
	}
	
	/**
	 * Returns the value at the given index.
	 *
	 * @param index the index
	 * @return the value
	 */
	Object get(int index) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("No value at that index!");
		}
		ListNode current;
		
		if (index <= size / 2) {
			current = first;
			while (index != 0) {
				current = current.next;
				index--;
			}
		} else {
			current = last;
			while (index != size - 1) {
				current = current.prev;
				index++;
			}
		}
		
		return current.value;
	}
	
	/**
	 * Checks if <code>LinkedListIndexedCollection</code> contains the given value.
	 *
	 * @param value the value
	 * @return true, if successful
	 */
	@Override
	boolean contains(Object value) {
		boolean result = false;
		int i = 0;
		
		ListNode current = first;
		while (i < size) {
			if (value.equals(current.value)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * Allocates a new array and fills it with elements from the <code>LinkedListIndexedCollection</code>.
	 *
	 * @return array of objects from the collection.
	 */
	@Override
	Object[] toArray() {
		int i = 0;
		Object[] array = new Object[size];
		
		ListNode current = first;
		while (i < size) {
			array[i] = current.value;
			current = current.next;
			i++;
		}
		
		return array;
	}
	
	/**
	 * Clears the <code>LinkedListIndexedCollection</code>.
	 */
	@Override
	void clear() {
		first = last = null;
	}
	
	/**
	 * Inserts given value into the <code>LinkedListIndexedCollection</code> at the given position.
	 * 
	 * @throws IndexOutOfBoundsException
	 * @throws NullPointerException
	 * @param value the value
	 * @param position the position
	 */
	void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Can't insert a value at that position!");
		}
		if (value == null) {
			throw new NullPointerException("Can't insert null as an element!");
		}
		
		ListNode newNode = new ListNode(value);
		
		//rightNode represents the node that will be on a higher index than the inserted node,
		//while leftNode represents the one on a lower index.
		ListNode rightNode = first;
		ListNode leftNode;
		
		int i = 0;
		while (i < position) {
			rightNode = rightNode.next;
			i++;
		}
		leftNode = rightNode.prev;
		
		if (i == 0) first = newNode;
		else if (i == size) last = newNode;
		
		leftNode.next = newNode;
		rightNode.prev = newNode;
		newNode.prev = leftNode;
		newNode.next = rightNode;
		
		size++;
	}
	
	/**
	 * Returns the index of the given value.
	 *
	 * @param value the value
	 * @return the index if found, -1 otherwise.
	 */
	int indexOf(Object value) {
		int ind = -1;
		int i = 0;
		ListNode current = first;
		while (i < size) {
			if (value.equals(current.value)) {
				ind = i;
				break;
			}
		}
		
		return ind;
	}
	
	/**
	 * Removes the element at the given index.
	 *
	 * @param index the index
	 */
	void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("No value to remove at that index!");
		}
		
		ListNode current = first;
		while (index > 0) {
			current = current.next;
			index--;
		}
		ListNode leftNode = current.prev;
		ListNode rightNode = current.next;
		
		leftNode.next = rightNode;
		rightNode.prev = leftNode;
		
		size--;
	}
	
	/**
	 * Removes the given value from the <code>LinkedListIndexedCollection</code>.
	 *
	 * @param value the value
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	@Override
	boolean remove(Object value) {
		if (value == null) return false;
		
		boolean result = false;
		
		ListNode current = first;
		int i = 0;
		
		while (i < size) {
			if (value.equals(current.value)) {
				result = true;
				
				if (i == 0) {
					first = current.next;
					first.prev = null;
				} else if (i == size - 1) {
					last = current.prev;
					last.next = null;
				} else {
					(current.prev).next = current.next;
					(current.next).prev = current.prev;
				}
				
				size--;
				break;
			}
		}
		
		return result;
	}
	
	
	/**
	 * Calls method <code>processor.process()</code> for each element in the <code>LinkedListIndexedCollection</code>.
	 *
	 * @param processor the processor
	 */
	@Override
	void forEach(Processor processor) {
		ListNode current = first;
		
		if(current == null) {
			for (int i = 0; i < size; i++) {
				
			}
		}
		
		int i = 0;
		while (i < size) {
			processor.process(current.value);
			current = current.next;
			i++;
		}
	}
}
