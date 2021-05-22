package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * The listener interface for receiving multipleDocument events.
 * The class that is interested in processing a multipleDocument
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addMultipleDocumentListener<code> method. When
 * the multipleDocument event occurs, that object's appropriate
 * method is invoked.
 *
 * @see MultipleDocumentEvent
 */
public interface MultipleDocumentListener {
	
	/**
	 * Invoked when the current document has been changed.
	 *
	 * @param previousModel the previous model
	 * @param currentModel the current model
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * Invoked when a document has been added.
	 *
	 * @param model the model
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Invoked when a document has been removed.
	 *
	 * @param model the model
	 */
	void documentRemoved(SingleDocumentModel model);
}
