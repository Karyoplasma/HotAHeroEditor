package core.specialties;

import java.nio.ByteBuffer;

import core.Specialty;
import core.enums.SpecialtyType;

public class CreatureSpeedSpecialty implements Specialty {
	
	private int bonus;
	
	public CreatureSpeedSpecialty(int bonus) {
		this.bonus = bonus;
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 7);
		buffer.putInt(this.getType().getBytes());
		buffer.putInt(this.bonus);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.flip();
		return buffer;
	}
	
	public int getBonus() {
		return bonus;
	}
	
	@Override
	public SpecialtyType getType() {
		return SpecialtyType.CREATURE_BONUS_SPEED;
	}
	
	@Override
	public boolean isHotaOnly() {
		return false;
	}
	
	@Override
	public String toString() {
		return "Creature speed +" + Integer.reverseBytes(this.bonus);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CreatureSpeedSpecialty)) {
			return false;
		}
		CreatureSpeedSpecialty other = (CreatureSpeedSpecialty) o;
		return bonus == other.bonus;
	}
	
}
