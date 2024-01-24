package actions;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import core.Hero;
import core.ModFileHandler;
import gui.HotAHeroEditor;
import models.ChangesTableModel;

public class ExportButtonAction extends AbstractAction {

	private static final long serialVersionUID = -4803017960981133994L;
	private HotAHeroEditor gui;

	public ExportButtonAction(HotAHeroEditor gui) {
		putValue(Action.NAME, "Export");
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<Hero> changes = ((ChangesTableModel) this.gui.getTableChanges().getModel()).getChanges();
		String filename = ModFileHandler.writeModFileToDisk(changes);
		if (!filename.equals("Could not write changes to disk.") && !filename.equals("No parent folder.")) {
			JOptionPane.showMessageDialog(this.gui.getFrame(),
					String.format("%d changes have been written to:\n%s", changes.size(), filename), "Success",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this.gui.getFrame(), filename, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
