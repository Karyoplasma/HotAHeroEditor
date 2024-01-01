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
			return this.creature.getPlural() + (" (static)");
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
