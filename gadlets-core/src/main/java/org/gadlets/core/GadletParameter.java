package org.gadlets.core;

public class GadletParameter {
	
	private String name;

	private String value;

	public GadletParameter(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

}
