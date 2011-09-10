package org.gadlets.scanner;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
		Map<String, Gadlet> unresolved = new HashMap<String, Gadlets.Gadlet>();
		Map<String, URL> urls = new HashMap<String, URL>();
		try {
			JAXBContext jc = JAXBContext.newInstance(Gadlets.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			for (URL url : gadletXmlUrls) {
				logger.info("Reading: " + url);
				Gadlets gadlets = (Gadlets) unmarshaller.unmarshal(url);
				List<Gadlet> gadletList = gadlets.getGadlet();
				for (Gadlet gadlet : gadletList) {
					logger.info("Found Gadlet: '" + gadlet.getName() + "'");
					unresolved.put(gadlet.getName(), gadlet);
					urls.put(gadlet.getName(), url);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Unexpected Error!", e);
		}
		//
		//
		logger.info("Resolving Gadlet mappings.");
		Map<String, GadletDefinition> resolved = new HashMap<String, GadletDefinition>();
		Set<Entry<String,Gadlet>> entrySet = unresolved.entrySet();
		for (Entry<String, Gadlet> entry : entrySet) {
			URL url = urls.get(entry.getKey());
			Gadlet gadlet = entry.getValue();
			String resource = gadlet.getResource();
			if(resource != null) {
				URL resourceURL = URLHelper.getResourceURL(url, resource);
				try {
					resourceURL.getContent();
					resolved.put(gadlet.getName(), new GadletDefinition(gadlet.getName(), resourceURL.toExternalForm()));
				} catch (Exception e) {
					logger.error("Failed to find Gadlet resource: " + resourceURL, e);
				}
			} else {
				String extendsGadlet = gadlet.getExtends();
				// TODO: handle extensions ... need to rewrite loop
			}
		}
		logger.info("Register resolved Gadlets.");
		Set<Entry<String,GadletDefinition>> entrySet2 = resolved.entrySet();
		for (Entry<String, GadletDefinition> entry : entrySet2) {
			GadletInstanceRepository.addGadlet(new GadletInstance(entry.getValue()));
		}
		logger.info("Completed Gadlets scanning.");
	}

}
