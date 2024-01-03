package models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import core.enums.SkillLevel;

public class SkillLevelComboBoxModel extends AbstractListModel<SkillLevel> implements ComboBoxModel<SkillLevel> {

	private static final long serialVersionUID = 3098376213421212410L;
	List<SkillLevel> skillLevels;
	SkillLevel selected;
	
	public SkillLevelComboBoxModel() {
		this.skillLevels = new ArrayList<SkillLevel>();
		for (SkillLevel level : SkillLevel.values()) {
			this.skillLevels.add(level);
		}
		this.skillLevels.remove(SkillLevel.NONE);
	}
	
	@Override
	public int getSize() {
		return skillLevels.size();
	}

	@Override
	public SkillLevel getElementAt(int index) {
		return skillLevels.get(index);
	}
	
	@Override
	public void setSelectedItem(Object anItem) {
		selected = (SkillLevel) anItem;		
	}
	
	@Override
	public Object getSelectedItem() {
		return selected;
	}
}
