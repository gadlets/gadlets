package org.gadlets.core;

public class GadletParameter {
	
	private String name;

	private String value;
	
	private boolean required;

	public GadletParameter(String name, String value, boolean required) {
		this.name = name;
		this.value = value;
		this.required = required;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
	
	public boolean isRequired() {
		return required;
	}

}
