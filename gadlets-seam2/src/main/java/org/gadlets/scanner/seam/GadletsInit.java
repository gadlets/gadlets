package org.gadlets.scanner.seam;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.log.Log;

@Name("org.gadlets.GadletsInit")
@Scope(ScopeType.APPLICATION)
@Startup
public class GadletsInit {
	
	@Logger private Log log;
	
	@Create
	public void create() {
		log.info("APA");
	}

}
