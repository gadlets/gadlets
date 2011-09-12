package org.gadlets.match;

import java.util.ArrayList;
import java.util.Collection;

import org.gadlets.core.GadletDefinition;

public class KeywordMatcher implements GadletsMatcher {

	private String keyword;
	
	public KeywordMatcher(String keyword) {
		this.keyword = keyword;
	}

	public Collection<GadletDefinition> match(Collection<GadletDefinition> gadletDefinitions) {
		ArrayList<GadletDefinition> list = new ArrayList<GadletDefinition>(1);
		for (GadletDefinition gadletDefinition : gadletDefinitions) {
			if(gadletDefinition.getKeywords().contains(keyword)) {
				 list.add(gadletDefinition);
			}
		}
		return list;
	}

}
