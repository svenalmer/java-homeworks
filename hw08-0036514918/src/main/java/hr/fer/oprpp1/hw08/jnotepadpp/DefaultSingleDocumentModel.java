package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Dimension;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel{

	private JTextArea editor;
	private Path openedFilePath;
	private boolean modified = false;
	private List<SingleDocumentListener> listeners;
	
	public DefaultSingleDocumentModel(Path path, String text) {
		openedFilePath = path;
		editor = new JTextArea(text);
		editor.setPreferredSize(new Dimension(200, 200));
		
		addTextChangeListener();
		listeners = new ArrayList<>();
	}
	
	public JTextArea getTextComponent() {
		return editor;
	}

	public Path getFilePath() {
		return openedFilePath;
	}

	public void setFilePath(Path path) {
		openedFilePath = path;
		for (SingleDocumentListener l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		if (modified == false) addTextChangeListener();
		this.modified = modified;
		for (SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}

	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
	private void addTextChangeListener() {
		editor.getDocument().addUndoableEditListener(new UndoableEditListener() {
			@Override
			public void undoableEditHappened(UndoableEditEvent event) {
				setModified(true);
				DefaultSingleDocumentModel.this.editor.getDocument().removeUndoableEditListener(this);
			}
		});
	}
}
