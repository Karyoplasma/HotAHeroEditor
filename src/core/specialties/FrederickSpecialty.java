package core.specialties;

import java.nio.ByteBuffer;

import core.enums.SpecialtyType;

public class FrederickSpecialty implements Specialty {

	public FrederickSpecialty() {
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
		buffer.putInt(SpecialtyType.FREDERICK_SPECIALTY.getBytes());
		buffer.flip();
		return buffer;
	}

	@Override
	public SpecialtyType getType() {
		return SpecialtyType.FREDERICK_SPECIALTY;
	}
	
	@Override
	public String toString() {
		return SpecialtyType.FREDERICK_SPECIALTY.toString();
	}

}
