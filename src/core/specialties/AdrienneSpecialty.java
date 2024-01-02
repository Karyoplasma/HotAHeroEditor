package core.specialties;

import java.nio.ByteBuffer;

import core.Specialty;
import core.enums.SpecialtyType;

public class AdrienneSpecialty implements Specialty {

	public AdrienneSpecialty() {
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 7);
		buffer.putInt(0xFFFFFFFF);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.flip();
		return buffer;
	}

	@Override
	public SpecialtyType getType() {
		return SpecialtyType.ADRIENNE_SPECIALTY;
	}
	
	@Override
	public String toString() {
		return SpecialtyType.ADRIENNE_SPECIALTY.toString();
	}

}
