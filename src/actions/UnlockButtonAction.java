package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import gui.HotAHeroEditor;

public class UnlockButtonAction extends AbstractAction {

	private static final long serialVersionUID = 150793036059703737L;
	HotAHeroEditor gui;
	
	public UnlockButtonAction(HotAHeroEditor gui) {
		putValue(Action.NAME, "Unlock");
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.gui.getBtnUnlock().getText().equals("Unlock")) {
			this.gui.getBtnUnlock().setText("Lock");
			this.gui.getBtnWrite().setEnabled(true);
		} else {
			this.gui.getBtnUnlock().setText("Unlock");
			this.gui.getBtnWrite().setEnabled(false);
		}
	}
}
