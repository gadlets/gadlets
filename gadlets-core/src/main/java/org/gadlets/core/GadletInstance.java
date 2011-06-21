package org.gadlets.core;

import java.util.LinkedList;
import java.util.List;

public class GadletInstance {

	private GadletDefinition gadletDefinition;

	private List<GadletParameter> parameters;
	
	public GadletInstance(GadletDefinition gadletDefinition) {
		this.gadletDefinition = gadletDefinition;
		this.parameters = new LinkedList<GadletParameter>(); 
	}
	
	public void addParameter(String name, String value) {
		parameters.add(new GadletParameter(name, value));
	}

	public GadletDefinition getGadletDefinition() {
		return gadletDefinition;
	}
	
	public List<GadletParameter> getParameters() {
		return parameters;
	}
	
}
