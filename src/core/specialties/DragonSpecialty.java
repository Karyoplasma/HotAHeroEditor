package core.specialties;

import java.nio.ByteBuffer;

import core.enums.SpecialtyType;

public class DragonSpecialty implements Specialty {

	private int attack, defense;

	public DragonSpecialty(int attack, int defense) {
		this.attack = attack;
		this.defense = defense;
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 7);
		buffer.putInt(this.getType().getBytes());
		buffer.putInt(0x00000000);
		buffer.putInt(this.attack);
		buffer.putInt(this.defense);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.flip();
		return buffer;
	}

	@Override
	public SpecialtyType getType() {
		return SpecialtyType.DRAGON_BONUS;
	}

	@Override
	public String toString() {
		return "Dragons";
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof DragonSpecialty)) {
			return false;
		}
		DragonSpecialty other = (DragonSpecialty) o;
		return (attack == other.attack) && (defense == other.defense);
	}
	
}
