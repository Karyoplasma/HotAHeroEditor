package enums;

public enum SkillLevel {
	NONE("None", 0x00000000),
	BASIC("Basic", 0x01000000),
	ADVANCED("Advanced", 0x02000000),
	EXPERT("Expert", 0x03000000);
	
	private int bytes;
	private String name;
	
	private SkillLevel(String name, int bytes) {
		this.name = name;
		this.bytes = bytes;
	}
	
	public int getBytes() {
		return bytes;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
