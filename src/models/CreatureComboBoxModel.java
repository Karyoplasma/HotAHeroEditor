package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import core.enums.Creature;

public class CreatureComboBoxModel extends AbstractListModel<Creature> implements ComboBoxModel<Creature> {

	private static final long serialVersionUID = 5334568425374864634L;
	private List<Creature> creatures;
	private Creature selected;
	
	public CreatureComboBoxModel(boolean isHotA, boolean isSecondSlot) {
		this.creatures = new ArrayList<Creature>();
		for (Creature creature : Creature.values()) {
			if (creature.hotaOnly() && !isHotA) {
				continue;
			}
			this.creatures.add(creature);
		}
		this.creatures.remove(Creature.ARROW_TOWER);
		this.creatures.remove(Creature.CATAPULT);	
		if (isHotA) {
			this.creatures.remove(Creature.ELECTRIC_TOWER);
			this.creatures.remove(Creature.DEBUG);
		}
		if (!isSecondSlot) {
			this.creatures.remove(Creature.BALLISTA);
			this.creatures.remove(Creature.FIRST_AID_TENT);
			this.creatures.remove(Creature.AMMO_CART);
			if (isHotA) {
				this.creatures.remove(Creature.CANNON);
			}
		}
	}
	@Override
	public int getSize() {
		return this.creatures.size();
	}

	@Override
	public Creature getElementAt(int index) {
		return this.creatures.get(index);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		this.selected = (Creature) anItem;
		
	}

	@Override
	public Object getSelectedItem() {
		return selected;
	}

}
