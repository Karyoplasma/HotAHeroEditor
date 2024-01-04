package core.specialties;

import java.nio.ByteBuffer;

import core.Specialty;
import core.enums.Resource;
import core.enums.SpecialtyType;

public class ResourceSpecialty implements Specialty {
	
	private Resource resource;
	
	public ResourceSpecialty(Resource resource) {
		this.resource = resource;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 7);
		buffer.putInt(this.getType().getBytes());
		buffer.putInt(this.resource.getBytes());
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
		return SpecialtyType.RESOURCE_BONUS;
	}
	
	@Override
	public boolean isHotaOnly() {
		return false;
	}
	
	@Override
	public String toString() {
		switch (this.resource) {
		case WOOD:
			return "+2 Wood";
		case MERCURY:
			return "+1 Mercury";
		case ORE:
			return "+2 Ore";
		case SULFUR:
			return "+1 Sulfur";
		case CRYSTAL:
			return "+1 Crystal";
		case GEM:
			return "+1 Gem";
		case GOLD:
			return "+350 Gold";
		default:
			return "";
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ResourceSpecialty)) {
			return false;
		}
		ResourceSpecialty other = (ResourceSpecialty) o;
		return resource == other.resource;
	}
}
