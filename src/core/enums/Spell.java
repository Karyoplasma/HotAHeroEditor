package enums;

public enum Spell {
	SUMMON_BOAT("Summon Boat", 0x00000000),
	SCUTTLE_BOAT("Scuttle Boat", 0x01000000),
	VISIONS("Visions", 0x02000000),
	VIEW_EARTH("View Earth", 0x03000000),
	DISGUISE("Disguise", 0x04000000),
	VIEW_AIR("View Air", 0x05000000),
	FLY("Fly", 0x06000000),
	WATER_WALK("Water Walk", 0x07000000),
	DIMENSION_DOOR("Dimension Door", 0x08000000),
	TOWN_PORTAL("Town Portal", 0x09000000),
	QUICK_SAND("Quick Sand", 0x0A000000),
	LAND_MINE("Land Mine", 0x0B000000),
	FORCE_FIELD("Force Field", 0x0C000000),
	FIRE_WALL("Fire Wall", 0x0D000000),
	EARTHQUAKE("Earthquake", 0x0E000000),
	MAGIC_ARROW("Magic Arrow", 0x0F000000),
	ICE_BOLT("Ice Bolt", 0x10000000),
	LIGHTNING_BOLT("Lightning Bolt", 0x11000000),
	IMPLOSION("Implosion", 0x12000000),
	CHAIN_LIGHTNING("Chain Lightning", 0x13000000),
	FROST_RING("Frost Ring", 0x14000000),
	FIREBALL("Fireball", 0x15000000),
	INFERNO("Inferno", 0x16000000),
	METEOR_SHOWER("Meteor Shower", 0x17000000),
	DEATH_RIPPLE("Death Ripple", 0x18000000),
	DESTROY_UNDEAD("Destroy Undead", 0x19000000),
	ARMAGEDDON("Armageddon", 0x1A000000),
	SHIELD("Shield", 0x1B000000),
	AIR_SHIELD("Air Shield", 0x1C000000),
	FIRE_SHIELD("Fire Shield", 0x1D000000),
	PROTECTION_FROM_AIR("Protection from Air", 0x1E000000),
	PROTECTION_FROM_FIRE("Protection from Fire", 0x1F000000),
	PROTECTION_FROM_WATER("Protection from Water", 0x20000000),
	PROTECTION_FROM_EARTH("Protection from Earth", 0x21000000),
	ANTI_MAGIC("Anti-Magic", 0x22000000),
	DISPEL("Dispel", 0x23000000),
	MAGIC_MIRROR("Magic Mirror", 0x24000000),
	CURE("Cure", 0x25000000),
	RESURRECTION("Resurrection", 0x26000000),
	ANIMATE_DEAD("Animate Dead", 0x27000000),
	SACRIFICE("Sacrifice", 0x28000000),
	BLESS("Bless", 0x29000000),
	CURSE("Curse", 0x2A000000),
	BLOODLUST("Bloodlust", 0x2B000000),
	PRECISION("Precision", 0x2C000000),
	WEAKNESS("Weakness", 0x2D000000),
	STONE_SKIN("Stone Skin", 0x2E000000),
	DISRUPTING_RAY("Disrupting Ray", 0x2F000000),
	PRAYER("Prayer", 0x30000000),
	MIRTH("Mirth", 0x31000000),
	SORROW("Sorrow", 0x32000000),
	FORTUNE("Fortune", 0x33000000),
	MISFORTUNE("Misfortune", 0x34000000),
	HASTE("Haste", 0x35000000),
	SLOW("Slow", 0x36000000),
	SLAYER("Slayer", 0x37000000),
	FRENZY("Frenzy", 0x38000000),
	TITANS_LIGHTNING_BOLT("Titan's Lightning Bolt", 0x39000000),
	COUNTERSTRIKE("Counterstrike", 0x3A000000),
	BERSERK("Berserk", 0x3B000000),
	HYPNOTIZE("Hypnotize", 0x3C000000),
	FORGETFULNESS("Forgetfulness", 0x3D000000),
	BLIND("Blind", 0x3E000000),
	TELEPORT("Teleport", 0x3F000000),
	REMOVE_OBSTACLE("Remove Obstacle", 0x40000000),
	CLONE("Clone", 0x41000000),
	FIRE_ELEMENTAL("Fire Elemental", 0x42000000),
	EARTH_ELEMENTAL("Earth Elemental", 0x43000000),
	WATER_ELEMENTAL("Water Elemental", 0x44000000),
	AIR_ELEMENTAL("Air Elemental", 0x45000000),
	NONE("None", 0xFFFFFFFF);

	private String name;
	private int bytes;

	private Spell(String name, int bytes) {
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
