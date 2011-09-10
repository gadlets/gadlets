package org.gadlets.match;

import java.util.ArrayList;
import java.util.Collection;

import org.gadlets.core.GadletDefinition;

public class NameMatcher implements GadletsMatcher {

	private String name;
	
	public NameMatcher(String name) {
		this.name = name;
	}

	public Collection<GadletDefinition> match(Collection<GadletDefinition> gadletDefinitions) {
		ArrayList<GadletDefinition> list = new ArrayList<GadletDefinition>(1);
		for (GadletDefinition gadletDefinition : gadletDefinitions) {
			if(gadletDefinition.getName().equals(name)) {
				 list.add(gadletDefinition);
			}
		}
		return list;
	}

}
