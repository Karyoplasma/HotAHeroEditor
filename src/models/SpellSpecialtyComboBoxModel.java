package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import core.enums.Spell;

public class SpellSpecialtyComboBoxModel extends AbstractListModel<Spell> implements ComboBoxModel<Spell> {

	private static final long serialVersionUID = 2576571488974549388L;
	private List<Spell> spells;
	private Spell selected;

	public SpellSpecialtyComboBoxModel() {
		this.spells = new ArrayList<Spell>();
		spells.add(Spell.AIR_SHIELD);
		spells.add(Spell.ANIMATE_DEAD);
		spells.add(Spell.BLESS);
		spells.add(Spell.BLOODLUST);
		spells.add(Spell.CHAIN_LIGHTNING);
		spells.add(Spell.CLONE);
		spells.add(Spell.CURE);
		spells.add(Spell.DEATH_RIPPLE);
		spells.add(Spell.DISRUPTING_RAY);
		spells.add(Spell.FIRE_WALL);
		spells.add(Spell.FIREBALL);
		spells.add(Spell.FORTUNE);
		spells.add(Spell.FORGETFULNESS);
		spells.add(Spell.FRENZY);
		spells.add(Spell.FROST_RING);
		spells.add(Spell.HASTE);
		spells.add(Spell.HYPNOTIZE);
		spells.add(Spell.ICE_BOLT);
		spells.add(Spell.INFERNO);
		spells.add(Spell.LAND_MINE);
		spells.add(Spell.LIGHTNING_BOLT);
		spells.add(Spell.MAGIC_ARROW);
		spells.add(Spell.METEOR_SHOWER);
		spells.add(Spell.PRECISION);
		spells.add(Spell.PRAYER);
		spells.add(Spell.RESURRECTION);
		spells.add(Spell.SLAYER);
		spells.add(Spell.STONE_SKIN);
		spells.add(Spell.WEAKNESS);	
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
