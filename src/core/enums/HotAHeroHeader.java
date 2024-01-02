package core.enums;

@Deprecated
public enum HotAHeroHeader {
	CORKES("Corkes", 0x25E7, 0x264B),
	JEREMY("Jeremy", 0x2885, 0x28E9),
	ILLOR("Illor", 0x2B97, 0x2BFB),
	DEREK("Derek", 0x2E76, 0x2EDA),
	LEENA("Leena", 0x3139, 0x319D),
	ANABEL("Anabel", 0x3411, 0x3475),
	CASSIOPEIA("Cassiopeia", 0x36E9, 0x374D),
	MIRIAM("Miriam", 0x3922, 0x3986),
	CASMETRA("Casmetra", 0x3C72, 0x3CD6),
	EOVACIUS("Eovacius", 0x3F21, 0x3F85),
	SPINT("Spint", 0x41CA, 0x422E),
	ANDAL("Andal", 0x449E, 0x4502),
	MANFRED("Manfred", 0x4765, 0x47C9),
	ZILARE("Zilare", 0x4A12, 0x4A76),
	ASTRA("Astra", 0x4C99, 0x4CFD),
	DARGEM("Dargem", 0x4F81, 0x4FE5),
	BIDLEY("Bidley", 0x5250, 0x52B4),
	TARK("Tark", 0x5523, 0x5587),
	ELMORE("Elmore", 0x579E, 0x5802),
	BEATRICE("Beatrice", 0x5A0A, 0x5A6E),
	KINKERIA("Kinkeria", 0x5CAA, 0x5D0E),
	RANLOO("Ranloo", 0x5F84, 0x5FE8),
	GISELLE("Giselle", 0x61FE, 0x6262),
	HENRIETTA("Henrietta", 0x652B, 0x658F),
	SAM("Sam", 0x6822, 0x6886),
	TANCRED("Tancred", 0x6B3F, 0x6BA3),
	MELCHIOR("Melchior", 0x6DEA, 0x6E4E),
	FLORIBERT("Floribert", 0x7054, 0x70B8),
	WYNONA("Wynona", 0x730D, 0x7371),
	DURY("Dury", 0x75BE, 0x7622),
	MORTON("Morton", 0x7842, 0x78A6),
	CELESTINE("Celestine", 0x7B8F, 0x7BF3),
	TODD("Todd", 0x7EB7, 0x7F1B),
	AGAR("Agar", 0x81BC, 0x8220),
	BERTRAM("Bertram", 0x8440, 0x84A4),
	WRATHMONT("Wrathmont", 0x8735, 0x8799),
	ZIPH("Ziph", 0x89EF, 0x8A53),
	VICTORIA("Victoria", 0x8CFD, 0x8D61),
	EANSWYTHE("Eanswythe", 0x9015, 0x9079),
	FREDERICK("Frederick", 0x9307, 0x936B),
	TAVIN("Tavin", 0x95AC, 0x9610),
	MURDOCH("Murdoch", 0x980B, 0x986F);
	
	private String name;
	private long specialtyOffset, dataOffset;
	
	private HotAHeroHeader(String name, long dataOffset, long specialtyOffset) {
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
