package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * The listener interface for receiving singleDocument events.
 * The class that is interested in processing a singleDocument
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSingleDocumentListener<code> method. When
 * the singleDocument event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SingleDocumentEvent
 */
public interface SingleDocumentListener {
	
	/**
	 * Invoked when document modify status update occurs.
	 *
	 * @param model the model
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Invoked when document file path update occurs.
	 *
	 * @param model the model
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
