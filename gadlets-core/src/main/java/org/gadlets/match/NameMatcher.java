package org.gadlets.match;

import java.util.ArrayList;
import java.util.Collection;

import org.gadlets.core.GadletInstance;

public class NameMatcher implements GadletsMatcher {

	private String name;
	
	public NameMatcher(String name) {
		this.name = name;
	}

	public Collection<GadletInstance> match(Collection<GadletInstance> gadletInstances) {
		ArrayList<GadletInstance> list = new ArrayList<GadletInstance>(1);
		for (GadletInstance gadletInstance : gadletInstances) {
			if(gadletInstance.getGadletDefinition().getName().equals(name)) {
				 list.add(gadletInstance);
			}
		}
		return list;
	}

}
