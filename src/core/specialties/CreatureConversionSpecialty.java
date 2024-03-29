package core.specialties;

import java.nio.ByteBuffer;

import core.Specialty;
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
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		if (this.allowed2 != null) {
			buffer.putInt(this.allowed2.getBytes());
		} else {
			// Azure Dragon if no second creature
			buffer.putInt(0x84000000);
		}
		
		buffer.putInt(this.result.getBytes());
		buffer.flip();
		return buffer;
	}
	
	public Creature getFirstAllowed() {
		return allowed1;
	}
	
	public Creature getSecondAllowed() {
		return allowed2;
	}
	
	public Creature getResult() {
		return result;
	}
	
	@Override
	public SpecialtyType getType() {
		return SpecialtyType.CREATURE_CONVERSION;
	}
	
	@Override
	public boolean isHotaOnly() {
		return allowed1.hotaOnly() || allowed2.hotaOnly() || result.hotaOnly();
	}
	
	@Override
	public String toString() {
		return this.allowed1.toString() + " or " + this.allowed2.toString() + " --> " + this.result.toString();
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
