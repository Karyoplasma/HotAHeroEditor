package core.enums;

public enum Race {
	DEMON("Demon", 0x00000000),
	DWARF("Dwarf", 0x01000000),
	EFREET("Efreet", 0x02000000),
	ELF("Elf", 0x03000000),
	GENIE("Genie", 0x04000000),
	GNOLL("Gnoll", 0x05000000),
	GOBLIN("Goblin", 0x06000000),
	HUMAN("Human", 0x07000000),
	LICH("Lich", 0x08000000),
	LIZARDMAN("Lizardman", 0x09000000),
	MINOTAUR("Minotaur", 0x0a000000),
	OGRE("Ogre", 0x0b000000),
	TROGLODYTE("Troglodyte", 0x0c000000),
	VAMPIRE("Vampire", 0x0d000000),
	NYMPH("Nymph", 0x11000000),
	NIX("Nix", 0x12000000),
	HALFLING("Halfling", 0x13000000),
	DARK_ELF("Dark Elf", 0x14000000),
	GREMLIN("Gremlin", 0x15000000),
	DEBUG("DEBUG", 0xDEADBEEF);

	private String race;
	private int bytes;

	private Race(String race, int bytes) {
		this.race = race;
		this.bytes = bytes;
	}

	public int getBytes() {
		return this.bytes;
	}

	public static Race getRaceByBytes(int bytes) {
		for (Race race : Race.values()) {
			if (race.bytes == bytes) {
				return race;
			}
		}
		return Race.DEBUG;
	}

	@Override
	public String toString() {
		return this.race;
	}
}
