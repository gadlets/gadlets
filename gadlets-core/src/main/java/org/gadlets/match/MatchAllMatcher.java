package org.gadlets.match;

import java.util.Collection;

import org.gadlets.core.GadletInstance;

public class MatchAllMatcher implements GadletsMatcher {

	@Override
	public Collection<GadletInstance> match(Collection<GadletInstance> gadletInstances) {
		return gadletInstances;
	}
	

}
