package models;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.Hero;
import gui.HotAHeroEditor;

public class ChangesTableModel extends AbstractTableModel {
	private static final Logger logger = LogManager.getLogger(ChangesTableModel.class);
	private static final long serialVersionUID = -6106115281402023971L;
	List<Hero> changedHeroes;

	HotAHeroEditor gui;
	private String[] columnNames = { "Hero", "Vanilla", "Changed" };

	public ChangesTableModel(HotAHeroEditor hotAHeroEditor, List<Hero> heroes) {
		logger.info("Initializing ChangesTableModel...");
		this.gui = hotAHeroEditor;
		this.changedHeroes = new ArrayList<Hero>();
		for (Hero hero : heroes) {
			if (hero.isChanged(this.gui.getOriginalHero(hero.getName()))) {
				this.changedHeroes.add(hero);
			}
		}
		this.gui.getBtnWrite().setEnabled(false);
		this.gui.getBtnUnlock().setEnabled(true);
		if (heroes.isEmpty()) {
			this.gui.getBtnLoad().setEnabled(false);
			this.gui.getBtnChange().setEnabled(false);
		} else {
			this.gui.getBtnLoad().setEnabled(true);
			this.gui.getBtnChange().setEnabled(true);
		}	
		if (!this.changedHeroes.isEmpty()) {
			this.gui.getBtnSave().setEnabled(true);		
			this.gui.getBtnDiscardAll().setEnabled(true);
			this.resize();
		} else {
			this.gui.getBtnSave().setEnabled(false);
			this.gui.getBtnDiscardAll().setEnabled(false);
		}
		logger.debug(String.format("Initialized ChangesTableModel with %d changes.", this.changedHeroes.size()));
	}

	public void resize() {
		this.resizeChangesColumnWidth();
	}
	
	public int loadChanges(List<Hero> changes) {
		logger.info("Loading changes from disk...");
		int notLoaded = 0;
		for (Hero changed : this.changedHeroes) {
			Hero heroOriginal = new Hero(this.gui.getOriginalHero(changed.getName()));
			changed.setSpecialty(heroOriginal.getSpecialty());
			changed.setFirstSkill(heroOriginal.getFirstSkill());
			changed.setSecondSkill(heroOriginal.getSecondSkill());
			changed.setSpellBook(heroOriginal.getSpellBook());
			changed.setStartingTroops(heroOriginal.getStartingTroops());		
		}
		this.changedHeroes.clear();
		for (Hero hero : changes) {
			//if (hero.isHotaOnly() && !this.gui.isHotA()) {
			if (!hero.isCompatibleWithVersion(gui)) {
				notLoaded++;
			} else {
				Hero listedHero = this.gui.getComboBoxHero().getItemAt(hero.getHeader().ordinal());
				listedHero.setTo(hero);
				changedHeroes.add(listedHero);
			}
		}
		if (!changedHeroes.isEmpty()) {
			this.gui.getBtnDiscardAll().setEnabled(true);
			this.gui.getBtnSave().setEnabled(true);
			this.resize();
			this.gui.forceHeroBoxItemEventTrigger(changedHeroes.get(0));
			this.gui.getFrame().repaint();
		} else {
			logger.warn("Loaded empty changes file.");
			this.gui.getBtnDiscardAll().setEnabled(false);
			this.gui.getBtnSave().setEnabled(false);
			this.gui.getFrame().repaint();
		}
		logger.info(String.format("%d changes loaded, %d discarded.", changedHeroes.size(), notLoaded));
		return notLoaded;
	}

	public void discardAll() {
		logger.info("Discarding all changes...");
		for (Hero changed : this.changedHeroes) {
			Hero heroOriginal = new Hero(this.gui.getOriginalHero(changed.getName()));
			changed.setSpecialty(heroOriginal.getSpecialty());
			changed.setFirstSkill(heroOriginal.getFirstSkill());
			changed.setSecondSkill(heroOriginal.getSecondSkill());
			changed.setSpellBook(heroOriginal.getSpellBook());
			changed.setStartingTroops(heroOriginal.getStartingTroops());		
		}
		this.changedHeroes.clear();
		this.gui.getBtnDiscardAll().setEnabled(false);
		this.gui.getBtnSave().setEnabled(false);
		this.gui.getBtnWrite().setEnabled(false);
		this.gui.getBtnUnlock().setEnabled(true);
		this.resize();
		this.gui.getComboBoxHero().setSelectedIndex(-1);
		this.gui.getComboBoxHero().setSelectedIndex(0);
		this.gui.getFrame().repaint();
	}

	public List<Hero> getChanges() {
		return this.changedHeroes;
	}

	public void proposeChange(Hero hero) {
		Hero original = this.gui.getOriginalHero(hero.getName());
		Hero currentChange = this.searchChangesFor(hero);
		if (currentChange == null) {
			if (hero.isChanged(original)) {
				logger.debug("Adding hero change for " + hero.toString());
				this.changedHeroes.add(hero);
				this.gui.getBtnDiscardAll().setEnabled(true);
				this.gui.getBtnSave().setEnabled(true);
				this.gui.getBtnUnlock().setEnabled(true);
				this.resize();
				fireTableDataChanged();
				return;
			} else {
				return;
			}
		} else {
			if (!hero.isChanged(original)) {
				this.changedHeroes.remove(currentChange);
				logger.debug("Removed hero change for " + currentChange.toString());
				if (this.changedHeroes.isEmpty()) {
					this.gui.getBtnDiscardAll().setEnabled(false);
					this.gui.getBtnSave().setEnabled(false);
					this.gui.getBtnWrite().setEnabled(false);
				}
				this.resize();
				fireTableDataChanged();				
				return;
			} else {
				this.resize();
				fireTableDataChanged();
				return;
			}
		}
	}

	private Hero searchChangesFor(Hero hero) {
		for (Hero changed : this.changedHeroes) {
			if (changed.equals(hero)) {
				return changed;
			}
		}
		return null;
	}

	@Override
	public int getRowCount() {
		return changedHeroes.size() * 5;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return String.class;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Hero hero = this.changedHeroes.get(rowIndex / 5);
		Hero original = this.gui.getOriginalHero(hero.getName());
		String temp = "";
		switch (columnIndex) {
		case 0:
			if ((rowIndex % 5) == 0) {
				return hero.getName();
			} else {
				return "";
			}
		case 1:
			switch (rowIndex % 5) {
			case 0:
				return original.getSpecialty().toString();
			case 1:
				return original.getFirstSkill().toString();
			case 2:
				return original.getSecondSkill().toString();
			case 3:
				return original.getSpellBook().toString();
			case 4:
				temp = Arrays.toString(original.getStartingTroops());
				return temp.substring(1, temp.length() - 1);
			default:
				return "";
			}
		case 2:
			switch (rowIndex % 5) {
			case 0:
				return hero.getSpecialty().toString();
			case 1:
				return hero.getFirstSkill().toString();
			case 2:
				return hero.getSecondSkill().toString();
			case 3:
				return hero.getSpellBook().toString();
			case 4:
				temp = Arrays.toString(hero.getStartingTroops());
				return temp.substring(1, temp.length() - 1);
			default:
				return "";
			}
		default:
			return "";
		}
	}

	private void resizeChangesColumnWidth() {
		JTable table = gui.getTableChanges();
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 15; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			if (width > 400)
				width = 400; // Max width
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}
}
