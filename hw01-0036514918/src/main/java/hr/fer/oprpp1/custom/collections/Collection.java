package hr.fer.oprpp1.custom.collections;

/**
 * A general collection of objects.
 */
public class Collection {
	
	
	/**
	 * Default class constructor.
	 */
	protected Collection() {
		
	}
	
	/**
	 * Checks if collection is empty.
	 *
	 * @return <code>true</code>, if collection is empty,
	 * 			<code>false</code> otherwise.
	 */
	boolean isEmpty() {
		return size()==0;
	}
	
	/**
	 * Size of the collection. 
	 *
	 * @return the size of the collection.
	 */
	int size() {
		return 0;
	}
	
	/**
	 * Adds an object to the collection.
	 *
	 * @param value the object being added.
	 */
	void add(Object value) {
		
	}
	
	/**
	 * Checks if the collection contains a value.
	 *
	 * @param value the value
	 * @return <code>true</code> if collection contains the value,
	 * 			<code>false</code> otherwise.
	 */
	boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Checks if collection contains a value, then removes one occurrence of it.
	 *
	 * @param value the value being removed
	 * @return <code>true</code> if successful,
	 * 			<code>false</code> otherwise.
	 */
	boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates new array of equal size as the collection
	 * and fills it with collection content.
	 *
	 * @throws UnsupportedOperationException
	 * @return New array filled with collection content.
	 */
	Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Calls the method processor.process() for each element in the collection.
	 *
	 * @param processor the processor
	 */
	void forEach(Processor processor) {

	}
	
	/**
	 * Adds all elements of other given collection to the collection,
	 * while the other collection remains unchanged.
	 *
	 * @param other the collection from which the elements are being added.
	 */
	void addAll(Collection other) {
		class addingProcessor extends Processor{
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		addingProcessor processor = new addingProcessor();
		other.forEach(processor);
	}
	
	/**
	 * Removes all elements from the collection.
	 */
	void clear() {
		
	}
	
	
}
