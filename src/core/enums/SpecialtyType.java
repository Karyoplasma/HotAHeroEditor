package core.enums;

public enum SpecialtyType {
	SKILL_BONUS("Skill Bonus", 0x00000000),
	CREATURE_BONUS_LEVEL("Creature bonus per level", 0x01000000),
	RESOURCE_BONUS("Resource Bonus", 0x02000000),
	SPELL_BONUS("Spell Bonus", 0x03000000),
	CREATURE_BONUS_STATIC("Static creature bonus", 0x04000000),
	CREATURE_BONUS_SPEED("Creature speed bonus", 0x05000000),
	CREATURE_CONVERSION("Creature conversion", 0x06000000),
	DRAGON_BONUS("Static dragon bonus", 0x070000000);
	
	private String name;
	private int bytes;
	
	private SpecialtyType(String name, int bytes) {
		this.name = name;
		this.bytes = bytes;
	}
	
	public int getBytes() {
		return bytes;
	}
	
	@Override
	public String toString() {
		return name;
	}
}