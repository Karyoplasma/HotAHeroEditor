package core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Map;

import core.enums.Creature;
import core.enums.Gender;
import core.enums.HeroHeader;
import core.enums.HeroTrait;
import core.enums.HotAHeroHeader;
import core.enums.Profession;
import core.enums.Race;
import core.enums.SkillLevel;
import core.enums.Spell;
import core.specialties.Specialty;

public class H3ExecutableReader {

	private H3ExecutableReader() {

	}

	public static Map<String, Hero> readHeroes(Path executable, boolean isHotA) throws IOException {
		Map<String, Hero> heroes = new LinkedHashMap<String, Hero>();
		FileChannel fileChannel = FileChannel.open(executable, StandardOpenOption.READ);

		for (HeroHeader header : HeroHeader.values()) {
			long offsetHeroData = header.getDataOffset();
			ByteBuffer bufferHeroData = ByteBuffer.allocate(Integer.BYTES * 12);
			long offsetHeroSpecialty = header.getSpecialtyOffset();
			ByteBuffer bufferSpecialtyData = ByteBuffer.allocate(Integer.BYTES * 7);
			// Process the data
			fileChannel.read(bufferHeroData, offsetHeroData);
			fileChannel.read(bufferSpecialtyData, offsetHeroSpecialty);
			bufferHeroData.flip();
			bufferSpecialtyData.flip();
			Gender gender = (bufferHeroData.getInt() == 0x00000000) ? Gender.MALE : Gender.FEMALE;
			Race race = Race.getRaceByBytes(bufferHeroData.getInt());
			Profession profession = Profession.getProfessionByBytes(bufferHeroData.getInt());
			HeroTrait skill1 = HeroTrait.getHeroTraitByBytes(bufferHeroData.getInt());
			SecondarySkill secondary1 = new SecondarySkill(skill1, SkillLevel.values()[Integer.reverseBytes(bufferHeroData.getInt())]);
			HeroTrait skill2 = HeroTrait.getHeroTraitByBytes(bufferHeroData.getInt());
			SecondarySkill secondary2 = new SecondarySkill(skill2, SkillLevel.values()[Integer.reverseBytes(bufferHeroData.getInt())]);
			bufferHeroData.getInt();
			SpellBook spellBook = new SpellBook(Spell.getSpellByBytes(bufferHeroData.getInt()));
			Creature[] startingTroops = new Creature[3];
			for (int i = 0; i < 3; i++) {
				startingTroops[i] = Creature.getCreatureByBytes(bufferHeroData.getInt());
			}
			Specialty specialty = SpecialtyFactory.createSpecialtyFromBuffer(bufferSpecialtyData);
			Hero hero = new Hero(header, gender, race, profession, specialty, secondary1, secondary2, spellBook, startingTroops, new HeroChange());
			heroes.put(header.toString(), hero);

		}
		fileChannel.close();
		
		if (isHotA) {
			Path hotaDAT = Paths.get(executable.getParent() + "/HotA.dat");
			FileChannel fileChannelHotA = FileChannel.open(hotaDAT, StandardOpenOption.READ);
			for (HotAHeroHeader header : HotAHeroHeader.values()) {
				long offsetHeroData = header.getDataOffset();
				ByteBuffer bufferHeroData = ByteBuffer.allocate(Integer.BYTES * 12);
				long offsetHeroSpecialty = header.getSpecialtyOffset();
				ByteBuffer bufferSpecialtyData = ByteBuffer.allocate(Integer.BYTES * 7);
				
				fileChannelHotA.read(bufferHeroData, offsetHeroData);
				fileChannelHotA.read(bufferSpecialtyData, offsetHeroSpecialty);
				bufferHeroData.flip();
				bufferSpecialtyData.flip();
				Gender gender = (bufferHeroData.getInt() == 0x00000000) ? Gender.MALE : Gender.FEMALE;
				Race race = Race.getRaceByBytes(bufferHeroData.getInt());
				Profession profession = Profession.getProfessionByBytes(bufferHeroData.getInt());
				HeroTrait skill1 = HeroTrait.getHeroTraitByBytes(bufferHeroData.getInt());
				SecondarySkill secondary1 = new SecondarySkill(skill1, SkillLevel.values()[Integer.reverseBytes(bufferHeroData.getInt())]);
				HeroTrait skill2 = HeroTrait.getHeroTraitByBytes(bufferHeroData.getInt());
				SecondarySkill secondary2 = new SecondarySkill(skill2, SkillLevel.values()[Integer.reverseBytes(bufferHeroData.getInt())]);
				bufferHeroData.getInt();
				SpellBook spellBook = new SpellBook(Spell.getSpellByBytes(bufferHeroData.getInt()));
				Creature[] startingTroops = new Creature[3];
				for (int i = 0; i < 3; i++) {
					startingTroops[i] = Creature.getCreatureByBytes(bufferHeroData.getInt());
				}
				Specialty specialty = SpecialtyFactory.createSpecialtyFromBuffer(bufferSpecialtyData);
				HotaHero hero = new HotaHero(header, gender, race, profession, specialty, secondary1, secondary2, spellBook, startingTroops, new HeroChange());
				heroes.put(header.toString(), hero);
			}
			fileChannelHotA.close();
		}
		return heroes;
	}
}
