package actions;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import core.enums.HeroTrait;
import core.enums.SkillLevel;
import gui.HotAHeroEditor;

public class HeroTraitComboBoxListener implements ItemListener{
	
	HotAHeroEditor gui;
	
	public HeroTraitComboBoxListener(HotAHeroEditor hotAHeroEditor) {
		this.gui = hotAHeroEditor;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (this.gui.getComboBoxSecondSkill().getSelectedItem() == HeroTrait.NONE) {
				this.gui.getComboBoxSecondSkillLevel().setSelectedIndex(-1);
				this.gui.getComboBoxSecondSkillLevel().setEnabled(false);
			} else {
				this.gui.getComboBoxSecondSkillLevel().setSelectedItem(SkillLevel.BASIC);
				this.gui.getComboBoxSecondSkillLevel().setEnabled(true);
			}
		}
		
	}

}
