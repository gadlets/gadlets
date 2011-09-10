package org.gadlets.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GadletInstanceRepository {

	static private List<GadletDefinition> definitions;

	static {
		definitions = new LinkedList<GadletDefinition>();
	}

	static public void addGadlet(GadletDefinition gadletDefinition) {
		definitions.add(gadletDefinition);
	}

	static public List<GadletDefinition> getInstances() {
		ArrayList<GadletDefinition> instances = new ArrayList<GadletDefinition>(definitions.size());
		for (GadletDefinition gadletDefinition : definitions) {
			if(!gadletDefinition.isAbstract()) {
				instances.add(gadletDefinition);
			}
		}
		return instances;
	}

}
