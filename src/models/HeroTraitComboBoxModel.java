package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import core.enums.HeroTrait;

public class HeroTraitComboBoxModel extends AbstractListModel<HeroTrait> implements ComboBoxModel<HeroTrait> {
	
	private static final long serialVersionUID = -7510224796618835274L;
	private List<HeroTrait> heroTraits;
	private HeroTrait selected;
	
	public HeroTraitComboBoxModel(boolean isHota, boolean isSecondSkill, boolean isSpecialty) {
		this.heroTraits = new ArrayList<HeroTrait>();
		for (HeroTrait trait : HeroTrait.values()) {
			if (trait == HeroTrait.INTERFERENCE && !isHota) {
				continue;
			}
			heroTraits.add(trait);
		}
		this.heroTraits.remove(HeroTrait.DEBUG);
		if (!isSecondSkill) {
			this.heroTraits.remove(HeroTrait.NONE);
		}
		if (isSpecialty) {
			this.heroTraits.remove(HeroTrait.ARTILLERY);
		}
	}
	
	@Override
	public int getSize() {
		return heroTraits.size();
	}

	@Override
	public HeroTrait getElementAt(int index) {
		return heroTraits.get(index);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		this.selected = (HeroTrait) anItem;
		
	}

	@Override
	public Object getSelectedItem() {
		return selected;
	}

}
