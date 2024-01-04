package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import gui.HotAHeroEditor;
import models.ChangesTableModel;

public class DiscardAllButtonAction extends AbstractAction {

	private static final long serialVersionUID = 9031850457823821490L;
	HotAHeroEditor gui;

	public DiscardAllButtonAction(HotAHeroEditor gui) {
		putValue(Action.NAME, "Discard All");
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int confirmationResult = JOptionPane.showConfirmDialog(this.gui.getFrame(),
				"Do you want to reset all changes to their vanilla values?", "Confirmation", JOptionPane.YES_NO_OPTION);
		if (confirmationResult == JOptionPane.YES_OPTION) {
			((ChangesTableModel) this.gui.getTableChanges().getModel()).discardAll();
		} else {
			return;
		}
	}

}
