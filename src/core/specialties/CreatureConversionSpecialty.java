package core.specialties;

import java.nio.ByteBuffer;

import core.enums.Creature;
import core.enums.SpecialtyType;

public class CreatureConversionSpecialty implements Specialty {
	private Creature allowed1, allowed2, result;
	
	public CreatureConversionSpecialty(Creature allowed1, Creature allowed2, Creature result) {
		this.allowed1 = allowed1;
		this.allowed2 = allowed2;
		this.result = result;
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 7);
		buffer.putInt(this.getType().getBytes());
		buffer.putInt(this.allowed1.getBytes());
		// TODO verify;
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		if (this.allowed2 != null) {
			buffer.putInt(this.allowed2.getBytes());
		} else {
			buffer.putInt(0x00000000);
		}
		
		buffer.putInt(this.result.getBytes());
		buffer.flip();
		return buffer;
	}

	@Override
	public SpecialtyType getType() {
		return SpecialtyType.CREATURE_CONVERSION;
	}
	
	@Override
	public String toString() {
		return this.result.toString() + " conversion";
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CreatureConversionSpecialty)) {
			return false;
		}
		CreatureConversionSpecialty other = (CreatureConversionSpecialty) o;
		return (allowed1 == other.allowed1) && (allowed2 == other.allowed2) && (result == other.result);
	}
}
