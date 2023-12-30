package core;

import java.nio.ByteBuffer;

import core.enums.Creature;
import core.enums.HeroTrait;
import core.enums.Resource;
import core.enums.Spell;
import core.specialties.AdrienneSpecialty;
import core.specialties.CreatureConversionSpecialty;
import core.specialties.CreatureSpecialty;
import core.specialties.CreatureSpeedSpecialty;
import core.specialties.DragonSpecialty;
import core.specialties.ResourceSpecialty;
import core.specialties.SkillSpecialty;
import core.specialties.Specialty;
import core.specialties.SpellSpecialty;
import core.specialties.StaticCreatureSpecialty;

public class SpecialtyFactory {

	private SpecialtyFactory() {

	}

	public static Specialty createSpecialtyFromBuffer(ByteBuffer buffer) {

		switch (buffer.getInt()) {
		case 0x00000000:
			return new SkillSpecialty(HeroTrait.getHeroTraitByBytes(buffer.getInt()));
		case 0x01000000:
			return new CreatureSpecialty(Creature.getCreatureByBytes(buffer.getInt()));
		case 0x02000000:
			return new ResourceSpecialty(Resource.getResourceByBytes(buffer.getInt()));
		case 0x03000000:
			return new SpellSpecialty(Spell.getSpellByBytes(buffer.getInt()));
		case 0x04000000:
			return new StaticCreatureSpecialty(Creature.getCreatureByBytes(buffer.getInt()), buffer.getInt(),
					buffer.getInt(), buffer.getInt());
		case 0x05000000:
			return new CreatureSpeedSpecialty(buffer.getInt());
		case 0x06000000:
			Creature allowed1 = Creature.getCreatureByBytes(buffer.getInt());
			buffer.getInt();
			buffer.getInt();
			buffer.getInt();
			return new CreatureConversionSpecialty(allowed1, Creature.getCreatureByBytes(buffer.getInt()),
					Creature.getCreatureByBytes(buffer.getInt()));
		case 0x07000000:
			buffer.getInt();
			return new DragonSpecialty(buffer.getInt(), buffer.getInt());
		case 0xFFFFFFFF:
			return new AdrienneSpecialty();
		default:
			return null;
		}
	}

}
