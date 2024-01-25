package core;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.enums.Creature;
import core.enums.Gender;
import core.enums.HeroHeader;
import core.enums.HeroTrait;
import core.enums.Profession;
import core.enums.Race;
import core.enums.SkillLevel;
import core.enums.Spell;
import gui.HotAHeroEditor;

public class H3ExecutableHandler {
	private static final Logger logger = LogManager.getLogger(H3ExecutableHandler.class);

	private H3ExecutableHandler() {

	}

	public static List<Hero> readHeroes(Path executable, boolean isHotA) {
		logger.info("Reading " + executable.getFileName());
		List<Hero> heroes = new ArrayList<Hero>();
		if (isHotA) {
			Path hotaDAT = Paths.get(executable.getParent() + "/HotA.dat");
			int totalHeroes = HotADatFileParser.parseHeroes(hotaDAT);
			int totalCreatures = HotADatFileParser.parseCreatures(hotaDAT);
			if (totalHeroes == -1 || totalCreatures == -1) {
				return heroes;
			}
			HotAHeroEditor.setTotalCreatures(totalCreatures);
			HotAHeroEditor.setTotalHeroes(totalHeroes);
			try (FileChannel fileChannel = FileChannel.open(executable, StandardOpenOption.READ);
					FileChannel hotaChannel = FileChannel.open(hotaDAT, StandardOpenOption.READ)) {
				for (HeroHeader header : HeroHeader.values()) {
					if (header.hotaOnly()) {
						if (header.isOffsetChanged()) {
							long offsetHeroData = header.getDataOffset();
							ByteBuffer bufferHeroData = ByteBuffer.allocate(Integer.BYTES * 12);
							long offsetHeroSpecialty = header.getSpecialtyOffset();
							ByteBuffer bufferSpecialtyData = ByteBuffer.allocate(Integer.BYTES * 7);
							hotaChannel.read(bufferHeroData, offsetHeroData);
							hotaChannel.read(bufferSpecialtyData, offsetHeroSpecialty);
							bufferHeroData.flip();
							bufferSpecialtyData.flip();
							heroes.add(createHeroFromBuffers(header, bufferHeroData, bufferSpecialtyData));
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
			} catch (IOException e) {
				logger.error("Exception encountered while reading heroes:", e);
				return heroes;
			}
		} else {
			try (FileChannel fileChannel = FileChannel.open(executable, StandardOpenOption.READ)) {
				for (HeroHeader header : HeroHeader.values()) {
					if (!header.hotaOnly()) {
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
			} catch (IOException e) {
				logger.error("Exception encountered while reading heroes:", e);
			}
		}
		return heroes;
	}

	private static Hero createHeroFromBuffers(HeroHeader header, ByteBuffer bufferHeroData,
			ByteBuffer bufferSpecialtyData) {
		Gender gender = (bufferHeroData.getInt() == 0x00000000) ? Gender.MALE : Gender.FEMALE;
		Race race = Race.getRaceByBytes(bufferHeroData.getInt());
		Profession profession = Profession.getProfessionByBytes(bufferHeroData.getInt());
		HeroTrait skill1 = HeroTrait.getHeroTraitByBytes(bufferHeroData.getInt());
		SecondarySkill secondary1 = new SecondarySkill(skill1,
				SkillLevel.values()[Integer.reverseBytes(bufferHeroData.getInt())]);
		HeroTrait skill2 = HeroTrait.getHeroTraitByBytes(bufferHeroData.getInt());
		SecondarySkill secondary2 = new SecondarySkill(skill2,
				SkillLevel.values()[Integer.reverseBytes(bufferHeroData.getInt())]);
		bufferHeroData.getInt();
		SpellBook spellBook = new SpellBook(Spell.getSpellByBytes(bufferHeroData.getInt()));
		Creature[] startingTroops = new Creature[3];
		for (int i = 0; i < 3; i++) {
			startingTroops[i] = Creature.getCreatureByBytes(bufferHeroData.getInt());
		}
		Specialty specialty = SpecialtyFactory.createSpecialtyFromBuffer(bufferSpecialtyData);
		Hero hero = new Hero(header, gender, race, profession, specialty, secondary1, secondary2, spellBook,
				startingTroops);
		return hero;
	}

	public static int createBackup(Path executable, String timeStamp) {
		logger.info("Creating backup of " + executable.getFileName() + " before writing changes...");
		File parentDirectory = executable.getParent().toFile();
		File backupFolder = new File(parentDirectory, "backupHeroModder");
		if (!backupFolder.exists() && !backupFolder.mkdir()) {
			return 1;
		}
		File backupFile = new File(backupFolder, executable.toFile().getName() + "_" + timeStamp);
		try {
			Path destinationPath = backupFile.toPath();
			Files.copy(executable, destinationPath);
			logger.info("Backup of " + executable.getFileName() + " successful!");
			return 0;
		} catch (IOException e) {
			logger.error("Error encountered while backing up " + executable.getFileName(), e);
			return 2;
		}
	}

	public static int writeAllChanges(List<Hero> changes, Path executable) {
		logger.info("Writing changes to " + executable.getFileName());
		if (!executable.toFile().exists()) {
			logger.error("Executable " + executable.getFileName() + " does not exist!");
			return 2;
		}
		if (executable.toFile().isDirectory()) {
			logger.error("Executable " + executable.getFileName() + " is a directory!");
			return 3;
		}
		Path hotaDAT = Paths.get(executable.getParent() + "/HotA.dat");
		if (Files.exists(hotaDAT)) {
			try (FileChannel fileChannel = FileChannel.open(executable, StandardOpenOption.WRITE);
					FileChannel hotaChannel = FileChannel.open(hotaDAT, StandardOpenOption.WRITE)) {
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
				logger.info("Successfully written changes!");
				return 0;
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("Error encountered while writing changes:", e);
				return 4;
			}
		} else {
			try (FileChannel fileChannel = FileChannel.open(executable, StandardOpenOption.WRITE)) {
				for (Hero hero : changes) {
					if (hero.getHeader().hotaOnly()) {
						logger.warn("There was an attemp to write HotA changes when HotA.dat does not exist!");
						continue;
					} else {
						long dataOffset = hero.getDataOffset() + 0xC;
						long specialtyOffset = hero.getSpecialtyOffset();
						ByteBuffer heroData = hero.getByteBuffer();
						ByteBuffer specialtyData = hero.getSpecialty().getByteBuffer();
						fileChannel.write(specialtyData, specialtyOffset);
						fileChannel.write(heroData, dataOffset);
					}
				}
				logger.info("Successfully written changes!");
				return 0;
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("Error encountered while writing changes:", e);
				return 4;
			}
		}
	}
}
