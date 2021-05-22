package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

/**
 * The Interface MultipleDocumentModel.
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel>{
	
	/**
	 * Creates a new single document, adds it to the collection of documents and creates a new tab for it.
	 *
	 * @return the single document model
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Gets the currently selected document.
	 *
	 * @return the current document
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Loads a document from disk, adds it to the collection of documents and creates a new tab for it.
	 * If the document is already open, the user is notified and that tab is selected.
	 *
	 * @param path the path
	 * @return the single document model
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Saves the currently selected document.
	 *
	 * @param model the model
	 * @param newPath the new path
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Closes the currently selected document.
	 *
	 * @param model the model
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Adds the multiple document listener.
	 *
	 * @param l the l
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Removes the multiple document listener.
	 *
	 * @param l the l
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Gets the number of documents.
	 *
	 * @return the number of documents
	 */
	int getNumberOfDocuments();
	
	/**
	 * Gets the document at specified index.
	 *
	 * @param index the index
	 * @return the document
	 */
	SingleDocumentModel getDocument(int index);
}
