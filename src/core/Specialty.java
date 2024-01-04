package core;

import java.nio.ByteBuffer;
import core.enums.SpecialtyType;

public interface Specialty {
	public ByteBuffer getByteBuffer();
	public SpecialtyType getType();
	public boolean isHotaOnly();
}
