package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

// TODO: Auto-generated Javadoc
/**
 * The Interface SingleDocumentModel.
 */
public interface SingleDocumentModel {
	
	/**
	 * Gets the documents text component (editor).
	 *
	 * @return the text component
	 */
	JTextArea getTextComponent();
	
	/**
	 * Gets the opened file path.
	 *
	 * @return the file path
	 */
	Path getFilePath();
	
	/**
	 * Sets the opened file path.
	 *
	 * @param path the new file path
	 */
	void setFilePath(Path path);
	
	/**
	 * Checks if document is modified.
	 *
	 * @return true, if is modified
	 */
	boolean isModified();
	
	/**
	 * Sets the modified status.
	 *
	 * @param modified the new modified
	 */
	void setModified(boolean modified);
	
	/**
	 * Adds a single document listener.
	 *
	 * @param l the l
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Removes the single document listener.
	 *
	 * @param l the l
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
