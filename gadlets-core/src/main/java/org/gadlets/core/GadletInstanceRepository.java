package org.gadlets.core;

import java.util.LinkedList;
import java.util.List;

public class GadletInstanceRepository {

	static private List<GadletInstance> instances;

	static {
		instances = new LinkedList<GadletInstance>();
	}

	static public void addGadlet(GadletInstance gadletInstance) {
		instances.add(gadletInstance);
	}

	static public List<GadletInstance> getInstances() {
		return instances;
	}

}
