package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{

	private static final long serialVersionUID = 1L;
	private List<SingleDocumentModel> documents;
	private SingleDocumentModel currentDocument;
	private List<MultipleDocumentListener> listeners;
	private static ImageIcon green;
	private static ImageIcon red;
	
	public DefaultMultipleDocumentModel() {
		documents = new ArrayList<>();
		
		try {
			green = getImageIcon("icons/save-16-2.png");
			red = getImageIcon("icons/save-16.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.listIterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDoc = new DefaultSingleDocumentModel(null, "");
		documents.add(newDoc);
		currentDocument = newDoc;
		
		//Dodan tab
		JScrollPane scrollPane = new JScrollPane(newDoc.getTextComponent());
		JPanel p = new JPanel();
		p.add(scrollPane);
		p.setLayout(new GridLayout(1,1));
		this.addTab("unnamed", green, p, "unnamed");
		
		newDoc.addSingleDocumentListener(new DefaultSingleDocumentListener());
		
		return newDoc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		byte[] okteti;
		try {
			okteti = Files.readAllBytes(path);
		} catch (IOException e) {
			return null;
		}
		String tekst = new String(okteti, StandardCharsets.UTF_8);
		SingleDocumentModel doc = new DefaultSingleDocumentModel(path, tekst);
		
		doc.addSingleDocumentListener(new DefaultSingleDocumentListener());
		
		boolean contains = false;
		for (SingleDocumentModel d : documents) {
			if (d.getFilePath() == null) continue;
			if (d.getFilePath().equals(doc.getFilePath())) {
				contains = true;
				break;
			}
		}
		
		if (!contains) {
			documents.add(doc);
			this.addTab(path.getFileName().toString(), green, new JScrollPane(doc.getTextComponent()), path.toString());
			currentDocument = doc;
		} else {
			JOptionPane.showMessageDialog(this, "This document is already open!", "Notification", JOptionPane.PLAIN_MESSAGE);
		}
		
		return doc;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		// TODO Auto-generated method stub
		byte[] podatci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newPath, podatci);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
					this, 
					"Eror while saving file: "+newPath.toFile().getAbsolutePath()+".\nCaution: state of file unclear!", 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (model.getFilePath() == null) {
			model.setFilePath(newPath);
		}
		
		JOptionPane.showMessageDialog(
				this, 
				"File saved.", 
				"Info", 
				JOptionPane.INFORMATION_MESSAGE);
		
		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		this.remove(index);
		documents.remove(index);
		if (documents.size() == 0) currentDocument = null;
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	public void changeCurrentDocument(int index) {
		if (index < 0) {
			return;
		}
		SingleDocumentModel model = documents.get(index);
		currentDocument = model;
	}
	
	/**
	 * Gets the image icon from given path.
	 *
	 * @param path the path
	 * @return the image icon
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private ImageIcon getImageIcon(String path) throws IOException {
		InputStream is = this.getClass().getResourceAsStream(path);
		if (is == null)
			throw new IOException();
		byte[] bytes = is.readAllBytes();
		is.close();
		return new ImageIcon(bytes);
	}

	private class DefaultSingleDocumentListener implements SingleDocumentListener {
		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			if (model.isModified()) setIconAt(documents.indexOf(model), red);
			else setIconAt(documents.indexOf(model), green);
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			DefaultMultipleDocumentModel.this.setTitleAt(documents.indexOf(model), model.getFilePath().getFileName().toString());
			DefaultMultipleDocumentModel.this.setToolTipTextAt(documents.indexOf(model), model.getFilePath().toString());
			JFrame frame = (JFrame) DefaultMultipleDocumentModel.this.getTopLevelAncestor();
			frame.setTitle(model.getFilePath().toString() + " - JNotepad++");
		}
	}
}
