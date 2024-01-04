package actions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import core.H3ExecutableHandler;
import core.Hero;
import gui.HotAHeroEditor;
import models.ChangesTableModel;

public class WriteButtonAction extends AbstractAction {

	private static final long serialVersionUID = -7880210436237200968L;
	HotAHeroEditor gui;

	public WriteButtonAction(HotAHeroEditor gui) {
		putValue(Action.NAME, "Write");
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<Hero> changes = ((ChangesTableModel) this.gui.getTableChanges().getModel()).getChanges();
		if (changes == null || changes.isEmpty()) {
			JOptionPane.showMessageDialog(this.gui.getFrame(),
					"No changes found. This shouldn't happen.\nYour executable has not been changed.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		Path executable = this.gui.getExecutable();
		
		int errorCode = H3ExecutableHandler.createBackup(executable);
		if (errorCode == 1) {
			JOptionPane.showMessageDialog(this.gui.getFrame(),
					"Could not create backup directory. Please create a directory \"backupHeroModder\" in your HoMM folder and try again.\nYour executable has not been changed.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (errorCode == 2) {
			JOptionPane.showMessageDialog(this.gui.getFrame(),
					"Could not create backup file. Try writing the changes again.\nYour executable has not been changed.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (this.gui.isHotA()) {
			Path hotaDAT = Paths.get(executable.getParent() + "/HotA.dat");
			
			errorCode = H3ExecutableHandler.createBackup(hotaDAT);			
			if (errorCode == 1) {
				JOptionPane.showMessageDialog(this.gui.getFrame(),
						"Could not create backup directory. Please create a directory \"backupHeroModder\" in your HotA folder and try again.\nYour files have not been changed.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (errorCode == 2) {
				JOptionPane.showMessageDialog(this.gui.getFrame(),
						"Could not create backup file. Try writing the changes again.\nYour files have not been changed.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		errorCode = H3ExecutableHandler.writeAllChanges(changes, executable);
		if (errorCode == 0) {
			JOptionPane.showMessageDialog(this.gui.getFrame(),
					String.format("Successfully written %d changes to the executable.", changes.size()), "Success",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (errorCode == 1) {
			JOptionPane.showMessageDialog(this.gui.getFrame(),
					"Executable was null. This should never happen.\nYour executable has not been changed.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (errorCode == 2) {
			JOptionPane.showMessageDialog(this.gui.getFrame(),
					"Executable does not exist. This should never happen.\nYour executable has not been changed.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (errorCode == 3) {
			JOptionPane.showMessageDialog(this.gui.getFrame(),
					"Executable path links to a directory. This should never happen.\nYour executable has not been changed.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (errorCode == 4) {
			JOptionPane.showMessageDialog(this.gui.getFrame(),
					"Fatal error during the writing process.\nPlease restore the files from the backup folder before playing.",
					"Fatal Error", JOptionPane.ERROR);
			return;
		}
	}

}
