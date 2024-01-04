package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import core.Hero;
import core.SecondarySkill;
import core.Specialty;
import core.SpellBook;
import core.enums.Creature;
import core.enums.HeroTrait;
import core.enums.Resource;
import core.enums.SkillLevel;
import core.enums.SpecialtyType;
import core.enums.Spell;
import core.specialties.AdrienneSpecialty;
import core.specialties.CreatureConversionSpecialty;
import core.specialties.CreatureSpecialty;
import core.specialties.CreatureSpeedSpecialty;
import core.specialties.DragonSpecialty;
import core.specialties.FrederickSpecialty;
import core.specialties.ResourceSpecialty;
import core.specialties.SkillSpecialty;
import core.specialties.SpellSpecialty;
import core.specialties.StaticCreatureSpecialty;
import gui.HotAHeroEditor;
import models.ChangesTableModel;

public class ChangeButtonAction extends AbstractAction {

	private static final long serialVersionUID = 5685199578322214700L;
	private HotAHeroEditor gui;

	public ChangeButtonAction(HotAHeroEditor gui) {
		putValue(Action.NAME, "Change");
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Hero currentHero = (Hero) this.gui.getComboBoxHero().getSelectedItem();
		Specialty specialty = this.getSpecialty();
		if (specialty == null) {
			return;
		}
		HeroTrait trait = (HeroTrait) this.gui.getComboBoxFirstSkill().getSelectedItem();
		SkillLevel level = (SkillLevel) this.gui.getComboBoxFirstSkillLevel().getSelectedItem();
		SecondarySkill firstSkill = new SecondarySkill(trait, level);
		trait = (HeroTrait) this.gui.getComboBoxSecondSkill().getSelectedItem();
		if (trait == HeroTrait.NONE) {
			level = SkillLevel.NONE;
		} else {
			level = (SkillLevel) this.gui.getComboBoxSecondSkillLevel().getSelectedItem();
		}
		SecondarySkill secondSkill = new SecondarySkill(trait, level);
		Spell spell = (Spell) this.gui.getComboBoxSpell().getSelectedItem();
		SpellBook spellBook = new SpellBook(spell);
		Creature[] startingTroops = new Creature[3];
		startingTroops[0] = (Creature) this.gui.getComboBoxFirstTroop().getSelectedItem();
		startingTroops[1] = (Creature) this.gui.getComboBoxSecondTroop().getSelectedItem();
		startingTroops[2] = (Creature) this.gui.getComboBoxThirdTroop().getSelectedItem();

		currentHero.setSpecialty(specialty);
		currentHero.setFirstSkill(firstSkill);
		currentHero.setSecondSkill(secondSkill);
		currentHero.setSpellBook(spellBook);
		currentHero.setStartingTroops(startingTroops);
		
		((ChangesTableModel) this.gui.getTableChanges().getModel()).proposeChange(currentHero);
	}

	private Specialty getSpecialty() {
		switch ((SpecialtyType) this.gui.getComboBoxSpecialty().getSelectedItem()) {
		case SKILL_BONUS:
			HeroTrait trait = (HeroTrait) this.gui.getSpecialtySkill().getSelectedItem();
			if (trait == null) {
				JOptionPane.showMessageDialog(this.gui.getFrame(), "Select a skill to specialize in!", "No skill",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			}
			return new SkillSpecialty(trait);
		case CREATURE_BONUS_LEVEL:
			Creature creature = (Creature) this.gui.getSpecialtyCreature().getSelectedItem();
			if (creature == null) {
				JOptionPane.showMessageDialog(this.gui.getFrame(), "Select a creature to specialize in!", "No creature",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			}
			return new CreatureSpecialty(creature);
		case RESOURCE_BONUS:
			Resource resource = (Resource) this.gui.getSpecialtyResources().getSelectedItem();
			if (resource == null) {
				JOptionPane.showMessageDialog(this.gui.getFrame(), "Select a resource to specialize in!", "No resource",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			}
			return new ResourceSpecialty(resource);
		case SPELL_BONUS:
			Spell spell = (Spell) this.gui.getSpecialtySpell().getSelectedItem();
			if (spell == null) {
				JOptionPane.showMessageDialog(this.gui.getFrame(), "Select a spell to specialize in!", "No spell",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			}
			return new SpellSpecialty(spell);
		case CREATURE_BONUS_STATIC:
			Creature creatureStatic = (Creature) this.gui.getSpecialtyCreatureStatic().getSelectedItem();
			Integer staticAttack = (Integer) this.gui.getSpecialtyCreatureAttack().getValue();
			Integer staticDefense = (Integer) this.gui.getSpecialtyCreatureDefense().getValue();
			Integer staticDamage = (Integer) this.gui.getSpecialtyCreatureDamage().getValue();
			if (creatureStatic == null) {
				JOptionPane.showMessageDialog(this.gui.getFrame(), "Select a creature to specialize in!", "No creature",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			}
			return new StaticCreatureSpecialty(creatureStatic, Integer.reverseBytes(staticAttack),
					Integer.reverseBytes(staticDefense), Integer.reverseBytes(staticDamage));
		case CREATURE_BONUS_SPEED:
			Integer speed = (Integer) this.gui.getSpecialtySpeed().getValue();
			return new CreatureSpeedSpecialty(Integer.reverseBytes(speed));
		case CREATURE_CONVERSION:
			Creature allowed1 = (Creature) this.gui.getSpecialtyFirstConversion().getSelectedItem();
			Creature allowed2 = (Creature) this.gui.getSpecialtySecondConversion().getSelectedItem();
			Creature result = (Creature) this.gui.getSpecialtyConversionResult().getSelectedItem();
			if (allowed1 == null || allowed2 == null) {
				JOptionPane.showMessageDialog(this.gui.getFrame(), "Select two creatures to convert!",
						"Insufficient conversion", JOptionPane.INFORMATION_MESSAGE);
				break;
			}
			if (result == null) {
				JOptionPane.showMessageDialog(this.gui.getFrame(), "Select a creature to convert into!",
						"No conversion target", JOptionPane.INFORMATION_MESSAGE);
				break;
			}
			if (allowed1 == result || allowed2 == result || allowed1 == allowed2) {
				JOptionPane.showMessageDialog(this.gui.getFrame(), "The creatures you've selected are not different!",
						"Same creatures", JOptionPane.INFORMATION_MESSAGE);
				break;
			}
			return new CreatureConversionSpecialty(allowed1, allowed2, result);
		case DRAGON_BONUS:
			Integer dragonAttack = (Integer) this.gui.getSpecialtyDragonAttack().getValue();
			Integer dragonDefense = (Integer) this.gui.getSpecialtyDragonDefense().getValue();
			return new DragonSpecialty(Integer.reverseBytes(dragonAttack), Integer.reverseBytes(dragonDefense));
		case ADRIENNE_SPECIALTY:
			return new AdrienneSpecialty();
		case FREDERICK_SPECIALTY:
			return new FrederickSpecialty();
		}
		return null;
	}

}
