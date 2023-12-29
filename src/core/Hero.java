package core;

import java.nio.ByteBuffer;
import java.util.Arrays;

import core.enums.Creature;
import core.enums.Gender;
import core.enums.HeroHeader;
import core.enums.Profession;
import core.enums.Race;
import core.enums.Spell;
import core.specialties.Specialty;

public class Hero {
	private HeroHeader header;
	private Gender gender;
	private Race race;
	private Profession profession;
	private SecondarySkill secondary1, secondary2;
	private Specialty specialty;
	private HeroChange change;
	private SpellBook spellBook;
	private Creature[] startingTroops;

	public Hero(HeroHeader header, Gender gender, Race race, Profession profession, Specialty specialty,
			SecondarySkill secondary1, SecondarySkill secondary2, SpellBook spellBook, Creature[] startingTroops,
			HeroChange change) {
		this.header = header;
		this.secondary1 = secondary1;
		this.secondary2 = secondary2;
		this.specialty = specialty;
		this.change = change;
		this.gender = gender;
		this.race = race;
		this.profession = profession;
		this.spellBook = spellBook;
		this.startingTroops = startingTroops;
	}

	public SecondarySkill getSecondary1() {
		return secondary1;
	}

	public SecondarySkill getSecondary2() {
		return secondary2;
	}

	public String getName() {
		return header.getName();
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public long getHeroOffset() {
		return header.getOffset();
	}

	public long getSpecialtyOffset() {
		// TODO change HeroHeader for specialty offsets and return that;
		return 0;
	}

	public Profession getProfession() {
		return profession;
	}

	public Gender getGender() {
		return gender;
	}

	public HeroHeader getHeader() {
		return header;
	}

	public HeroChange getChange() {
		return change;
	}

	public Spell getSpell() {
		if (this.spellBook == null) {
			return Spell.NONE;
		}
		return this.spellBook.getSpell();
	}

	public Creature[] getStartingTroops() {
		return startingTroops;
	}

	public boolean hasSpellbook() {
		return this.spellBook.getSpell() != Spell.NONE;
	}

	public Race getRace() {
		return race;
	}

	public boolean changeSecondary1(SecondarySkill secondary1) {
		if (!secondary1.equals(this.secondary1)) {
			if (this.change.getSecondary1() == null) {
				this.change.setSecondary1(secondary1);
				return true;
			} else {
				if (this.change.getSecondary1().equals(secondary1)) {
					return false;
				}
			}
		} else {
			this.change.setSecondary1(null);
			return true;
		}
		return false;
	}

	public boolean changeSecondary2(SecondarySkill secondary2) {
		if (!secondary2.equals(this.secondary2)) {
			if (this.change.getSecondary2() == null) {
				this.change.setSecondary2(secondary2);
				return true;
			} else {
				if (this.change.getSecondary2().equals(secondary2)) {
					return false;
				}
			}
		} else {
			this.change.setSecondary2(null);
			return true;
		}
		return false;
	}

	public boolean changeSpecialty(Specialty specialty) {
		if (!this.specialty.equals(specialty)) {
			if (this.change.getSpecialty() == null) {
				this.change.setSpecialty(specialty);
				return true;
			} else {
				if (this.change.getSpecialty().equals(specialty)) {
					return false;
				}
			}
		} else {
			this.change.setSpecialty(null);
			return true;
		}
		return false;
	}

	public boolean changeSpellbook(SpellBook spellbook) {
		if (!this.spellBook.equals(spellbook)) {
			if (this.change.getSpellBook() == null) {
				this.change.setSpellBook(spellbook);
				return true;
			} else {
				if (this.change.getSpellBook().equals(spellbook)) {
					return false;
				}
			}
		} else {
			this.change.setSpellBook(null);
			return true;
		}
		return false;
	}

	public boolean setStartingTroops(Creature[] startingTroops) {
		if (Arrays.equals(this.startingTroops, startingTroops)) {
			if (this.change.getStartingTroops() == null) {
				this.change.setStartingTroops(startingTroops);
				return true;
			} else {
				if (Arrays.equals(this.change.getStartingTroops(), startingTroops)) {
					return false;
				}
			}
		} else {
			this.change.setStartingTroops(null);
			return true;
		}
		return false;
	}

	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 9);
		if (this.change.getSecondary1() == null) {
			buffer.putInt(this.secondary1.getTrait().getBytes());
			buffer.putInt(this.secondary1.getLevel().getBytes());
		} else {
			buffer.putInt(this.change.getSecondary1().getTrait().getBytes());
			buffer.putInt(this.change.getSecondary1().getLevel().getBytes());
		}
		if (this.change.getSecondary2() == null) {
			buffer.putInt(this.secondary2.getTrait().getBytes());
			buffer.putInt(this.secondary2.getLevel().getBytes());
		} else {
			buffer.putInt(this.change.getSecondary2().getTrait().getBytes());
			buffer.putInt(this.change.getSecondary2().getLevel().getBytes());
		}
		if (this.change.getSpellBook() == null) {
			if (this.spellBook.getSpell() == Spell.NONE) {
				buffer.putInt(0x00000000);
				buffer.putInt(Spell.NONE.getBytes());
			} else {
				buffer.putInt(0x01000000);
				buffer.putInt(this.spellBook.getSpell().getBytes());
			}
		} else {
			if (this.change.getSpellBook().getSpell() == Spell.NONE) {
				buffer.putInt(0x00000000);
				buffer.putInt(Spell.NONE.getBytes());
			} else {
				buffer.putInt(0x01000000);
				buffer.putInt(this.change.getSpellBook().getSpell().getBytes());
			}
		}
		if (this.change.getStartingTroops() == null) {
			buffer.putInt(this.startingTroops[0].getBytes());
			buffer.putInt(this.startingTroops[1].getBytes());
			buffer.putInt(this.startingTroops[2].getBytes());
		} else {
			buffer.putInt(this.change.getStartingTroops()[0].getBytes());
			buffer.putInt(this.change.getStartingTroops()[1].getBytes());
			buffer.putInt(this.change.getStartingTroops()[2].getBytes());
		}
		buffer.flip();
		return buffer;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Hero)) {
			return false;
		}
		Hero other = (Hero) o;
		return this.header == other.getHeader();
	}
}
