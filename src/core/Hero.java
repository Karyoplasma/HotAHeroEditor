package core;

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
	private Hero change;
	private boolean hasSpellbook;
	private Spell spell;
	private Creature[] startingTroops;

	public Hero(HeroHeader header, Gender gender, Race race, Profession profession, Specialty specialty, SecondarySkill secondary1, SecondarySkill secondary2, boolean hasSpellBook, Spell spell, Creature[] startingTroops, Hero change) {
		this.header = header;
		this.secondary1 = secondary1;
		this.secondary2 = secondary2;
		this.specialty = specialty;
		this.change = change;
		this.gender = gender;
		this.race = race;
		this.profession = profession;
		this.hasSpellbook = hasSpellBook;
		this.spell = spell;
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
		//TODO change HeroHeader for specialty offsets and return that;
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

	public Hero getChange() {
		return change;
	}
	
	public Spell getSpell() {
		return spell;
	}
	
	public Creature[] getStartingTroops() {
		return startingTroops;
	}
	
	public boolean hasSpellbook() {
		return this.hasSpellbook;
	}
	
	public void setChange(Hero change) {
		this.change = change;
	}
	
	public Object getRace() {
		return race;
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
