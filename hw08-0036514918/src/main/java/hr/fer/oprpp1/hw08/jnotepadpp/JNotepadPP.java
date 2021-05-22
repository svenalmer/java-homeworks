package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JNotepadPP extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private DefaultMultipleDocumentModel tabs;
	
	/**
	 * Instantiates a new j notepad PP.
	 */
	public JNotepadPP() {
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		setTitle("JNotepad++");
		
		initGUI();
		
		//TODO Popraviti save i ici po svakom dokumentu provjeravati je li nespremljen
		
		WindowListener wl = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(new ActionEvent(this, 1, null));
			}
		};
		this.addWindowListener(wl);
		
		setLocationRelativeTo(null);

	}
	
	/**
	 * Initializes the graphic components.
	 */
	private void initGUI() {
		
		getContentPane().setLayout(new BorderLayout());
		
		tabs = new DefaultMultipleDocumentModel();
		tabs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				tabs.changeCurrentDocument(tabs.getSelectedIndex());
				updateTitle();
			}
		});
		
		getContentPane().add(tabs, BorderLayout.CENTER);
		
		createActions();
		createMenus();
		createToolbars();
	}
	
	private Action newDocumentAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		@Override
		public void actionPerformed(ActionEvent e) {
			tabs.createNewDocument();
		}
	};
	
	private Action openDocumentAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if(fc.showOpenDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"File "+fileName.getAbsolutePath()+" does not exist!", 
						"Error", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			SingleDocumentModel doc = tabs.loadDocument(filePath);
			if (doc == null) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Error while reading file "+fileName.getAbsolutePath()+".", 
						"Error", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	};
	
	private Action saveDocumentAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Path path = null;
			if(tabs.getCurrentDocument().getFilePath()==null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(
							JNotepadPP.this, 
							"Nothing was saved.", 
							"Warning", 
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				path = jfc.getSelectedFile().toPath();
			} else {
				path = tabs.getCurrentDocument().getFilePath();
			}
			
			tabs.saveDocument(tabs.getCurrentDocument(), path);
		}
	};
	
	private Action saveAsAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Nothing was saved.", 
						"Warning", 
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			Path path = jfc.getSelectedFile().toPath();
			
			tabs.saveDocument(tabs.getCurrentDocument(), path);
			
		}
	};
	
	private Action closeDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (tabs.getCurrentDocument().isModified()) {
				String name = "unnamed";
				if (tabs.getCurrentDocument().getFilePath() != null) {
					name = tabs.getCurrentDocument().getFilePath().getFileName().toString();
				}
				
				int rezultat = JOptionPane.showConfirmDialog(JNotepadPP.this, "There are unsaved changes in document " + name + ". Do you want to save?", "Warning!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				switch(rezultat) {
					case JOptionPane.CANCEL_OPTION:
						return;
					case JOptionPane.YES_OPTION:
						saveDocumentAction.actionPerformed(new ActionEvent(this, 1, null));
						break;
					case JOptionPane.NO_OPTION:
						break;
						
				}
			}
			tabs.closeDocument(tabs.getCurrentDocument());
		}
	};
	
	private Action infoAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel doc = tabs.getCurrentDocument();
			
		}
	};
	
	private Action exitAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			for (SingleDocumentModel doc : tabs) {
				String name = "unnamed";
				if (doc.getFilePath() != null) {
					name = doc.getFilePath().getFileName().toString();
				}
				
				if(doc.isModified()) {
					
					int rezultat = JOptionPane.showConfirmDialog(JNotepadPP.this, "There are unsaved changes in document " + name + ". Do you want to save?", "Warning!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					switch(rezultat) {
					case JOptionPane.CANCEL_OPTION:
						return;
					case JOptionPane.YES_OPTION:
						tabs.saveDocument(doc, doc.getFilePath());
						continue;
					case JOptionPane.NO_OPTION:
						continue;
					}
				}
			}
			
			System.exit(0);
		}
		
	};
	
	/**
	 * Creates the actions.
	 */
	private void createActions() {
		newDocumentAction.putValue(
				Action.NAME, 
				"New");
		newDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control N")); 
		newDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_N); 
		newDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to create a new blank file."); 
		
		openDocumentAction.putValue(
				Action.NAME, 
				"Open");
		openDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control O")); 
		openDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_O); 
		openDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to open existing file from disk."); 
		
		saveDocumentAction.putValue(
				Action.NAME, 
				"Save");
		saveDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control S")); 
		saveDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_S); 
		saveDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to save current file to disk.");
		
		saveAsAction.putValue(
				Action.NAME, 
				"Save As...");
		saveAsAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to save current file to disk on a new location under new name.");
		
		closeDocumentAction.putValue(
				Action.NAME, 
				"Close");
		closeDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control W")); 
		closeDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_W); 
		closeDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to close current tab.");
		
		exitAction.putValue(
				Action.NAME, 
				"Exit");
		exitAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control X"));
		exitAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_X); 
		exitAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Exit application."); 
	}
	
	/**
	 * Creates the dropdown menus.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
//		editMenu.add(new JMenuItem(deleteSelectedPartAction));
//		editMenu.add(new JMenuItem(toggleCaseAction));
		
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Creates the toolbars.
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);
		
		toolBar.add(new JButton(newDocumentAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsAction));
		toolBar.add(new JButton(closeDocumentAction));
		toolBar.addSeparator();
//		toolBar.add(new JButton(deleteSelectedPartAction));
//		toolBar.add(new JButton(toggleCaseAction));
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * Updates the window title when a path of a file changes.
	 */
	private void updateTitle() {
		SingleDocumentModel doc = tabs.getCurrentDocument();
		if (doc == null) setTitle("JNotepad++");
		else if (doc.getFilePath() == null) setTitle("unnamed - JNotepad ++");
		else setTitle(doc.getFilePath().toString() + " - JNotepad ++");
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new JNotepadPP().setVisible(true);
		});
	}
}
