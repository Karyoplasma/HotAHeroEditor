package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import core.enums.Spell;

public class SpellComboBoxModel extends AbstractListModel<Spell> implements ComboBoxModel<Spell> {

	private static final long serialVersionUID = 8861276176493931863L;
	private List<Spell> spells;
	private Spell selected;
	
	public SpellComboBoxModel() {
		this.spells = new ArrayList<Spell>();
		for (Spell spell : Spell.values()) {
			this.spells.add(spell);
		}
		this.spells.remove(Spell.DEBUG);
	}
	
	@Override
	public int getSize() {
		return spells.size();
	}

	@Override
	public Spell getElementAt(int index) {
		return spells.get(index);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		this.selected = (Spell) anItem;		
	}

	@Override
	public Object getSelectedItem() {
		return selected;
	}

}
