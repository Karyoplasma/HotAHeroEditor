package core;

import core.enums.HeroTrait;
import core.enums.SkillLevel;

public class SecondarySkill {
	
	private HeroTrait trait;
	private SkillLevel level;

	public SecondarySkill(HeroTrait trait, SkillLevel level) {
		this.trait = trait;
		this.level = level;
	}

	public HeroTrait getTrait() {
		return this.trait;
	}

	public SkillLevel getLevel() {
		return this.level;
	}

	@Override
	public String toString() {
		if (this.getTrait().equals(HeroTrait.NONE)) {
			return "None";
		}
		return String.format("%s %s", this.level, this.getTrait());
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SecondarySkill)) {
			return false;
		}

		SecondarySkill other = (SecondarySkill) o;
		return (trait == other.getTrait()) && (level == other.getLevel());
	}
	
}
