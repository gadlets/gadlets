package org.gadlets.scanner.cdi;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

import org.gadlets.scanner.GadletsInitializer;

public class GadletsCdiInitializer implements Extension {

	void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd) {
		new GadletsInitializer().initialize();
	}
}
