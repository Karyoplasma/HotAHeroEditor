package core;

import java.nio.ByteBuffer;
import java.util.Arrays;

import core.enums.Creature;
import core.enums.Gender;
import core.enums.HeroHeader;
import core.enums.HeroTrait;
import core.enums.Profession;
import core.enums.Race;
import core.enums.SpecialtyType;
import core.enums.Spell;
import core.specialties.CreatureConversionSpecialty;
import core.specialties.CreatureSpecialty;
import core.specialties.StaticCreatureSpecialty;
import gui.HotAHeroEditor;
import models.CreatureComboBoxModel;
import models.CreatureSpecialtyComboBoxModel;
import models.HeroComboBoxModel;
import models.HeroTraitComboBoxModel;
import models.SpecialtyComboBoxModel;

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

	public Hero(Hero hero) {
		this.header = hero.header;
		this.firstSkill = new SecondarySkill(hero.getFirstSkill().getTrait(), hero.getFirstSkill().getLevel());
		this.secondSkill = new SecondarySkill(hero.getSecondSkill().getTrait(), hero.getSecondSkill().getLevel());
		this.specialty = SpecialtyFactory.createSpecialtyFromBuffer(hero.getSpecialty().getByteBuffer());
		this.gender = hero.getGender();
		this.race = hero.getRace();
		this.profession = hero.getProfession();
		this.spellBook = new SpellBook(hero.getSpellBook().getSpell());
		Creature[] troops = new Creature[3];
		for (int i = 0; i < 3; i++) {
			troops[i] = hero.getStartingTroops()[i];
		}
		this.startingTroops = troops;
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

	public void setTo(Hero hero) {
		assert hero != null;
		this.specialty = hero.specialty;
		this.firstSkill = hero.firstSkill;
		this.secondSkill = hero.secondSkill;
		this.spellBook = hero.spellBook;
		this.startingTroops = hero.startingTroops;
	}

	public boolean isChanged(Hero originalHero) {
		if (!this.getSpecialty().equals(originalHero.getSpecialty())) {
			return true;
		}
		if (!this.getFirstSkill().equals(originalHero.getFirstSkill())) {
			return true;
		}
		if (!this.getSecondSkill().equals(originalHero.getSecondSkill())) {
			return true;
		}
		if (!this.getSpellBook().equals(originalHero.getSpellBook())) {
			return true;
		}
		if (!Arrays.equals(this.getStartingTroops(), originalHero.getStartingTroops())) {
			return true;
		}
		return false;
	}
	
	public boolean isCompatibleWithVersion(HotAHeroEditor gui) {
		if (!((HeroComboBoxModel) gui.getComboBoxHero().getModel()).hasElement(this)) {
			return false;
		}
		if (!((HeroTraitComboBoxModel) gui.getComboBoxFirstSkill().getModel()).hasElement(this.firstSkill.getTrait())) {
			return false;
		}
		if (!((HeroTraitComboBoxModel) gui.getComboBoxSecondSkill().getModel()).hasElement(this.secondSkill.getTrait())) {
			return false;
		}
		switch (specialty.getType()) {
		case FREDERICK_SPECIALTY:
			if (!((SpecialtyComboBoxModel) gui.getComboBoxSpecialty().getModel()).hasElement(SpecialtyType.FREDERICK_SPECIALTY)) {
				return false;
			}
			break;
		case CREATURE_BONUS_STATIC:
			if (!((CreatureSpecialtyComboBoxModel) gui.getSpecialtyCreatureStatic().getModel()).hasElement(((StaticCreatureSpecialty) this.specialty).getCreature())){ 
				return false;
			}
			break;
		case CREATURE_BONUS_LEVEL:
			if (!((CreatureSpecialtyComboBoxModel) gui.getSpecialtyCreature().getModel()).hasElement(((CreatureSpecialty) this.specialty).getCreature())){ 
				// bug in the original file
				if (((CreatureSpecialty) this.specialty).getCreature() == Creature.CRUSADER) {
					break;
				}
				return false;
			}
			break;
		case CREATURE_CONVERSION:
			if (!((CreatureComboBoxModel) gui.getSpecialtyFirstConversion().getModel()).hasElement(((CreatureConversionSpecialty) this.specialty).getFirstAllowed())){ 
				return false;
			}
			if (!((CreatureComboBoxModel) gui.getSpecialtySecondConversion().getModel()).hasElement(((CreatureConversionSpecialty) this.specialty).getSecondAllowed())){ 
				return false;
			}
			if (!((CreatureComboBoxModel) gui.getSpecialtyConversionResult().getModel()).hasElement(((CreatureConversionSpecialty) this.specialty).getResult())){ 
				return false;
			}
			break;
		default:
			break;
		}
		if (!((CreatureComboBoxModel) gui.getComboBoxFirstTroop().getModel()).hasElement(this.startingTroops[0])){ 
			return false;
		}
		if (!((CreatureComboBoxModel) gui.getComboBoxSecondTroop().getModel()).hasElement(this.startingTroops[1])){ 
			return false;
		}
		if (!((CreatureComboBoxModel) gui.getComboBoxThirdTroop().getModel()).hasElement(this.startingTroops[2])){ 
			return false;
		}
		return true;
	}
	
	public boolean isHotaOnly() {
		if (header.hotaOnly()) {
			return true;
		}
		if (specialty.isHotaOnly()) {
			return true;
		}
		if (firstSkill.getTrait() == HeroTrait.INTERFERENCE) {
			return true;
		}
		if (secondSkill.getTrait() == HeroTrait.INTERFERENCE) {
			return true;
		}
		for (int i = 0; i < 3; i++) {
			if (startingTroops[i].hotaOnly()) {
				return true;
			}
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
	
	@Deprecated
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
