package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import actions.OpenExecutableButtonAction;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.BorderLayout;

public class HotAHeroEditor {

	private JFrame frame;
	private JTable tableEditor;
	private JTable tableChanges;
	private Path saveDirectory;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HotAHeroEditor window = new HotAHeroEditor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HotAHeroEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.initializeSaveDirectory();
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][][grow][][]", "[][grow][][grow\r\n][]"));

		JCheckBox chckbxSaveDirectory = new JCheckBox("Save directory");
		chckbxSaveDirectory.setSelected(true);
		frame.getContentPane().add(chckbxSaveDirectory, "cell 1 0,grow");
		
		JButton btnOpenExecutable = new JButton(new OpenExecutableButtonAction(saveDirectory, chckbxSaveDirectory));
		btnOpenExecutable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnOpenExecutable, "cell 0 0,grow");
		
		JButton btnUnlock = new JButton("Unlock");
		btnUnlock.setEnabled(false);
		btnUnlock.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(btnUnlock, "cell 3 0,grow");

		JButton btnWrite = new JButton("Write");
		btnWrite.setEnabled(false);
		btnWrite.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(btnWrite, "cell 4 0,grow");

		JScrollPane scrollPaneEditor = new JScrollPane();
		frame.getContentPane().add(scrollPaneEditor, "cell 0 1 5 1,grow");

		tableEditor = new JTable();
		tableEditor.setShowGrid(false);
		tableEditor.setShowVerticalLines(false);
		tableEditor.setShowHorizontalLines(false);
		tableEditor.setFillsViewportHeight(true);
		tableEditor.setFont(new Font("Monospaced", Font.PLAIN, 14));
		scrollPaneEditor.setViewportView(tableEditor);

		JPanel panelSeparator = new JPanel();
		frame.getContentPane().add(panelSeparator, "cell 0 2 5 1,grow");
		panelSeparator.setLayout(new BorderLayout(0, 0));

		JSeparator separator = new JSeparator();
		panelSeparator.add(separator, BorderLayout.CENTER);

		JScrollPane scrollPaneChanges = new JScrollPane();
		frame.getContentPane().add(scrollPaneChanges, "cell 0 3 5 1,grow");

		tableChanges = new JTable();
		tableChanges.setShowVerticalLines(false);
		tableChanges.setShowHorizontalLines(false);
		tableChanges.setShowGrid(false);
		tableChanges.setFillsViewportHeight(true);
		tableChanges.setFont(new Font("Monospaced", Font.PLAIN, 14));
		scrollPaneChanges.setViewportView(tableChanges);

		JButton btnLoad = new JButton("load");
		btnLoad.setEnabled(false);
		btnLoad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnLoad, "cell 0 4,grow");

		JButton btnNewButton = new JButton("save");
		btnNewButton.setEnabled(false);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnNewButton, "cell 1 4,grow");

		JButton btnDiscardAll = new JButton("Discard All");
		btnDiscardAll.setEnabled(false);
		btnDiscardAll.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnDiscardAll, "cell 4 4");
	}

	private void initializeSaveDirectory() {
		try (BufferedReader reader = new BufferedReader(new FileReader("resources/directoryPath.txt"))) {
			this.saveDirectory = Paths.get(reader.readLine());
		} catch (IOException e) {
			//ignore error
		}

	}
}