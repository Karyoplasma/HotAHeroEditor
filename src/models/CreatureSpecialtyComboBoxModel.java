package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import core.enums.Creature;

public class CreatureSpecialtyComboBoxModel extends AbstractListModel<Creature> implements ComboBoxModel<Creature> {

	private static final long serialVersionUID = -2673153398785414337L;
	List<Creature> creatureList;
	Creature selected;

	public CreatureSpecialtyComboBoxModel(boolean isHota) {
		this.creatureList = new ArrayList<Creature>();
		creatureList.add(Creature.PIKEMAN);
		creatureList.add(Creature.ARCHER);
		creatureList.add(Creature.GRIFFIN);
		creatureList.add(Creature.SWORDSMAN);
		creatureList.add(Creature.MONK);
		creatureList.add(Creature.CAVALIER);
		creatureList.add(Creature.ANGEL);
		creatureList.add(Creature.CENTAUR);
		creatureList.add(Creature.DWARF);
		creatureList.add(Creature.WOOD_ELF);
		creatureList.add(Creature.PEGASUS);
		creatureList.add(Creature.DENDROID_GUARD);
		creatureList.add(Creature.UNICORN);
		creatureList.add(Creature.GREEN_DRAGON);
		creatureList.add(Creature.GREMLIN);
		creatureList.add(Creature.STONE_GARGOYLE);
		creatureList.add(Creature.STONE_GOLEM);
		creatureList.add(Creature.MAGE);
		creatureList.add(Creature.GENIE);
		creatureList.add(Creature.NAGA);
		creatureList.add(Creature.GIANT);
		creatureList.add(Creature.IMP);
		creatureList.add(Creature.GOG);
		creatureList.add(Creature.HELL_HOUND);
		creatureList.add(Creature.DEMON);
		creatureList.add(Creature.PIT_FIEND);
		creatureList.add(Creature.EFREETI);
		creatureList.add(Creature.DEVIL);
		creatureList.add(Creature.SKELETON);
		creatureList.add(Creature.WALKING_DEAD);
		creatureList.add(Creature.WIGHT);
		creatureList.add(Creature.VAMPIRE);
		creatureList.add(Creature.LICH);
		creatureList.add(Creature.BLACK_KNIGHT);
		creatureList.add(Creature.BONE_DRAGON);
		creatureList.add(Creature.TROGLODYTE);
		creatureList.add(Creature.HARPY);
		creatureList.add(Creature.BEHOLDER);
		creatureList.add(Creature.MEDUSA);
		creatureList.add(Creature.MINOTAUR);
		creatureList.add(Creature.MANTICORE);
		creatureList.add(Creature.RED_DRAGON);
		creatureList.add(Creature.GOBLIN);
		creatureList.add(Creature.WOLF_RIDER);
		creatureList.add(Creature.ORC);
		creatureList.add(Creature.OGRE);
		creatureList.add(Creature.ROC);
		creatureList.add(Creature.CYCLOPS);
		creatureList.add(Creature.BEHEMOTH);
		creatureList.add(Creature.GNOLL);
		creatureList.add(Creature.LIZARDMAN);
		creatureList.add(Creature.GORGON);
		creatureList.add(Creature.SERPENT_FLY);
		creatureList.add(Creature.BASILISK);
		creatureList.add(Creature.WYVERN);
		creatureList.add(Creature.HYDRA);
		creatureList.add(Creature.PIXIE);
		creatureList.add(Creature.AIR_ELEMENTAL);
		creatureList.add(Creature.WATER_ELEMENTAL);
		creatureList.add(Creature.FIRE_ELEMENTAL);
		creatureList.add(Creature.EARTH_ELEMENTAL);
		creatureList.add(Creature.PSYCHIC_ELEMENTAL);
		creatureList.add(Creature.FIREBIRD);
		if (isHota) {
			creatureList.add(Creature.NYMPH);
			creatureList.add(Creature.CREW_MATE);
			creatureList.add(Creature.PIRATE);
			creatureList.add(Creature.STORMBIRD);
			creatureList.add(Creature.SEA_WITCH);
			creatureList.add(Creature.NIX);
			creatureList.add(Creature.SEA_SERPENT);
			creatureList.add(Creature.HALFLING);
			creatureList.add(Creature.MECHANIC);
			creatureList.add(Creature.ARMADILLO);
			creatureList.add(Creature.AUTOMATON);
			creatureList.add(Creature.SANDWORM);
			creatureList.add(Creature.GUNSLINGER);
			creatureList.add(Creature.COUATL);
			creatureList.add(Creature.DREADNOUGHT);
			creatureList.add(Creature.STEEL_GOLEM);
		}
		creatureList.add(Creature.GOLD_GOLEM);
		creatureList.add(Creature.DIAMOND_GOLEM);
		creatureList.add(Creature.PEASANT);
		if (isHota) {
			creatureList.add(Creature.LEPRECHAUN);
		}
		creatureList.add(Creature.BOAR);
		creatureList.add(Creature.MUMMY);
		creatureList.add(Creature.NOMAD);
		creatureList.add(Creature.ROGUE);
		creatureList.add(Creature.TROLL);
		if (isHota) {
			creatureList.add(Creature.FANGARM);
			creatureList.add(Creature.SATYR);
		}
		creatureList.add(Creature.ENCHANTER);
		creatureList.add(Creature.SHARPSHOOTER);
		creatureList.add(Creature.FAERIE_DRAGON);
		creatureList.add(Creature.RUST_DRAGON);
		creatureList.add(Creature.CRYSTAL_DRAGON);
		creatureList.add(Creature.AZURE_DRAGON);
		creatureList.add(Creature.BALLISTA);
		if (isHota) {
			creatureList.add(Creature.CANNON);
		}
	}

	@Override
	public int getSize() {
		return creatureList.size();
	}

	@Override
	public Creature getElementAt(int index) {
		return creatureList.get(index);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selected = (Creature) anItem;

	}

	@Override
	public Object getSelectedItem() {
		return selected;
	}

}
