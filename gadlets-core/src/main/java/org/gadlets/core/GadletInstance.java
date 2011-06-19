package org.gadlets.core;

public class GadletInstance {

	private GadletDefinition gadletDefinition;

	public GadletInstance(GadletDefinition gadletDefinition) {
		this.gadletDefinition = gadletDefinition;
	}

	public GadletDefinition getGadletDefinition() {
		return gadletDefinition;
	}

}
