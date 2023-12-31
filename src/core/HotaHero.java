package core;

import java.util.Arrays;

import core.enums.Creature;
import core.enums.Gender;
import core.enums.HotAHeroHeader;
import core.enums.Profession;
import core.enums.Race;
import core.specialties.Specialty;

public class HotaHero extends Hero {
	
	HotAHeroHeader header;
	
	public HotaHero(HotAHeroHeader header, Gender gender, Race race, Profession profession, Specialty specialty,
			SecondarySkill secondary1, SecondarySkill secondary2, SpellBook spellBook, Creature[] startingTroops,
			HeroChange change) {
		super(null, gender, race, profession, specialty, secondary1, secondary2, spellBook, startingTroops, change);
		this.header = header;
	}
	
	public HotAHeroHeader getHotAHeader() {
		return header;
	}

	@Override
	public String getName() {
		return header.toString();
	}
	
	@Override
	public long getDataOffset() {
		return header.getDataOffset();
	}
	
	@Override
	public long getSpecialtyOffset() {
		return header.getSpecialtyOffset();
	}
	
	@Override
	public void debug() {
		try {
			System.out.println(String.format("%s (%s); %s, %s; %s, %s", header.toString(), super.getSpecialty().toString(),
					super.getSecondary1().toString(), super.getSecondary2().toString(), super.getSpell().toString(),
					Arrays.toString(super.getStartingTroops())));
		} catch (NullPointerException e) {
			System.err.print(header.toString() + " had a null! ");
			if (super.getSpecialty() == null) {
				System.err.print("NULL specialty! ");
			} else {
				System.err.print(super.getSpecialty().toString());
			}
			if (super.getSecondary1() == null) {
				System.err.println("NULL secondary 1! ");
			} else {
				System.err.print(super.getSecondary1().toString());
			}
			if (super.getSecondary2() == null) {
				System.err.println("NULL secondary 2! ");
			} else {
				System.err.print(super.getSecondary2().toString());
			}
			if (super.getSpellBook() == null) {
				System.err.println("NULL Spellbook! ");
			} else {
				System.err.print(super.getSpellBook().toString());
			}
			for (int i = 0; i<3; i++) {
				if (super.getStartingTroops()[i] == null) {
					System.err.print("NULL startingTroop[" + i + "]!");
				} else {
					System.err.print(super.getStartingTroops()[i]);
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
		if (!(o instanceof HotaHero)) {
			return false;
		}
		HotaHero other = (HotaHero) o;
		return this.header == other.getHotAHeader();
	}
}
