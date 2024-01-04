package core.specialties;

import java.nio.ByteBuffer;

import core.Specialty;
import core.enums.SpecialtyType;

public class FrederickSpecialty implements Specialty {

	public FrederickSpecialty() {
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 7);
		buffer.putInt(SpecialtyType.FREDERICK_SPECIALTY.getBytes());
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
		return SpecialtyType.FREDERICK_SPECIALTY;
	}
	
	@Override
	public boolean isHotaOnly() {
		return true;
	}
	
	@Override
	public String toString() {
		return SpecialtyType.FREDERICK_SPECIALTY.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof FrederickSpecialty);
	}
}
