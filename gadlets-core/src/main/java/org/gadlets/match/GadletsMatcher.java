package org.gadlets.match;

import java.util.Collection;

import org.gadlets.core.GadletInstance;

public interface GadletsMatcher {

	public Collection<GadletInstance> match(Collection<GadletInstance> gadletInstances);

}
