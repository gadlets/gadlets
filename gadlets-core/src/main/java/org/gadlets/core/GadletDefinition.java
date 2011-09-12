package org.gadlets.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GadletDefinition {

	private String name;
	
	private String path;
	
	private boolean isAbstract;

	private Map<String, GadletParameter> name2parameter;

	private List<String> keywords;
	
	public GadletDefinition(String name, String path, boolean isAbstract) {
		this.path = path;
		this.name = name; 
		this.isAbstract = isAbstract;
		this.name2parameter = new HashMap<String, GadletParameter>();
		this.keywords = new ArrayList<String>();
	}

	public void putParameter(String name, String value, boolean required) {
		GadletParameter gadletParameter = new GadletParameter(name, value, required);
		name2parameter.put(name, gadletParameter);
	}

	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}

	public Collection<GadletParameter> getParameters() {
		return name2parameter.values();
	}
	
	public boolean isAbstract() {
		return isAbstract;
	}

	public Collection<String> getKeywords() {
		return keywords;
	}

	public void putKeyword(String keyword) {
		keywords.add(keyword);		
	}

}
