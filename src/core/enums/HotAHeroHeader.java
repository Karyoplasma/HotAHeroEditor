package core.enums;

public enum HotAHeroHeader {
	CORKES("Corkes", 0x279, 0x215),
	JEREMY("Jeremy", 0x517, 0x4B3),
	ILLOR("Illor", 0x829, 0x7C5),
	DEREK("Derek", 0xB08, 0xAA4),
	LEENA("Leena", 0xDCB, 0xD67),
	ANABEL("Anabel", 0x10A3, 0x103F),
	CASSIOPEIA("Cassiopeie", 0x137B, 0x1317),
	MIRIAM("Miriam", 0x15CA, 0x1566),
	CASMETRA("Casmetra", 0x191A, 0x18B6),
	EOVACIUS("Eovacius", 0x1BC9, 0x1B65),
	SPINT("Spint", 0x1E72, 0x1E0E),
	ANDAL("Andal", 0x2146, 0x20E2),
	MANFRED("Manfred", 0x240D, 0x23A9),
	ZILARE("Zilare", 0x26BA, 0x2656),
	ASTRA("Astra", 0x2941, 0x28DD),
	DARGEM("Dargem", 0x2C29, 0x2BC5),
	BIDLEY("Bidley", 0x2EF8, 0x2E94),
	TARK("Tark", 0x31CB, 0x3167),
	ELMORE("Elmore", 0x3446, 0x33E2),
	BEATRICE("Beatrice", 0x36C8, 0x3664),
	KINKERIA("Kinkeria", 0x3968, 0x3904),
	RANLOO("Ranloo", 0x3C41, 0x3BDD);
	
	private String name;
	private long specialtyOffset, dataOffset;
	
	private HotAHeroHeader(String name, long specialtyOffset, long dataOffset) {
		this.name = name;
		this.specialtyOffset = specialtyOffset;
		this.dataOffset = dataOffset;
	}

	public long getSpecialtyOffset() {
		return specialtyOffset;
	}

	public long getDataOffset() {
		return dataOffset;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
