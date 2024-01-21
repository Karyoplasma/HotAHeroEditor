package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.Hero;
import core.ModFileHandler;
import gui.HotAHeroEditor;
import models.ChangesTableModel;

public class LoadButtonAction extends AbstractAction {

	private static final long serialVersionUID = -5257384624322780404L;
	HotAHeroEditor gui;

	public LoadButtonAction(HotAHeroEditor gui) {
		putValue(Action.NAME, "Load");
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File changesFile = this.getChangesFile();
		if (changesFile == null) {
			return;
		}
		List<Hero> changes = ModFileHandler.readModFileFromDisk(changesFile.toPath());
		if (changes == null || changes.isEmpty()) {
			JOptionPane.showMessageDialog(this.gui.getFrame(), "Could not load changes.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		int notLoaded = ((ChangesTableModel) this.gui.getTableChanges().getModel()).loadChanges(changes);
		if (notLoaded == 0) {
			JOptionPane.showMessageDialog(this.gui.getFrame(), String.format("%d changes loaded!", changes.size()),
					"Success", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this.gui.getFrame(), String.format(
					"%d changes loaded!\n%d changes were inapplicable because the mod file contained HotA content.",
					changes.size() - notLoaded, notLoaded), "Finished loading", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private File getChangesFile() {
		File directory = new File("mods");
		JFileChooser fileChooser = new JFileChooser("Select changes you saved");
		fileChooser.setFileFilter(new FileNameExtensionFilter("mod files", "mod"));
		fileChooser.setCurrentDirectory(directory);
		int ret = fileChooser.showOpenDialog(gui.getFrame());
		if (ret == (JFileChooser.APPROVE_OPTION)) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}

}
