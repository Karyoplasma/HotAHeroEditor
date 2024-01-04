package actions;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JSpinner;

import core.Hero;
import core.specialties.CreatureConversionSpecialty;
import core.specialties.CreatureSpecialty;
import core.specialties.CreatureSpeedSpecialty;
import core.specialties.DragonSpecialty;
import core.specialties.ResourceSpecialty;
import core.specialties.SkillSpecialty;
import core.specialties.SpellSpecialty;
import core.specialties.StaticCreatureSpecialty;
import gui.HotAHeroEditor;

public class HeroComboBoxItemListener implements ItemListener {

	private HotAHeroEditor gui;
	private List<Component> componentList;

	public HeroComboBoxItemListener(HotAHeroEditor gui) {
		this.gui = gui;
		this.componentList = new ArrayList<Component>();
		this.componentList.add(gui.getSpecialtySkill());
		this.componentList.add(gui.getSpecialtyCreature());
		this.componentList.add(gui.getSpecialtyResources());
		this.componentList.add(gui.getSpecialtySpell());
		this.componentList.add(gui.getSpecialtyCreatureStatic());
		this.componentList.add(gui.getSpecialtyCreatureAttack());
		this.componentList.add(gui.getSpecialtyCreatureDefense());
		this.componentList.add(gui.getSpecialtyCreatureDamage());
		this.componentList.add(gui.getSpecialtySpeed());
		this.componentList.add(gui.getSpecialtyFirstConversion());
		this.componentList.add(gui.getSpecialtySecondConversion());
		this.componentList.add(gui.getSpecialtyConversionResult());
		this.componentList.add(gui.getSpecialtyDragonAttack());
		this.componentList.add(gui.getSpecialtyDragonDefense());

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (gui.getComboBoxHero().getSelectedIndex() == -1) {
				return;
			}
			Hero hero = (Hero) gui.getComboBoxHero().getSelectedItem();
			this.gui.getComboBoxFirstSkill().setSelectedItem(hero.getFirstSkill().getTrait());
			this.gui.getComboBoxSecondSkill().setSelectedItem(hero.getSecondSkill().getTrait());
			this.gui.getComboBoxFirstSkillLevel().setSelectedItem(hero.getFirstSkill().getLevel());
			this.gui.getComboBoxSecondSkillLevel().setSelectedItem(hero.getSecondSkill().getLevel());			
			this.gui.getComboBoxSpell().setSelectedItem(hero.getSpell());
			this.gui.getComboBoxFirstTroop().setSelectedItem(hero.getStartingTroops()[0]);
			this.gui.getComboBoxSecondTroop().setSelectedItem(hero.getStartingTroops()[1]);
			this.gui.getComboBoxThirdTroop().setSelectedItem(hero.getStartingTroops()[2]);
			switch (hero.getSpecialty().getType()) {
			case SKILL_BONUS:
				this.gui.getSpecialtySkill().setSelectedItem(((SkillSpecialty) hero.getSpecialty()).getSkill());
				this.resetOthersToDefault(this.gui.getSpecialtySkill());
				break;
			case CREATURE_BONUS_LEVEL:
				this.gui.getSpecialtyCreature()
						.setSelectedItem(((CreatureSpecialty) hero.getSpecialty()).getCreature());
				this.resetOthersToDefault(this.gui.getSpecialtyCreature());
				break;
			case RESOURCE_BONUS:
				this.gui.getSpecialtyResources()
						.setSelectedItem(((ResourceSpecialty) hero.getSpecialty()).getResource());
				this.resetOthersToDefault(this.gui.getSpecialtyResources());
				break;
			case SPELL_BONUS:
				this.gui.getSpecialtySpell().setSelectedItem(((SpellSpecialty) hero.getSpecialty()).getSpell());
				this.resetOthersToDefault(this.gui.getSpecialtySpell());
				break;
			case CREATURE_BONUS_STATIC:
				this.gui.getSpecialtyCreatureAttack()
						.setValue(Integer.reverseBytes(((StaticCreatureSpecialty) hero.getSpecialty()).getAttack()));
				this.gui.getSpecialtyCreatureDefense()
						.setValue(Integer.reverseBytes(((StaticCreatureSpecialty) hero.getSpecialty()).getDefense()));
				this.gui.getSpecialtyCreatureDamage()
						.setValue(Integer.reverseBytes(((StaticCreatureSpecialty) hero.getSpecialty()).getDamage()));
				this.gui.getSpecialtyCreatureStatic()
						.setSelectedItem(((StaticCreatureSpecialty) hero.getSpecialty()).getCreature());
				this.resetOthersToDefault(this.gui.getSpecialtyCreatureAttack(), this.gui.getSpecialtyCreatureDefense(),
						this.gui.getSpecialtyCreatureDamage(), this.gui.getSpecialtyCreatureStatic());
				break;
			case CREATURE_BONUS_SPEED:
				this.gui.getSpecialtySpeed().setValue(Integer.reverseBytes(((CreatureSpeedSpecialty) hero.getSpecialty()).getBonus()));
				this.resetOthersToDefault(this.gui.getSpecialtySpeed());
				break;
			case CREATURE_CONVERSION:
				this.gui.getSpecialtyFirstConversion()
						.setSelectedItem(((CreatureConversionSpecialty) hero.getSpecialty()).getFirstAllowed());
				this.gui.getSpecialtySecondConversion()
						.setSelectedItem(((CreatureConversionSpecialty) hero.getSpecialty()).getSecondAllowed());
				this.gui.getSpecialtyConversionResult()
						.setSelectedItem(((CreatureConversionSpecialty) hero.getSpecialty()).getResult());
				this.resetOthersToDefault(this.gui.getSpecialtyFirstConversion(),
						this.gui.getSpecialtySecondConversion(), this.gui.getSpecialtyConversionResult());
				break;
			case DRAGON_BONUS:
				this.gui.getSpecialtyDragonAttack().setValue(Integer.reverseBytes(((DragonSpecialty) hero.getSpecialty()).getAttack()));
				this.gui.getSpecialtyDragonDefense().setValue(Integer.reverseBytes(((DragonSpecialty) hero.getSpecialty()).getDefense()));
				this.resetOthersToDefault(this.gui.getSpecialtyDragonAttack(), this.gui.getSpecialtyDragonDefense());
				break;
			default:
				this.resetAllSpecialtiesToDefault();
				break;
			}
			this.gui.getComboBoxSpecialty().setSelectedItem(hero.getSpecialty().getType());
			gui.getFrame().repaint();
		}
	}

	private void resetAllSpecialtiesToDefault() {
		for (Component component : componentList) {
			resetComponentToDefault(component);
		}
	}

	private void resetOthersToDefault(Component... components) {
		for (Component component : componentList) {
			boolean contains = Arrays.stream(components).anyMatch(item -> item.equals(component));
			if (!contains) {
				resetComponentToDefault(component);
			}
		}
	}

	private void resetComponentToDefault(Component component) {
		if (component instanceof JSpinner) {
			((JSpinner) component).setValue(0);
		} else if (component instanceof JComboBox<?>) {
			((JComboBox<?>) component).setSelectedIndex(-1);
		}
	}

}
