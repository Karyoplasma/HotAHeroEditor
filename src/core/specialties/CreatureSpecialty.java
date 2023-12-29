package core.specialties;

import java.nio.ByteBuffer;

import core.enums.Creature;
import core.enums.SpecialtyType;

public class CreatureSpecialty implements Specialty {
	
	private Creature creature;
	
	public CreatureSpecialty(Creature creature) {
		this.creature = creature;
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 2);
		buffer.putInt(this.getType().getBytes());
		buffer.putInt(this.creature.getBytes());
		buffer.flip();
		return buffer;
	}

	@Override
	public SpecialtyType getType() {
		return SpecialtyType.CREATURE_BONUS_LEVEL;
	}
	
	@Override
	public String toString() {
		return this.creature.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CreatureSpecialty)) {
			return false;
		}
		CreatureSpecialty other = (CreatureSpecialty) o;
		return creature == other.creature;
	}
}
