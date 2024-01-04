package models;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import core.Hero;

public class HeroComboBoxModel extends AbstractListModel<Hero> implements ComboBoxModel<Hero> {

	private static final long serialVersionUID = -5398761852991588727L;
	private List<Hero> heroList;
	private Hero selected;

	public HeroComboBoxModel(List<Hero> heroes) {
		this.heroList = heroes;
	}

	@Override
	public int getSize() {
		return heroList.size();
	}

	@Override
	public Hero getElementAt(int index) {
		if (index < 0 || index >= heroList.size()) {
			return null;
		}
		return heroList.get(index);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selected = (Hero) anItem;
	}

	@Override
	public Object getSelectedItem() {
		return selected;
	}
}
