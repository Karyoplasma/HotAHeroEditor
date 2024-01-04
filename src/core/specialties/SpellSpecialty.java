package core.specialties;

import java.nio.ByteBuffer;

import core.Specialty;
import core.enums.SpecialtyType;
import core.enums.Spell;

public class SpellSpecialty implements Specialty {
	
	private Spell spell;
	
	public SpellSpecialty(Spell spell) {
		this.spell = spell;
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 7);
		buffer.putInt(this.getType().getBytes());
		buffer.putInt(this.spell.getBytes());
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.putInt(0x00000000);
		buffer.flip();
		return buffer;
	}
	
	public Spell getSpell() {
		return spell;
	}
	
	@Override
	public SpecialtyType getType() {
		return SpecialtyType.SPELL_BONUS;
	}
	
	@Override
	public boolean isHotaOnly() {
		return false;
	}
	
	@Override
	public String toString() {
		return this.spell.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SpellSpecialty)) {
			return false;
		}
		SpellSpecialty other = (SpellSpecialty) o;
		return spell == other.spell;
	}
	
}
