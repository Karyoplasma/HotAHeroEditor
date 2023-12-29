package core.specialties;

import java.nio.ByteBuffer;

import core.enums.HeroTrait;
import core.enums.SpecialtyType;

public class SkillSpecialty implements Specialty {
	
	private HeroTrait skill;
	
	public SkillSpecialty(HeroTrait skill) {
		this.skill = skill;
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 2);
		buffer.putInt(this.getType().getBytes());
		buffer.putInt(this.skill.getBytes());
		buffer.flip();
		return buffer;
	}

	@Override
	public SpecialtyType getType() {
		return SpecialtyType.SKILL_BONUS;
	}
	
	@Override
	public String toString() {
		return skill.toString();
	}
}
