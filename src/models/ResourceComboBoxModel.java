package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import core.enums.Resource;

public class ResourceComboBoxModel extends AbstractListModel<Resource> implements ComboBoxModel<Resource> {
	
	private static final long serialVersionUID = -6627839525340299428L;
	private List<Resource> resources;
	private Resource selected;
	
	public ResourceComboBoxModel() {
		this.resources = new ArrayList<Resource>();
		for (Resource resource : Resource.values()) {
			this.resources.add(resource);
		}
	}
	
	@Override
	public int getSize() {
		return this.resources.size();
	}

	@Override
	public Resource getElementAt(int index) {
		return this.resources.get(index);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		this.selected = (Resource) anItem;	
	}

	@Override
	public Object getSelectedItem() {
		return selected;
	}

}
