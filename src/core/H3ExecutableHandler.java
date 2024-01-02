package core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import core.enums.Creature;
import core.enums.Gender;
import core.enums.HeroHeader;
import core.enums.HeroTrait;
import core.enums.Profession;
import core.enums.Race;
import core.enums.SkillLevel;
import core.enums.Spell;

public class H3ExecutableHandler {

	private H3ExecutableHandler() {

	}

	public static List<Hero> readHeroes(Path executable, boolean isHotA) throws IOException {
		List<Hero> heroes = new ArrayList<Hero>();
		FileChannel fileChannel = FileChannel.open(executable, StandardOpenOption.READ);
		FileChannel hotaChannel = null;
		if (isHotA) {
			Path hotaDAT = Paths.get(executable.getParent() + "/HotA.dat");
			hotaChannel = FileChannel.open(hotaDAT, StandardOpenOption.READ);
		}
		for (HeroHeader header : HeroHeader.values()) {
			if (header.hotaOnly()) {
				if (isHotA) {
					long offsetHeroData = header.getDataOffset();
					ByteBuffer bufferHeroData = ByteBuffer.allocate(Integer.BYTES * 12);
					long offsetHeroSpecialty = header.getSpecialtyOffset();
					ByteBuffer bufferSpecialtyData = ByteBuffer.allocate(Integer.BYTES * 7);					
					hotaChannel.read(bufferHeroData, offsetHeroData);
					hotaChannel.read(bufferSpecialtyData, offsetHeroSpecialty);
					bufferHeroData.flip();
					bufferSpecialtyData.flip();
					heroes.add(createHeroFromBuffers(header, bufferHeroData, bufferSpecialtyData));
				} else {
					continue;
				}
			} else {
				long offsetHeroData = header.getDataOffset();
				ByteBuffer bufferHeroData = ByteBuffer.allocate(Integer.BYTES * 12);
				long offsetHeroSpecialty = header.getSpecialtyOffset();
				ByteBuffer bufferSpecialtyData = ByteBuffer.allocate(Integer.BYTES * 7);
				fileChannel.read(bufferHeroData, offsetHeroData);
				fileChannel.read(bufferSpecialtyData, offsetHeroSpecialty);
				bufferHeroData.flip();
				bufferSpecialtyData.flip();
				heroes.add(createHeroFromBuffers(header, bufferHeroData, bufferSpecialtyData));
			}	
		}
		
		fileChannel.close();
		if (hotaChannel != null) {
			hotaChannel.close();
		}
		
		return heroes;
	}

	private static Hero createHeroFromBuffers(HeroHeader header, ByteBuffer bufferHeroData, ByteBuffer bufferSpecialtyData) {
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
		Hero hero = new Hero(header, gender, race, profession, specialty, secondary1, secondary2, spellBook, startingTroops);
		return hero;
	}
}
