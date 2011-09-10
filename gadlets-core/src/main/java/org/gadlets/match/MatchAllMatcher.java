package org.gadlets.match;

import java.util.Collection;

import org.gadlets.core.GadletDefinition;

public class MatchAllMatcher implements GadletsMatcher {


	@Override
	public Collection<GadletDefinition> match(Collection<GadletDefinition> gadletDefinitions) {
		return gadletDefinitions;
	}
	

}
