package org.gadlets.scanner.seam;

import static org.jboss.seam.annotations.Install.BUILT_IN;

import org.gadlets.scanner.GadletsInitializer;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.log.Log;

@Name("org.gadlets.GadletsInit")
@Scope(ScopeType.APPLICATION)
@Startup
@Install(precedence=BUILT_IN)
public class GadletsSeam2Initializer {
	
	@Logger private Log log;
	
	@Create
	public void create() {
		new GadletsInitializer().initialize();
	}

}
