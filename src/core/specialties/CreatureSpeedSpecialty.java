package core.specialties;

import java.nio.ByteBuffer;

import core.enums.SpecialtyType;

public class CreatureSpeedSpecialty implements Specialty {
	
	private int bonus;
	public CreatureSpeedSpecialty(int bonus) {
		this.bonus = bonus;
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 2);
		buffer.putInt(this.getType().getBytes());
		buffer.putInt(this.bonus);
		buffer.flip();
		return buffer;
	}

	@Override
	public SpecialtyType getType() {
		return SpecialtyType.CREATURE_BONUS_SPEED;
	}
	
	@Override
	public String toString() {
		return "Creature speed +" + Integer.reverseBytes(this.bonus);
	}
}
