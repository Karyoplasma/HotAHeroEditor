package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import core.H3ExecutableHandler;
import core.Hero;
import core.enums.HeroHeader;
import gui.HotAHeroEditor;

public class OpenExecutableButtonAction extends AbstractAction {

	private static final long serialVersionUID = -3489967101763061385L;
	private Path saveDirectory;
	private HotAHeroEditor gui;
	
	public OpenExecutableButtonAction(Path saveDirectory, HotAHeroEditor hotAHeroEditor) {
		putValue(Action.NAME, "Open executable");
		this.saveDirectory = saveDirectory;
		this.gui = hotAHeroEditor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Path executable = this.getH3Executable();
		if (executable != null) {
			this.gui.setExecutable(executable);
			this.readExecutable(executable, gui.isHotA());
		}
	}

	private Path getH3Executable() {
		File executable;
		boolean saveDirectoyPreference = false;
		this.saveDirectory = gui.getSaveDirectory();
		if (this.saveDirectory == null) {
			executable = new File(System.getProperty("user.dir"));
			this.saveDirectory = executable.toPath();
			saveDirectoyPreference = true;
		} else {
			executable = saveDirectory.toFile();
		}

		JFileChooser fileChooser = new JFileChooser("Select Heroes 3 or HotA executable");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Executables", "exe"));
		fileChooser.setCurrentDirectory(executable.isDirectory() ? executable : executable.getParentFile());

		int ret = fileChooser.showOpenDialog(gui.getFrame());
		if (ret == (JFileChooser.APPROVE_OPTION)) {
			executable = fileChooser.getSelectedFile();
			if (!(executable.getName().toLowerCase().matches("h3hota(\\shd)?.exe")
					|| executable.getName().toLowerCase().matches("heroes3(\\shd)?.exe"))) {
				int option = JOptionPane.showConfirmDialog(gui.getFrame(),
						"The selected file does not match the expected filename.\nDo you want to select a different file?",
						"Incorrect File", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					return this.getH3Executable();
				} else {
					return null;
				}
			}
		} else {
			return null;
		}
		if (executable.getName().toLowerCase().matches("heroes3(\\shd)?.exe")){
			gui.setIsHotA(false);
		}
		if (executable.getName().toLowerCase().matches("h3hota(\\shd)?.exe")) {
			this.gui.setIsHotA(true);
		}
		if (!executable.getParentFile().getAbsolutePath().equals(saveDirectory.toAbsolutePath().toString())) {
			saveDirectoyPreference = true;
		}
		
		if (saveDirectoyPreference && gui.savePathPreference()) {
			this.saveDirectoryPreference(executable.toPath());
		}
		
		return executable.toPath();

	}

	private void saveDirectoryPreference(Path path) {
		Path directory = path.getParent();
		String content = directory.toString();
		Path preferenceFilePath = Paths.get("resources/directoryPath.txt");
		if (!preferenceFilePath.getParent().toFile().exists()) {
			preferenceFilePath.getParent().toFile().mkdir();
		}
		try {
			Files.write(preferenceFilePath, content.getBytes(), StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
			this.gui.setSaveDirectory(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void readExecutable(Path executable, boolean isHotA) {
		List<Hero> heroes = new ArrayList<Hero>();
		for (HeroHeader header : HeroHeader.values()) {
			header.resetOffsetChanged();
		}
		heroes = H3ExecutableHandler.readHeroes(executable, isHotA);
		if (heroes.isEmpty()) {
			JOptionPane.showMessageDialog(gui.getFrame(),
					"There was an error when parsing the hero and creature data. If HotA was recently updated there might have been a change in the .dat file structure.\nError Message: The heroes list is empty.",
					"Fatal error", JOptionPane.ERROR_MESSAGE);
		}
		gui.resetHeroes(heroes);
	}
}
