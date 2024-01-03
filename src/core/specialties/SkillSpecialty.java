package core.specialties;

import java.nio.ByteBuffer;

import core.Specialty;
import core.enums.HeroTrait;
import core.enums.SpecialtyType;

public class SkillSpecialty implements Specialty {
	
	private HeroTrait skill;
	
	public SkillSpecialty(HeroTrait skill) {
		this.skill = skill;
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 7);
		buffer.putInt(this.getType().getBytes());
		buffer.putInt(this.skill.getBytes());
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.flip();
		return buffer;
	}
	
	public HeroTrait getSkill() {
		return skill;
	}
	
	@Override
	public SpecialtyType getType() {
		return SpecialtyType.SKILL_BONUS;
	}
	
	@Override
	public String toString() {
		return skill.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SkillSpecialty)) {
			return false;
		}
		SkillSpecialty other = (SkillSpecialty) o;
		return skill == other.skill;
	}
	
}
