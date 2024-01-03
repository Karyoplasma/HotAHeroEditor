package models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import core.enums.SpecialtyType;

public class SpecialtyComboBoxModel extends AbstractListModel<SpecialtyType> implements ComboBoxModel<SpecialtyType> {
	
	private static final long serialVersionUID = -2862778595809099710L;
	private List<SpecialtyType> specialtyList;
	private SpecialtyType selected;
	
	public SpecialtyComboBoxModel(boolean isHotA) {
		this.specialtyList = new ArrayList<SpecialtyType>();
		for (SpecialtyType type : SpecialtyType.values()) {
			if (type == SpecialtyType.FREDERICK_SPECIALTY && !isHotA) {
				continue;
			}
			specialtyList.add(type);
		}
	}
	
	@Override
	public int getSize() {
		return specialtyList.size();
	}

	@Override
	public SpecialtyType getElementAt(int index) {
		return specialtyList.get(index);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selected = (SpecialtyType) anItem;	
	}

	@Override
	public Object getSelectedItem() {
		return selected;
	}
}
