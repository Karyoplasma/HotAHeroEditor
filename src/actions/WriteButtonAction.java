package actions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.H3ExecutableHandler;
import core.Hero;
import gui.HotAHeroEditor;
import models.ChangesTableModel;
import models.HeroComboBoxModel;

public class WriteButtonAction extends AbstractAction {
	
	private static final Logger logger = LogManager.getLogger(WriteButtonAction.class);
	private static final long serialVersionUID = -7880210436237200968L;
	HotAHeroEditor gui;

	public WriteButtonAction(HotAHeroEditor gui) {
		putValue(Action.NAME, "Write");
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<Hero> changes = ((ChangesTableModel) this.gui.getTableChanges().getModel()).getChanges();
		String timeStamp = Long.toString(Instant.now().getEpochSecond());
		
		Path executable = this.gui.getExecutable();
		if (changes.isEmpty()) {
			logger.debug("Attempting to write empty changes...");
			int result = JOptionPane.showConfirmDialog(this.gui.getFrame(), "Are you sure you want to reset to the original state?", "Confirmation", JOptionPane.YES_NO_OPTION);
	        if (result == JOptionPane.YES_OPTION) {
	            this.resetToOriginal(executable, timeStamp);
	            return;
	        } else {
	            logger.debug("Reset canceled by user.");
	            return;
	        }
		}
		int errorCode = H3ExecutableHandler.createBackup(executable, timeStamp);
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
			
			errorCode = H3ExecutableHandler.createBackup(hotaDAT, timeStamp);			
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
			Path h3exe = Paths.get(executable.getParent() + "/Heroes3.exe");
			
			errorCode = H3ExecutableHandler.createBackup(h3exe, timeStamp);			
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
					"Fatal Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	private void resetToOriginal(Path executable, String timeStamp) {
		int errorCode = H3ExecutableHandler.createBackup(executable, timeStamp);
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
			if (this.gui.isHotA()) {
				Path hotaDAT = Paths.get(executable.getParent() + "/HotA.dat");
				errorCode = H3ExecutableHandler.createBackup(hotaDAT, timeStamp);			
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
				Path h3exe = Paths.get(executable.getParent() + "/Heroes3.exe");
				errorCode = H3ExecutableHandler.createBackup(h3exe, timeStamp);			
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
		}
		List<Hero> vanillaHeroes = ((HeroComboBoxModel) this.gui.getComboBoxHero().getModel()).getHeroes();
		errorCode = H3ExecutableHandler.writeAllChanges(vanillaHeroes, executable);
		if (errorCode == 0) {
			JOptionPane.showMessageDialog(this.gui.getFrame(),
					"Executable reset to vanilla values.", "Success",
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
					"Fatal Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

}
