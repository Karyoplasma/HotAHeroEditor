package actions;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gui.HotAHeroEditor;

public class OpenGameDirectoryMenuAction extends AbstractAction {
	
	private static final Logger logger = LogManager.getLogger(OpenGameDirectoryMenuAction.class);
	private static final long serialVersionUID = 7633518770465730504L;
	private HotAHeroEditor gui;
	
	public OpenGameDirectoryMenuAction(HotAHeroEditor gui) {
		putValue(Action.NAME, "Open game directory");
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(this.gui.getSaveDirectory().toFile());
            } catch (IOException ex) {
                logger.error("Error opening the directory:", ex);
            }
        } else {
            logger.warn("Desktop is not supported, cannot open the directory.");
        }

	}

}
