package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Simple hashtable implementation.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {

	/**
	 * Entry in the hashtable.
	 *
	 * @param <K> the key type
	 * @param <V> the value type
	 */
	public static class TableEntry<K,V> {
		
		private K key;
		private V value;
		private TableEntry<K,V> next;
		
		/**
		 * Instantiates a new table entry.
		 *
		 * @param key the key
		 * @param value the value
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Gets the entry key.
		 *
		 * @return the key
		 */
		public K getKey() { 
			return key; 
		}
		
		/**
		 * Sets the entry value.
		 *
		 * @param value the new value
		 */
		public void setValue(V value) { 
			this.value = value; 
		}
		
		/**
		 * Gets the entry value.
		 *
		 * @return the value
		 */
		public V getValue() {
			return value;
		}
		
	}
	
	private static final int DEFAULT_SIZE = 16;
	private TableEntry<K,V>[] table;
	private int size = 0;
	private int modificationCount = 0;
	
	/**
	 * Instantiates a new simple hashtable with the default capacity of 16 slots.
	 */
	public SimpleHashtable() {
		this(DEFAULT_SIZE);
	}
	
	/**
	 * Instantiates a new simple hashtable. Sets the capacity of the table to the lowest power of 2 greater than the given capacity.
	 *
	 * @param capacity the capacity
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException("Table capacity must be equal or greater than 1!");
		
		int newCapacity = 1;
		while (newCapacity < capacity) {
			newCapacity *= 2;
		}
		
		table = (TableEntry<K,V>[])new TableEntry[newCapacity];
	}
	
	/**
	 * Puts new entry into hashtable with the given key and value values. If key already exists, overwrites its corresponding value and returns it.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the overwritten value if key exists, null otherwise.
	 */
	public V put(K key, V value) {
		if (key == null)
			throw new NullPointerException("Key cannot be null!");
		
		if (((1.0 * size) / table.length) >= 0.75 * table.length) 
			doubleCapacity();
		
		int index = Math.abs(key.hashCode()) % table.length;
		V returnValue = null;
		
		if (table[index] == null) {
			table[index] = new TableEntry<>(key, value);
			size++;
			modificationCount++;
		} else {
			TableEntry<K,V> current = table[index];
			while (true) {
				if (current.getKey().equals(key)) {
					returnValue = current.getValue();
					current.setValue(value);
					break;
				}
				
				if (current.next == null) {
					current.next = new TableEntry<>(key, value);
					size++;
					modificationCount++;
					break;
				}
				current = current.next;
			}
			
		}
		return returnValue;
	}
	
	/**
	 * Gets the value at the given key.
	 *
	 * @param key the key
	 * @return the v
	 */
	public V get(Object key) {
		if (key == null) return null;
		int index = Math.abs(key.hashCode()) % table.length;
		
		TableEntry<K,V> entry = table[index];
		while (entry != null) {
			if (entry.getKey().equals(key)) return entry.getValue();
			entry = entry.next;
		}
		
		return null;
	}
	
	/**
	 * Number of stored elements in the hashtable.
	 *
	 * @return the size
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Checks if table contains given key.
	 *
	 * @param key the key
	 * @return true if it contains the key, false otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null) return false;
		int index = Math.abs(key.hashCode()) % table.length;
		
		TableEntry<K,V> entry = table[index];
		while (entry != null) {
			if (entry.getKey().equals(key)) return true;
			entry = entry.next;
		}
		
		return false;
	}
	
	/**
	 * Checks if table contains given key.
	 *
	 * @param value the value
	 * @return true if it contains the value, false otherwise.
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			TableEntry<K,V> entry = table[i];
			while (entry != null) {
				if (entry.getValue().equals(value)) {
					return true;
				}
				entry = entry.next;
			}
		}
		return false;
	}
	
	/**
	 * Removes the entry with the given key.
	 *
	 * @param key the key
	 * @return the value at the given key, null if the table doesnt contain the key.
	 */
	public V remove(Object key) {
		if (key == null || !containsKey(key)) return null;
		
		V returnValue = null;
		int index = Math.abs(key.hashCode()) % table.length;
		TableEntry<K,V> current = table[index];
		
		if (current.getKey().equals(key)) {
			table[index] = current.next;
			modificationCount++;
			size--;
			return current.getValue();
		}
		
		TableEntry<K,V> previous = current;
		current = current.next;
		
		while (current != null) {
			if (current.getKey().equals(key)) {
				returnValue = current.getValue();
				previous.next = current.next;
				break;
			}
			previous = previous.next;
			current = current.next;
		}
		
		modificationCount++;
		size--;
		return returnValue;
	}
	
	/**
	 * Checks if hashtable is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Returns the contents of the table as a string.
	 *
	 * @return the string
	 */
	public String toString() {
		TableEntry<K,V>[] array = toArray();
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i].getKey());
			sb.append("=");
			sb.append(array[i].getValue());
			if (i != array.length - 1) sb.append(", ");
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * Returns the contents of the table as an array.
	 *
	 * @return the table entry[]
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K,V>[] toArray() {
		TableEntry<K,V>[] array = (TableEntry<K,V>[])new TableEntry[size];
		int j = 0;
		for (int i = 0; i < table.length; i++) {
			TableEntry<K,V> entry = table[i];
			while (entry != null) {
				array[j++] = entry;
				entry = entry.next;
			}
		}
		return array;
	}
	
	/**
	 * Clears the table.
	 */
	public void clear() {
		Arrays.fill(table, null);
	}
	
	/**
	 * Doubles capacity.
	 */
	@SuppressWarnings("unchecked")
	private void doubleCapacity() {
		int currentCapacity = table.length;
		TableEntry<K,V>[] entries = toArray();
		table = (TableEntry<K,V>[])new TableEntry[2 * currentCapacity];
		size = 0;
		for (TableEntry<K,V> e : entries) {
			put(e.getKey(),e.getValue());
		}
	}

	/**
	 * Private implementation of an iterator.
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		int counter = 0;
		int currentIndex = 0;
		TableEntry<K,V> currentEntry;
		boolean iteratingLinked = false;
		boolean removedCurrent = false;
		int savedModificationCount;
		
		/**
		 * Instantiates a new iterator impl.
		 *
		 * @param modificationCount the modification count
		 */
		public IteratorImpl(int modificationCount) {
			savedModificationCount = modificationCount;
		}

		/**
		 * Checks for next element.
		 *
		 * @return true, if successful
		 */
		@Override
		public boolean hasNext() {
			if (savedModificationCount != modificationCount)
				throw new ConcurrentModificationException();
			return counter < size;
		}

		/**
		 * returns next element.
		 *
		 * @return the next table entry
		 */
		@Override
		public TableEntry<K, V> next() {
			if (!hasNext()) 
				throw new NoSuchElementException();
			
			if(removedCurrent) removedCurrent = false;
			
			if (iteratingLinked) {
				if (currentEntry.next == null) {
					iteratingLinked = false;
					currentIndex++;
				} else {
					currentEntry = currentEntry.next;
					counter++;
					return currentEntry;
				}
			}
			
			while (table[currentIndex] == null) {
				currentIndex++;
			}
			
			currentEntry = table[currentIndex];
			iteratingLinked = true;
			counter++;
			return currentEntry;
		}
		
		/**
		 * Removes the current table entry from the collection.
		 */
		public void remove() {
			if (savedModificationCount != modificationCount) throw new ConcurrentModificationException();
			if (removedCurrent) throw new IllegalStateException();
			SimpleHashtable.this.remove(currentEntry.getKey());
			counter--;
			savedModificationCount++;
			removedCurrent = true;
		}
	}
	
	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl(modificationCount);
	}
}
