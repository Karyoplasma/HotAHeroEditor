package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class ModFileHandler {
	private static final Logger logger = LogManager.getLogger(ModFileHandler.class);
	
	private ModFileHandler() {

	}
	
	public static List<Hero> readModFileFromDisk(Path modFile) {
		List<Hero> ret = new ArrayList<Hero>();
		try {
			logger.info("Reading mod file from disk...");
			BufferedReader reader = new BufferedReader(new FileReader(modFile.toFile()));
			String in;
			while ((in = reader.readLine()) != null) {
				String[] inSplit = in.split(";");
				HeroHeader header = HeroHeader.values()[Integer.parseInt(inSplit[0])];
				Gender gender = Gender.values()[Integer.parseInt(inSplit[1])];
				Race race = Race.values()[Integer.parseInt(inSplit[2])];
				Profession profession = Profession.values()[Integer.parseInt(inSplit[3])];
				ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 7);
				String[] specialtyArr = inSplit[4].split(",");
				for (int i = 0; i < 7; i++) {
					buffer.putInt(Integer.parseInt(specialtyArr[i]));
				}
				buffer.flip();
				Specialty specialty = SpecialtyFactory.createSpecialtyFromBuffer(buffer);
				String[] secondaryArr = inSplit[5].split(",");
				SecondarySkill secondary1 = new SecondarySkill(HeroTrait.values()[Integer.parseInt(secondaryArr[0])],
						SkillLevel.values()[Integer.parseInt(secondaryArr[1])]);
				secondaryArr = inSplit[6].split(",");
				SecondarySkill secondary2 = new SecondarySkill(HeroTrait.values()[Integer.parseInt(secondaryArr[0])],
						SkillLevel.values()[Integer.parseInt(secondaryArr[1])]);
				SpellBook spellBook = new SpellBook(Spell.values()[Integer.parseInt(inSplit[7])]);
				String[] troopsArr = inSplit[8].split(",");
				Creature[] startingTroops = new Creature[3];
				for (int i = 0; i < 3; i++) {
					startingTroops[i] = Creature.values()[Integer.parseInt(troopsArr[i])];
				}
				Hero hero = new Hero(header, gender, race, profession, specialty, secondary1, secondary2, spellBook,
						startingTroops);
				ret.add(hero);
			}
			reader.close();
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Exception encountered while reading mod file:", e);
			return null;
		}
	}
	
	public static String writeModFileToDisk(List<Hero> changes) {
		try {
			logger.info("Writing mod file to disk...");
			Date date = Date.from(Instant.now());
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
			String fileName = timestamp + ".mod";
			Path path = Paths.get("mods/" + fileName);
			if (path.getParent() == null) {
				return "No parent folder.";
			}
			if (!path.getParent().toFile().exists()) {
				path.getParent().toFile().mkdir();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()));
			for (Hero hero : changes) {
				writer.write(Integer.toString(hero.getHeader().ordinal()));
				writer.write(";");
				writer.write(Integer.toString(hero.getGender().ordinal()));
				writer.write(";");
				writer.write(Integer.toString(hero.getRace().ordinal()));
				writer.write(";");
				writer.write(Integer.toString(hero.getProfession().ordinal()));
				writer.write(";");
				ByteBuffer buffer = hero.getSpecialty().getByteBuffer();
				for (int i = 0; i < 7; i++) {
					writer.write(Integer.toString(buffer.getInt()));
					if (i == 6) {
						writer.write(";");
					} else {
						writer.write(",");
					}
				}
				writer.write(Integer.toString(hero.getFirstSkill().getTrait().ordinal()));
				writer.write(",");
				writer.write(Integer.toString(hero.getFirstSkill().getLevel().ordinal()));
				writer.write(";");
				writer.write(Integer.toString(hero.getSecondSkill().getTrait().ordinal()));
				writer.write(",");
				writer.write(Integer.toString(hero.getSecondSkill().getLevel().ordinal()));
				writer.write(";");
				writer.write(Integer.toString(hero.getSpellBook().getSpell().ordinal()));
				writer.write(";");
				for (int i = 0; i < 3; i++) {
					writer.write(Integer.toString(hero.getStartingTroops()[i].ordinal()));
					if (i == 2) {
						writer.newLine();
					} else {
						writer.write(",");
					}
				}
			}
			writer.close();
			return path.getFileName().toString();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			logger.error("Exception encountered while writing mod file to disk:", ioe);
			return "Could not write changes to disk.";
		}		
	}
	
	public static Map<String, Hero> readOriginalHeroes() {
		Map<String, Hero> ret = new HashMap<String, Hero>();
		try {
			logger.info("Reading original heroes...");
			BufferedReader reader = new BufferedReader(new FileReader("resources/originalHeroes"));
			String in;
			while ((in = reader.readLine()) != null) {
				String[] inSplit = in.split(";");
				HeroHeader header = HeroHeader.values()[Integer.parseInt(inSplit[0])];
				Gender gender = Gender.values()[Integer.parseInt(inSplit[1])];
				Race race = Race.values()[Integer.parseInt(inSplit[2])];
				Profession profession = Profession.values()[Integer.parseInt(inSplit[3])];
				ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 7);
				String[] specialtyArr = inSplit[4].split(",");
				for (int i = 0; i < 7; i++) {
					buffer.putInt(Integer.parseInt(specialtyArr[i]));
				}
				buffer.flip();
				Specialty specialty = SpecialtyFactory.createSpecialtyFromBuffer(buffer);
				String[] secondaryArr = inSplit[5].split(",");
				SecondarySkill secondary1 = new SecondarySkill(HeroTrait.values()[Integer.parseInt(secondaryArr[0])],
						SkillLevel.values()[Integer.parseInt(secondaryArr[1])]);
				secondaryArr = inSplit[6].split(",");
				SecondarySkill secondary2 = new SecondarySkill(HeroTrait.values()[Integer.parseInt(secondaryArr[0])],
						SkillLevel.values()[Integer.parseInt(secondaryArr[1])]);
				SpellBook spellBook = new SpellBook(Spell.values()[Integer.parseInt(inSplit[7])]);
				String[] troopsArr = inSplit[8].split(",");
				Creature[] startingTroops = new Creature[3];
				for (int i = 0; i < 3; i++) {
					startingTroops[i] = Creature.values()[Integer.parseInt(troopsArr[i])];
				}
				Hero hero = new Hero(header, gender, race, profession, specialty, secondary1, secondary2, spellBook,
						startingTroops);
				ret.put(header.toString(), hero);
			}
			reader.close();
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Exception encountered while reading original heroes:", e);
			return null;
		}
	}
}
