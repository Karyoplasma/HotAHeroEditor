package models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import core.Hero;

public class HeroComboBoxModel extends AbstractListModel<Hero> implements ComboBoxModel<Hero> {

	private static final long serialVersionUID = -5398761852991588727L;
	private List<Hero> heroList;
	private Hero selected;

	public HeroComboBoxModel(List<Hero> heroes, int totalHeroes) {
		this.heroList = heroes;
		List<Hero> toRemove = new ArrayList<Hero>();
		for (Hero hero : this.heroList) {
			if (hero.getHeader().ordinal() > totalHeroes) {
				toRemove.add(hero);
			}
		}
		this.heroList.removeAll(toRemove);
	}
	
	public List<Hero> getHeroes(){
		return this.heroList;
	}
	
	public boolean hasElement(Hero hero) {
		return this.heroList.contains(hero);
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
