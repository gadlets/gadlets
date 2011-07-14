package org.gadlets.scanner;

import java.net.URL;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.gadlets.xml.ns.gadlets.Gadlets;

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
			}
		} catch (Exception e) {
			throw new RuntimeException("Unexpected Error!", e);
		}

	}

}
