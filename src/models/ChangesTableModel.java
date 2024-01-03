package models;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import core.Hero;
import gui.HotAHeroEditor;

public class ChangesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -6106115281402023971L;
	List<Hero> heroes;
	List<Hero> changedHeroes;
	
	HotAHeroEditor gui;
	private String[] columnNames = { "Hero", "Original", "Changes" };

	public ChangesTableModel(HotAHeroEditor hotAHeroEditor, List<Hero> heroes) {
		this.gui = hotAHeroEditor;
		this.heroes = heroes;
		this.changedHeroes = new ArrayList<Hero>();
	}

	public void initializeData() {
		fireTableStructureChanged();
		this.resizeChangesColumnWidth();
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
		Hero hero = heroes.get(rowIndex / 5);
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
				return "Specialty";
			case 1:
				return "First Skill";
			case 2:
				return "Second Skill";
			case 3:
				return "Spell Book";
			case 4:
				return "Starting troops";
			default:
				return "";
			}
		case 2:
			switch (rowIndex % 5) {
			case 0:
				return hero.getSpecialty();
			case 1:
				return hero.getFirstSkill();
			case 2:
				return hero.getSecondSkill();
			case 3:
				return hero.getSpellBook();
			case 4:
				return Arrays.toString(hero.getStartingTroops());
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
