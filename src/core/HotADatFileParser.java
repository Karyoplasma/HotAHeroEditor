package core;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.enums.HeroHeader;

public class HotADatFileParser {
	private static final Logger logger = LogManager.getLogger(HotADatFileParser.class);
	
	private HotADatFileParser() {

	}

	public static int parseHeroes(Path hotaDAT) {
		try {
			logger.info("Parsing heroes in HotA.dat...");
			FileChannel hotaChannel = FileChannel.open(hotaDAT, StandardOpenOption.READ);
			MappedByteBuffer buffer = hotaChannel.map(FileChannel.MapMode.READ_ONLY, 0, hotaChannel.size());
			byte[] data = new byte[(int) hotaChannel.size()];
			byte[] searchBytes = new byte[] {0x00, 0x00, 0x00, 0x01, 0x5C, 0x00, 0x00, 0x00};
			buffer.get(data);
			String fileContent = new String(data);
			for (int heroIndex = 156; heroIndex < 1000; heroIndex++) {
				String search = String.format("hero%d", heroIndex);
				int position = fileContent.indexOf(search);
				// did not find hero
				if (position == -1) {
					hotaChannel.close();
					logger.info("Found " + heroIndex + " heroes.");
					return heroIndex;
				}
				// get new offsets
				for (int i = position; i < data.length - searchBytes.length; i++) {
	                byte[] subArray = Arrays.copyOfRange(data, i, i + searchBytes.length);
	                if (Arrays.equals(subArray, searchBytes)) {
	                	HeroHeader header = HeroHeader.values()[heroIndex];
	                	header.setDataOffsetHota(i+8);
	                	header.setSpecialtyOffsetHota(i+8);
	                    break;
	                }
	            }
			}

		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Exception encountered while parsing HotA.dat for heroes:", e);
			return -1;
		}
		logger.warn("Abnormal state while parsing HotA.dat for heroes!");
		return -1;
	}
	
	public static int parseCreatures(Path hotaDAT) {
		try {
			logger.info("Parsing creatures in HotA.dat...");
			FileChannel hotaChannel = FileChannel.open(hotaDAT, StandardOpenOption.READ);
			MappedByteBuffer buffer = hotaChannel.map(FileChannel.MapMode.READ_ONLY, 0, hotaChannel.size());
			byte[] data = new byte[(int) hotaChannel.size()];
			buffer.get(data);
			String fileContent = new String(data);
			for (int creatureIndex = 150; creatureIndex < 1000; creatureIndex++) {
				String search = String.format("monst%d", creatureIndex);
				int position = fileContent.indexOf(search);
				// did not find creature
				if (position == -1) {
					hotaChannel.close();
					logger.info(String.format("Found %d creatures in HotA.dat!", creatureIndex - 4));
					return creatureIndex - 4;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Error encountered while parsing creatures from HotA.dat:", e);
			return -1;
		}
		logger.warn("Abnormal state while parsing HotA.dat for creatures!");
		return -1;
	}
}
