package core.specialties;

import java.nio.ByteBuffer;

import core.enums.Resource;
import core.enums.SpecialtyType;

public class ResourceSpecialty implements Specialty {
	
	Resource resource;
	
	public ResourceSpecialty(Resource resource) {
		this.resource = resource;
	}
	
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 2);
		buffer.putInt(this.getType().getBytes());
		buffer.putInt(this.resource.getBytes());
		buffer.flip();
		return buffer;
	}

	@Override
	public SpecialtyType getType() {
		return SpecialtyType.RESOURCE_BONUS;
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

}
