package org.gadlets.core;

import java.util.LinkedList;
import java.util.List;

public class GadletDefinition {

	private String name;
	
	private String path;

	private List<GadletParameter> parameters;

	public GadletDefinition(String name, String path) {
		this.path = path;
		this.name = name; 
		this.parameters = new LinkedList<GadletParameter>();
	}

	public void addParameter(String name, String value) {
		parameters.add(new GadletParameter(name, value));
	}

	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}

	public List<GadletParameter> getParameters() {
		return parameters;
	}

}
