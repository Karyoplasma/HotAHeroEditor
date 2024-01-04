package core;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
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
	
	public static int createBackup(Path executable) {
		File parentDirectory = executable.getParent().toFile();
		File backupFolder = new File(parentDirectory, "backupHeroModder");
		if (!backupFolder.exists() && !backupFolder.mkdir()) {
			return 1;
		}
		File backupFile = new File(backupFolder, executable.toFile().getName() + "_" + Instant.now().getEpochSecond());
		try {
			Path destinationPath = backupFile.toPath();
			Files.copy(executable, destinationPath);
			return 0;
		} catch (IOException e) {
			return 2;
		}
	}

	public static int writeAllChanges(List<Hero> changes, Path executable) {
		if (executable == null) {
			return 1;
		}
		if (!executable.toFile().exists()) {
			return 2;
		}
		if (executable.toFile().isDirectory()) {
			return 3;
		}
		try {
			FileChannel fileChannel = FileChannel.open(executable, StandardOpenOption.WRITE);
			Path hotaDAT = Paths.get(executable.getParent() + "/HotA.dat");
			FileChannel hotaChannel = FileChannel.open(hotaDAT, StandardOpenOption.WRITE);
			
			for (Hero hero : changes) {
				if (hero.getHeader().hotaOnly()) {
					long hotaDataOffset = hero.getDataOffset() + 0xC;
					long hotaSpecialtyOffset = hero.getSpecialtyOffset();
					ByteBuffer hotaHeroData = hero.getByteBuffer();
					ByteBuffer hotaSpecialtyData = hero.getSpecialty().getByteBuffer();
					hotaChannel.write(hotaSpecialtyData, hotaSpecialtyOffset);
					hotaChannel.write(hotaHeroData, hotaDataOffset);
				} else {
					long dataOffset = hero.getDataOffset() + 0xC;
					long specialtyOffset = hero.getSpecialtyOffset();
					ByteBuffer heroData = hero.getByteBuffer();
					ByteBuffer specialtyData = hero.getSpecialty().getByteBuffer();
					fileChannel.write(specialtyData, specialtyOffset);
					fileChannel.write(heroData, dataOffset);
				}
			}
			fileChannel.close();
			hotaChannel.close();
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 4;
		}
		
	}
}
