package enums;

public enum Gender {
	MALE("Male", 0x00000000),
	FEMALE("Female", 0x01000000);

	private String gender;
	private int id;

	private Gender(String gender, int id) {
		this.gender = gender;
		this.id = id;
	}

	public int getID() {
		return this.id;
	}

	@Override
	public String toString() {
		return this.gender;
	}
}
