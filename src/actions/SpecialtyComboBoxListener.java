package actions;

import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import core.enums.SpecialtyType;
import gui.HotAHeroEditor;

public class SpecialtyComboBoxListener implements ItemListener {
	HotAHeroEditor gui;
	
	public SpecialtyComboBoxListener(HotAHeroEditor hotAHeroEditor) {
		this.gui = hotAHeroEditor;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			switch ((SpecialtyType) gui.getComboBoxSpecialty().getSelectedItem()) {
			case SKILL_BONUS:
				this.switchToPanel("Skill");
				break;
			case CREATURE_BONUS_LEVEL:
				this.switchToPanel("Creature");
				break;
			case RESOURCE_BONUS:
				this.switchToPanel("Resource");
				break;
			case SPELL_BONUS:
				this.switchToPanel("Spell");
				break;
			case CREATURE_BONUS_STATIC:
				this.switchToPanel("Static");
				break;
			case CREATURE_BONUS_SPEED:
				this.switchToPanel("Speed");
				break;
			case CREATURE_CONVERSION:
				this.switchToPanel("Conversion");
				break;
			case DRAGON_BONUS:
				this.switchToPanel("Dragon");
				break;
			default:
				this.switchToPanel("Empty");
				break;
			
			}
		}
	}

	private void switchToPanel(String string) {
		((CardLayout) gui.getSpecialtyOptionsPanel().getLayout()).show(gui.getSpecialtyOptionsPanel(), string);	
	}

}
