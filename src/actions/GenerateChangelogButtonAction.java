package actions;

import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import core.Hero;
import gui.HotAHeroEditor;
import models.ChangesTableModel;

public class GenerateChangelogButtonAction extends AbstractAction {
	private static final Logger logger = LogManager.getLogger(GenerateChangelogButtonAction.class);
	private final String newLine = System.getProperty("line.separator");
	private static final long serialVersionUID = -6399493799599047583L;
	private HotAHeroEditor gui;

	public GenerateChangelogButtonAction(HotAHeroEditor gui) {
		putValue(Action.NAME, "Generate Changelog");
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<Hero> changes = ((ChangesTableModel) gui.getTableChanges().getModel()).getChanges();
		if (changes == null || changes.isEmpty()) {
			logger.info("No changes to generate a log from.");
		}

		List<Hero> specialtyChanges = new ArrayList<Hero>();
		List<Hero> skillChanges = new ArrayList<Hero>();
		List<Hero> spellChanges = new ArrayList<Hero>();
		List<Hero> startingArmyChanges = new ArrayList<Hero>();

		for (Hero hero : changes) {
			if (!hero.getFirstSkill().equals(gui.getOriginalHero(hero.getName()).getFirstSkill())
					|| !hero.getSecondSkill().equals(gui.getOriginalHero(hero.getName()).getSecondSkill())) {
				skillChanges.add(hero);
			}
			if (hero.getSpell() != gui.getOriginalHero(hero.getName()).getSpell()) {
				spellChanges.add(hero);
			}
			if (!hero.getSpecialty().equals(gui.getOriginalHero(hero.getName()).getSpecialty())) {
				specialtyChanges.add(hero);
			}
			if (!Arrays.equals(hero.getStartingTroops(), gui.getOriginalHero(hero.getName()).getStartingTroops())) {
				startingArmyChanges.add(hero);
			}
		}

		StringBuilder builder = new StringBuilder("Changes:");
		builder.append(newLine).append(newLine);

		if (!skillChanges.isEmpty()) {
			builder.append("Starting skills:").append(newLine);
			for (Hero hero : skillChanges) {
				builder.append(hero.getName()).append(": ");
				builder.append(gui.getOriginalHero(hero.getName()).getFirstSkill()).append(", ");
				builder.append(gui.getOriginalHero(hero.getName()).getSecondSkill()).append(" --> ");
				builder.append(hero.getFirstSkill()).append(", ").append(hero.getSecondSkill());
				builder.append(newLine);
			}
			builder.append(newLine);
		}
		if (!specialtyChanges.isEmpty()) {
			builder.append("Specialties:").append(newLine);
			for (Hero hero : specialtyChanges) {
				builder.append(hero.getName()).append(": ");
				builder.append(gui.getOriginalHero(hero.getName()).getSpecialty()).append(" --> ");
				builder.append(hero.getSpecialty());
				builder.append(newLine);
			}
			builder.append(newLine);
		}

		if (!startingArmyChanges.isEmpty()) {
			builder.append("Starting Troops:").append(newLine);
			for (Hero hero : startingArmyChanges) {
				builder.append(hero.getName()).append(": ");
				String temp = Arrays.toString(gui.getOriginalHero(hero.getName()).getStartingTroops());
				builder.append(temp.substring(1, temp.length() - 1));
				builder.append(" --> ");
				temp = Arrays.toString(hero.getStartingTroops());
				builder.append(temp.substring(1, temp.length() - 1));
				builder.append(newLine);
			}
			builder.append(newLine);
		}

		if (!spellChanges.isEmpty()) {
			builder.append("Spells:").append(newLine);
			for (Hero hero : spellChanges) {
				builder.append(hero.getName()).append(": ");
				builder.append(gui.getOriginalHero(hero.getName()).getSpell()).append(" --> ");
				builder.append(hero.getSpell());
				builder.append(newLine);
			}
		}

		Path fileName = Paths.get("changes.txt");
		try (FileWriter writer = new FileWriter(fileName.toString())) {
			writer.write(builder.toString());
			JOptionPane.showMessageDialog(this.gui.getFrame(), "Changelog written to: " + fileName.toAbsolutePath(),
					"Info", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException ex) {
			logger.error("Error while writing changelog to disk: " + ex.getMessage());
			JOptionPane.showMessageDialog(this.gui.getFrame(), "Error while writing changelog to disk!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

}
