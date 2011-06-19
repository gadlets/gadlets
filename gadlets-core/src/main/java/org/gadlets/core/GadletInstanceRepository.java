package org.gadlets.core;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

import org.gadlets.tag.GadletsGenerator;

public class GadletInstanceRepository {

	static private List<GadletInstance> instances;
	
	static {
		instances = new LinkedList<GadletInstance>();
		instances.add(new GadletInstance(GadletsGenerator.class.getClassLoader().getResource("org/gadlets/core/test1.xhtml").toString()));
		instances.add(new GadletInstance(GadletsGenerator.class.getClassLoader().getResource("org/gadlets/core/test2.xhtml").toString()));
	}
	
	static public List<GadletInstance> getInstances() {
		return instances;
	}
	
}
