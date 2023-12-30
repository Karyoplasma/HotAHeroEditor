package core.specialties;

import java.nio.ByteBuffer;

import core.enums.Creature;
import core.enums.SpecialtyType;

public class StaticCreatureSpecialty implements Specialty {

	private Creature creature;
	private int attack, defense, damage;

	public StaticCreatureSpecialty(Creature creature, int attack, int defense, int damage) {
		this.attack = attack;
		this.creature = creature;
		this.defense = defense;
		this.damage = damage;
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 5);
		buffer.putInt(this.getType().getBytes());
		buffer.putInt(this.creature.getBytes());
		buffer.putInt(this.attack);
		buffer.putInt(this.defense);
		buffer.putInt(this.damage);
		buffer.flip();
		return buffer;
	}

	@Override
	public SpecialtyType getType() {
		return SpecialtyType.CREATURE_BONUS_STATIC;
	}

	@Override
	public String toString() {
		switch (this.creature) {
		case PIKEMAN:
			return "Pikemen";
		case SWORDSMAN:
			return "Swordsmen";
		case DWARF:
			return "Dwarves";
		case PEGASUS:
			return "Pegasi";
		case MAGE:
			return "Magi";
		case EFREET:
			return "Efreeti";
		case LICH:
			return "Liches";
		case HARPY:
			return "Harpies";
		case CYCLOPS:
			return "Cyclopses";
		case LIZARDMAN:
			return "Lizardmen";
		case SERPENT_FLY:
			return "Serpent Flies";
		case PIXIES:
			return "Pixies";
		case BALLISTA:
			return "Ballista";
		default:
			return this.creature.toString() + "s";
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof StaticCreatureSpecialty)) {
			return false;
		}
		StaticCreatureSpecialty other = (StaticCreatureSpecialty) o;
		return (creature == other.creature) && (attack == other.attack) && (defense == other.defense)
				&& (damage == other.damage);
	}

}
