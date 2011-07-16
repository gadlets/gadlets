package org.gadlets.core;

import java.util.LinkedList;
import java.util.List;

public class GadletInstanceRepository {

	static private List<GadletInstance> instances;

	static {
		instances = new LinkedList<GadletInstance>();
		instances
				.add(new GadletInstance(new GadletDefinition(
						GadletInstanceRepository.class.getClassLoader()
								.getResource("org/gadlets/core/test1.xhtml")
								.toString())));
		instances
				.add(new GadletInstance(new GadletDefinition(
						GadletInstanceRepository.class.getClassLoader()
								.getResource("org/gadlets/core/test2.xhtml")
								.toString())));

		GadletInstance twitter1 = new GadletInstance(new GadletDefinition(
				GadletInstanceRepository.class.getClassLoader()
						.getResource("org/gadlets/core/twitter.xhtml")
						.toString()));
		twitter1.addParameter("user", "twitter");
		instances.add(twitter1);

		GadletInstance twitter2 = new GadletInstance(new GadletDefinition(
				GadletInstanceRepository.class.getClassLoader()
						.getResource("org/gadlets/core/twitter.xhtml")
						.toString()));
		twitter2.addParameter("user", "NASA");
		instances.add(twitter2);
	}

	static public void addGadlet(GadletInstance gadletInstance) {
		instances.add(gadletInstance);
	}

	static public List<GadletInstance> getInstances() {
		return instances;
	}

}
