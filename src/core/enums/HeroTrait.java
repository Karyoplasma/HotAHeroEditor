package enums;

public enum HeroTrait {	
	PATHFINDING("Pathfinding", 0x00000000),
	ARCHERY("Archery", 0x01000000),
	LOGISTICS("Logistics", 0x02000000),
	SCOUTING("Scouting", 0x03000000),
	DIPLOMACY("Diplomacy", 0x04000000),
	NAVIGATION("Navigation", 0x05000000),
	LEADERSHIP("Leadership", 0x06000000),
	WISDOM("Wisdom", 0x07000000),
	MYSTICISM("Mysticism", 0x08000000),
	LUCK("Luck", 0x09000000),
	BALLISTICS("Ballistics", 0x0a000000),
	EAGLE_EYE("Eagle Eye", 0x0b000000),
	NECROMANCY("Necromancy", 0x0c000000),
	ESTATES("Estates", 0x0d000000),
	FIRE_MAGIC("Fire Magic",	0x0e000000),
	AIR_MAGIC("Air Magic", 0x0f000000),
	WATER_MAGIC("Water Magic", 0x10000000),
	EARTH_MAGIC("Earth Magic", 0x11000000),
	SCHOLAR("Scholar", 0x12000000),
	TACTICS("Tactics", 0x13000000),
	ARTILLERY("Artillery", 0x14000000),
	LEARNING("Learning", 0x15000000),
	OFFENSE("Offense", 0x16000000),
	ARMORER("Armorer", 0x17000000),
	INTELLIGENCE("Intelligence", 0x18000000),
	SORCERY("Sorcery", 0x19000000),
	RESISTANCE("Resistance", 0x1a000000),
	FIRST_AID("First Aid", 0x1b000000),
	NONE("None", 0xFFFFFFFF);
	
	private String name;
	private int bytes;
	
	private HeroTrait(String name, int bytes) {
		this.name = name;
		this.bytes = bytes;
	}

	public int getBytes() {
		return this.bytes;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
