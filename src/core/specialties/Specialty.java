package core.specialties;

import java.nio.ByteBuffer;
import core.enums.SpecialtyType;

public interface Specialty {
	public ByteBuffer getByteBuffer();
	public SpecialtyType getType();
}