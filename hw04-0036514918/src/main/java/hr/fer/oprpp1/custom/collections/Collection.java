package hr.fer.oprpp1.custom.collections;

/**
 * A general collection of objects.
 */
public interface Collection<T> {	
	/**
	 * Checks if collection is empty.
	 *
	 * @return <code>true</code>, if collection is empty,
	 * 			<code>false</code> otherwise.
	 */
	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Size of the collection. 
	 *
	 * @return the size of the collection.
	 */
	int size();
	
	/**
	 * Adds an object to the collection.
	 *
	 * @param value the object being added.
	 */
	void add(T value);
	
	/**
	 * Checks if the collection contains a value.
	 *
	 * @param value the value
	 * @return <code>true</code> if collection contains the value,
	 * 			<code>false</code> otherwise.
	 */
	boolean contains(Object value);
	
	/**
	 * Checks if collection contains a value, then removes one occurrence of it.
	 *
	 * @param value the value being removed
	 * @return <code>true</code> if successful,
	 * 			<code>false</code> otherwise.
	 */
	boolean remove(T value);
	
	/**
	 * Allocates new array of equal size as the collection
	 * and fills it with collection content.
	 *
	 * @throws UnsupportedOperationException
	 * @return New array filled with collection content.
	 */
	Object[] toArray();
	
	/**
	 * Calls the method <code>processor.process()</code> for each element in the collection.
	 *
	 * @param processor the processor
	 */
	default void forEach(Processor<T> processor) {
		ElementsGetter<T> getter = createElementsGetter();
		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	/**
	 * Adds all elements of other given collection to the collection,
	 * while the other collection remains unchanged.
	 *
	 * @param other the collection from which the elements are being added.
	 */
	default void addAll(Collection<T> other) {
		class addingProcessor implements Processor<T>{
			public void process(T value) {
				add(value);
			}
		}
		addingProcessor processor = new addingProcessor();
		other.forEach(processor);
	}
	
	/**
	 * Removes all elements from the collection.
	 */
	void clear();
	
	/**
	 * Creates a new <code>ElementsGetter</code>.
	 *
	 * @return new instance of <code>ElementsGetter</code>
	 */
	ElementsGetter<T> createElementsGetter();
	
	default void addAllSatisfying(Collection<T> col, Tester<T> tester) {
		ElementsGetter<T> getter = col.createElementsGetter();
		
		while (getter.hasNextElement()) {
			T element = getter.getNextElement();
			
			if (tester.test(element)) {
				this.add(element);
			}
		}
	}
}
