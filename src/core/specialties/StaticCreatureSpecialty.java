package core.specialties;

import java.nio.ByteBuffer;

import core.Specialty;
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
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 7);
		buffer.putInt(this.getType().getBytes());
		buffer.putInt(this.creature.getBytes());
		buffer.putInt(this.attack);
		buffer.putInt(this.defense);
		buffer.putInt(this.damage);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.flip();
		return buffer;
	}
	
	public Creature getCreature() {
		return creature;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public int getDefense() {
		return defense;
	}
	
	public int getDamage() {
		return damage;
	}
	@Override
	public SpecialtyType getType() {
		return SpecialtyType.CREATURE_BONUS_STATIC;
	}
	
	@Override
	public boolean isHotaOnly() {
		return creature.hotaOnly();
	}
	
	@Override
	public String toString() {
		return String.format("%s +%d/+%d/+%d", this.creature.getPlural(), Integer.reverseBytes(this.attack),
				Integer.reverseBytes(this.defense), Integer.reverseBytes(this.damage));
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
