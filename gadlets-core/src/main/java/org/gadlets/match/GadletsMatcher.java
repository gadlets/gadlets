package org.gadlets.match;

import java.util.Collection;

import org.gadlets.core.GadletDefinition;

public interface GadletsMatcher {

	public Collection<GadletDefinition> match(Collection<GadletDefinition> gadletDefinitions);

}
