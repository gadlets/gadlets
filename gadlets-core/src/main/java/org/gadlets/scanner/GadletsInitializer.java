package org.gadlets.scanner;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.gadlets.core.GadletDefinition;
import org.gadlets.core.GadletInstanceRepository;
import org.gadlets.core.GadletParameter;
import org.gadlets.xml.ns.gadlets.Gadlets;
import org.gadlets.xml.ns.gadlets.Gadlets.Gadlet;
import org.gadlets.xml.ns.gadlets.Gadlets.Gadlet.Argument;
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
		ArrayList<Entry<String, Gadlet>> unresolvedList = new ArrayList<Entry<String, Gadlet>>(unresolved.entrySet());
		while(unresolvedList.size() > 0) {
			Entry<String, Gadlet> entry = unresolvedList.remove(0);
			URL url = urls.get(entry.getKey());
			Gadlet gadlet = entry.getValue();
			String resource = gadlet.getResource();
			if(resource != null) {
				URL resourceURL = URLHelper.getResourceURL(url, resource);
				try {
					resourceURL.getContent();
					GadletDefinition gadletDefinition = 
							new GadletDefinition(gadlet.getName(), resourceURL.toExternalForm(), gadlet.isAbstract());
					List<Argument> argument = gadlet.getArgument();
					for (Argument argument2 : argument) {
						gadletDefinition.putParameter(argument2.getName(), argument2.getValue(), argument2.isRequired());
					}
					resolved.put(gadlet.getName(), gadletDefinition);
				} catch (Exception e) {
					logger.error("Failed to find Gadlet resource: " + resourceURL, e);
				}
			} else {
				String extendsGadlet = gadlet.getExtends();
				GadletDefinition resolvedGadletDefinition = resolved.get(extendsGadlet);
				Gadlet unresolvedGadletDefinition = unresolved.get(extendsGadlet);
				if(resolvedGadletDefinition != null) {
					// Found defined gadlet to extends - we're all set
					GadletDefinition extendedGadletDefinition = 
							new GadletDefinition(gadlet.getName(), resolvedGadletDefinition.getPath(), gadlet.isAbstract());
					Collection<GadletParameter> parameters = resolvedGadletDefinition.getParameters();
					for (GadletParameter gadletParameter : parameters) {
						extendedGadletDefinition.putParameter(gadletParameter.getName(), gadletParameter.getValue(), gadletParameter.isRequired());
					}
					List<Argument> arguments = gadlet.getArgument();
					for (Argument argument : arguments) {
						extendedGadletDefinition.putParameter(argument.getName(), argument.getValue(), argument.isRequired());
					}
					resolved.put(gadlet.getName(), extendedGadletDefinition);
				} else if(unresolvedGadletDefinition != null) {
					// Right - we'll try later when it is resolved
					unresolvedList.add(entry);
				} else {
					// Extension is invalid - nothing to extend
					logger.error("Failed to find Gadlet for extension: " + gadlet.getName() + " <- " + extendsGadlet);
				}
			}
		}
		logger.info("Register resolved Gadlets.");
		Set<Entry<String,GadletDefinition>> entrySet2 = resolved.entrySet();
		for (Entry<String, GadletDefinition> entry : entrySet2) {
			logger.info("Register: " + entry.getKey());
			GadletInstanceRepository.addGadlet(entry.getValue());
		}
		logger.info("Completed Gadlets scanning.");
	}

}
