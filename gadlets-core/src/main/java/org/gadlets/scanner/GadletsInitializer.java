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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GadletsInitializer {

	private Logger logger = LoggerFactory.getLogger(GadletsInitializer.class);

	public GadletsInitializer() {
	}

	public void initialize() {
		logger.info("Starting Gadlets scanning.");
		GadletScanner gadletScanner = new GadletScanner();
		Set<URL> gadletXmlUrls = gadletScanner.getGadletXmlUrls();
		try {
			JAXBContext jc = JAXBContext.newInstance(Gadlets.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			for (URL url : gadletXmlUrls) {
				logger.info("Reading: " + url);
				Gadlets gadlets = (Gadlets) unmarshaller.unmarshal(url);
				List<Gadlet> gadletList = gadlets.getGadlet();
				for (Gadlet gadlet : gadletList) {
					logger.info("Found Gadlet: '" + gadlet.getName() + "'");
					String resource = gadlet.getResource();
					URL resourceURL = URLHelper.getResourceURL(url, resource);
					try {
						resourceURL.getContent();
						GadletInstanceRepository.addGadlet(new GadletInstance(
								new GadletDefinition(gadlet.getName(), resourceURL.toExternalForm())));
					} catch (Exception e) {
						logger.error("Failed to find Gadlet resource: " + resourceURL, e);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Unexpected Error!", e);
		}
		logger.info("Completed Gadlets scanning.");
	}

}
