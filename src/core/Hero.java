package core;

import java.nio.ByteBuffer;
import java.util.Arrays;

import core.enums.Creature;
import core.enums.Gender;
import core.enums.HeroHeader;
import core.enums.Profession;
import core.enums.Race;
import core.enums.Spell;

public class Hero {
	private HeroHeader header;
	private Gender gender;
	private Race race;
	private Profession profession;
	private SecondarySkill firstSkill, secondSkill;
	private Specialty specialty;
	private SpellBook spellBook;
	private Creature[] startingTroops;
	
	public Hero(HeroHeader header, Gender gender, Race race, Profession profession, Specialty specialty,
			SecondarySkill firstSkill, SecondarySkill secondSkill, SpellBook spellBook, Creature[] startingTroops) {
		this.header = header;
		this.firstSkill = firstSkill;
		this.secondSkill = secondSkill;
		this.specialty = specialty;
		this.gender = gender;
		this.race = race;
		this.profession = profession;
		this.spellBook = spellBook;
		this.startingTroops = startingTroops;
	}

	public SecondarySkill getFirstSkill() {
		return firstSkill;
	}

	public SecondarySkill getSecondSkill() {
		return secondSkill;
	}

	public String getName() {
		return header.toString();
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public long getDataOffset() {
		return header.getDataOffset();
	}

	public long getSpecialtyOffset() {
		return header.getSpecialtyOffset();
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

	public Spell getSpell() {
		return spellBook.getSpell();
	}

	public SpellBook getSpellBook() {
		return spellBook;
	}

	public Creature[] getStartingTroops() {
		return startingTroops;
	}

	public Race getRace() {
		return race;
	}
	
	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}
	
	public void setFirstSkill(SecondarySkill firstSkill) {
		this.firstSkill = firstSkill;
	}
	
	public void setSecondSkill(SecondarySkill secondSkill) {
		this.secondSkill = secondSkill;
	}
	
	public void setSpellBook(SpellBook spellBook) {
		this.spellBook = spellBook;
	}
	
	public void setStartingTroops(Creature[] startingTroops) {
		this.startingTroops = startingTroops;
	}
	
	public boolean isChanged(Hero originalHero) {
		if (!this.getSpecialty().equals(originalHero.getSpecialty())) {
			System.out.print("Specialty is different ");
			return true;
		}
		if (!this.getFirstSkill().equals(originalHero.getFirstSkill())) {
			System.out.print("First Skill is different ");
			return true;
		}
		if (!this.getSecondSkill().equals(originalHero.getSecondSkill())) {
			System.out.print("Second Skill is different ");
			return true;
		}
		if (!this.getSpellBook().equals(originalHero.getSpellBook())) {
			System.out.print("Spell is different ");
			return true;
		}
		if (!Arrays.equals(this.getStartingTroops(), originalHero.getStartingTroops())) {
			System.out.print("Starting Troops are different ");
			return true;
		}
		return false;
	}

	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 9);
		
		buffer.putInt(this.firstSkill.getTrait().getBytes());
		buffer.putInt(this.firstSkill.getLevel().getBytes());
		buffer.putInt(this.secondSkill.getTrait().getBytes());
		buffer.putInt(this.secondSkill.getLevel().getBytes());
		if (this.spellBook.getSpell() == Spell.NONE) {
			buffer.putInt(0x00000000);
			buffer.putInt(Spell.NONE.getBytes());
		} else {
			buffer.putInt(0x01000000);
			buffer.putInt(this.spellBook.getSpell().getBytes());
		}
		buffer.putInt(this.startingTroops[0].getBytes());
		buffer.putInt(this.startingTroops[1].getBytes());
		buffer.putInt(this.startingTroops[2].getBytes());
		buffer.flip();
		
		return buffer;
	}

	public void debug() {
		try {
			System.out.println(String.format("%s (%s); %s, %s; %s, %s; %s, %s", header.toString(), specialty.toString(),
					race, profession, firstSkill.toString(), secondSkill.toString(), spellBook.toString(),
					Arrays.toString(startingTroops)));
		} catch (NullPointerException e) {
			System.err.print(header.toString() + " had a null! ");
			if (specialty == null) {
				System.err.print("NULL specialty! ");
			} else {
				System.err.print(specialty.toString());
			}
			if (firstSkill == null) {
				System.err.println("NULL secondary 1! ");
			} else {
				System.err.print(firstSkill.toString());
			}
			if (secondSkill == null) {
				System.err.println("NULL secondary 2! ");
			} else {
				System.err.print(secondSkill.toString());
			}
			if (spellBook == null) {
				System.err.println("NULL Spellbook! ");
			} else {
				System.err.print(spellBook.toString());
			}
			for (int i = 0; i < 3; i++) {
				if (startingTroops[i] == null) {
					System.err.print("NULL startingTroop[" + i + "]!");
				} else {
					System.err.print(startingTroops[i]);
				}
			}
			System.err.println();
		}

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
