package core.specialties;

import java.nio.ByteBuffer;

import core.enums.SpecialtyType;

public class AdrienneSpecialty implements Specialty {

	public AdrienneSpecialty() {
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
		buffer.putInt(0xFFFFFFFF);
		buffer.flip();
		return buffer;
	}

	@Override
	public SpecialtyType getType() {
		return SpecialtyType.ADRIENNE;
	}
	
	@Override
	public String toString() {
		return SpecialtyType.ADRIENNE.toString();
	}

}
