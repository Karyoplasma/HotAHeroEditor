package core.enums;

public enum Profession {
	KNIGHT("Knight", 0x00000000),
	CLERIC("Cleric", 0x01000000),
	RANGER("Ranger", 0x02000000),
	DRUID("Druid", 0x03000000),
	ALCHEMIST("Alchemist", 0x04000000),
	WIZARD("Wizard", 0x05000000),
	DEMONIAC("Demoniac", 0x06000000),
	HERETIC("Heretic", 0x07000000),
	DEATH_KNIGHT("Death Knight", 0x08000000),
	NECROMANCER("Necromancer", 0x09000000),
	OVERLORD("Overlord", 0x0a000000),
	WARLOCK("Warlock", 0x0b000000),
	BARBARIAN("Barbarian", 0x0c000000),
	BATTLEMAGE("Battlemage", 0x0d000000),
	BEASTMASTER("Beastmaster", 0x0e000000),
	WITCH("Witch", 0x0f000000),
	PLANESWALKER("Planeswalker", 0x10000000),
	ELEMENTALIST("Elementalist", 0x11000000),
	CAPTAIN("Captain", 0x12000000),
	NAVIGATOR("Navigator", 0x13000000),
	MERCENARY("Mercenary", 0x14000000),
	ARTIFICER("Artificer", 0x15000000),
	DEBUG("DEBUG", 0xDEADBEEF);

	private String profession;
	private int bytes;

	private Profession(String profession, int bytes) {
		this.profession = profession;
		this.bytes = bytes;
	}

	public int getBytes() {
		return this.bytes;
	}

	public static Profession getProfessionByBytes(int bytes) {
		for (Profession profession : Profession.values()) {
			if (profession.getBytes() == bytes) {
				return profession;
			}
		}
		return Profession.DEBUG;
	}

	@Override
	public String toString() {
		return this.profession;
	}
}