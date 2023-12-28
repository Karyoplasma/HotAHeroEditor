package core.enums;

public enum Resource {
	WOOD("Wood", 0x00000000),
	MERCURY("Mercury", 0x01000000),
	ORE("Ore", 0x02000000),
	SULFUR("Sulfur", 0x03000000),
	CRYSTAL("Crystal", 0x04000000),
	GEM("Gem", 0x05000000),
	GOLD("Gold", 0x06000000);
	
	private String name;
	private int bytes;
	
	private Resource(String name, int bytes) {
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
