package actions;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OpenModFolderMenuAction extends AbstractAction {
	private static final Logger logger = LogManager.getLogger(OpenModFolderMenuAction.class);
	private static final long serialVersionUID = 5676228725032135652L;
	
	public OpenModFolderMenuAction() {
		putValue(Action.NAME, "Open mod directory");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
            	File modDir = new File("mods");
                desktop.open(modDir);
            } catch (IOException ex) {
                logger.error("Error opening the directory:", ex);
            }
        } else {
            logger.warn("Desktop is not supported, cannot open the directory.");
        }


	}

}
