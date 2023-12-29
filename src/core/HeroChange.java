package core;

import core.enums.Creature;
import core.specialties.Specialty;

public class HeroChange {

	private Specialty specialty;
	private SecondarySkill secondary1, secondary2;
	private SpellBook spellBook;
	private Creature[] startingTroops;
	
	public HeroChange() {
		this.specialty = null;
		this.secondary1 = null;
		this.secondary2 = null;
		this.spellBook = null;
		this.startingTroops = null;
	}
	
	public HeroChange(Specialty specialty, SecondarySkill secondary1, SecondarySkill secondary2, SpellBook spellBook,
			Creature[] startingTroops) {
		this.specialty = specialty;
		this.secondary1 = secondary1;
		this.secondary2 = secondary2;
		this.spellBook = spellBook;
		this.startingTroops = startingTroops;
	}

	public boolean isChanged() {
		return !((specialty == null) && (secondary1 == null) && (secondary2 == null) && (spellBook == null)
				&& (startingTroops == null));
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public SecondarySkill getSecondary1() {
		return secondary1;
	}

	public SecondarySkill getSecondary2() {
		return secondary2;
	}

	public SpellBook getSpellBook() {
		return spellBook;
	}

	public Creature[] getStartingTroops() {
		return startingTroops;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public void setSecondary1(SecondarySkill secondary1) {
		this.secondary1 = secondary1;
	}

	public void setSecondary2(SecondarySkill secondary2) {
		this.secondary2 = secondary2;
	}

	public void setSpellBook(SpellBook spellBook) {
		this.spellBook = spellBook;
	}

	public void setStartingTroops(Creature[] startingTroops) {
		this.startingTroops = startingTroops;
	}

}
