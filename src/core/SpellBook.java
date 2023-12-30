package core;

import core.enums.Spell;

public class SpellBook {
	
	private Spell spell;
	
	public SpellBook() {
		this.spell = Spell.NONE;
	}
	
	public SpellBook(Spell spell) {
		this.spell = spell;
	}
	
	public Spell getSpell() {
		return this.spell;
	}
	
	@Override
	public String toString() {
		return this.spell == Spell.NONE ? "No Spellbook" : spell.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SpellBook)) {
			return false;
		}
		
		SpellBook other = (SpellBook) o;
		return other.getSpell() == this.spell;
	}
}
