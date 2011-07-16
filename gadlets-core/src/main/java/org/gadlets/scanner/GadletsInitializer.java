package org.gadlets.scanner;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.gadlets.core.GadletDefinition;
import org.gadlets.core.GadletInstance;
import org.gadlets.core.GadletInstanceRepository;
import org.gadlets.xml.ns.gadlets.Gadlets;
import org.gadlets.xml.ns.gadlets.Gadlets.Gadlet;

public class GadletsInitializer {

	public GadletsInitializer() {
	}

	public void initialize() {
		GadletScanner gadletScanner = new GadletScanner();
		Set<URL> gadletXmlUrls = gadletScanner.getGadletXmlUrls();
		try {
			JAXBContext jc = JAXBContext.newInstance(Gadlets.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			for (URL url : gadletXmlUrls) {
				Gadlets gadlets = (Gadlets) unmarshaller.unmarshal(url);
				List<Gadlet> gadletList = gadlets.getGadlet();
				for (Gadlet gadlet : gadletList) {
					String resource = gadlet.getResource();
					URL resourceURL = URLHelper.getResourceURL(url, resource);
					GadletInstanceRepository
							.addGadlet(new GadletInstance(new GadletDefinition(
									resourceURL.toExternalForm())));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Unexpected Error!", e);
		}

	}

}
