package hr.fer.oprpp1.custom.collections;

/**
 * The Class Dictionary.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class Dictionary<K,V> {
	
	/**
	 * The Class Pair. Represents a pair of key and value, stored in a private <code>ArrayIndexedCollection</code>
	 *
	 * @param <K> the key type
	 * @param <V> the value type
	 */
	private static class Pair<K,V> {
		
		K key;
		V value;
		
		/**
		 * Instantiates a new key-value pair.
		 *
		 * @param key the key
		 * @param value the value
		 */
		public Pair(K key, V value) {
				this.key = key;
				this.value = value;
		}
	}
	
	private ArrayIndexedCollection<Pair<K,V>> col;
	
	/**
	 * Instantiates a new dictionary.
	 */
	public Dictionary() {
		col = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Checks if dictionary is empty.
	 *
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return col.isEmpty();
	}
	
	/**
	 * Number of elements (key-value pairs) in dictionary.
	 *
	 * @return the size
	 */
	public int size() {
		return col.size();
	}
	
	/**
	 * Removes all elements from dictionary.
	 */
	public void clear() {
		col.clear();
	}
	
	/**
	 * Puts a new key-value pair into the dictionary. If the key is already in the dictionary, overwrites value.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the previous value if it has been overwritten, null otherwise.
	 */
	public V put(K key, V value) {
		if (key == null) throw new NullPointerException("Key cannot be null!");
		
		ElementsGetter<Pair<K,V>> getter = col.createElementsGetter();
		boolean found = false;
		int i;
		for (i = 0 ; i < size(); i++) {
			if (getter.getNextElement().key.equals(key)) {
				found = true;
				break;
			}
		}
		
		V returnValue = null;
		if (found) {
			returnValue = col.get(i).value;
			col.remove(i);
			col.insert(new Pair<>(key, value), i);
		} else {
			col.add(new Pair<>(key,value));
		}
		
		return returnValue;
	}
	
	/**
	 * Gets the value paired with the given key.
	 *
	 * @param key the key
	 * @return the value if the key exists in dictionary, null otherwise.
	 */
	public V get(Object key) {
		V returnValue = null;
		
		ElementsGetter<Pair<K,V>> getter = col.createElementsGetter();
		for (int i = 0 ; i < size(); i++) {
			Pair<K,V> pair = getter.getNextElement();
			if (pair.key.equals(key)) {
				returnValue = pair.value;
				break;
			}
		}
		
		return returnValue;
	}
	
	/**
	 * Removes the key-value pair with the given key.
	 *
	 * @param key the key
	 * @return the value of the pair if it existed in dictionary, null otherwise.
	 */
	public V remove(K key) {
		if (key == null) return null;
		

		ElementsGetter<Pair<K,V>> getter = col.createElementsGetter();
		
		boolean found = false;
		int i;
		for (i = 0 ; i < size(); i++) {
			if (getter.getNextElement().key.equals(key)) {
				found = true;
				break;
			}
		}
		
		V returnValue = null;
		if (found) {
			returnValue = col.get(i).value;
			col.remove(i);	
		}
		
		return returnValue;
	}
}
