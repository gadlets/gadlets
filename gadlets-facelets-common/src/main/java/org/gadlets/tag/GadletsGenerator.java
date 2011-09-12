package org.gadlets.tag;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Random;

import org.gadlets.core.GadletDefinition;
import org.gadlets.core.GadletInstanceRepository;
import org.gadlets.core.GadletParameter;
import org.gadlets.match.GadletsMatcher;
import org.gadlets.match.MatchAllMatcher;
import org.gadlets.match.NameMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GadletsGenerator {
	
	private static Logger logger = LoggerFactory.getLogger(GadletsGenerator.class);

	public static String generateGadletInclude(IGadletsHandler gadletsHandler, IGadletsVariableMapper variableMapper) throws IOException {
		String name = gadletsHandler.getAttributeValue("name");
		if(name != null) {
			return generateGadletInclude(new NameMatcher(name), variableMapper);
		}
		return generateGadletInclude(new MatchAllMatcher(), variableMapper); 
	}
	
	public static String generateGadletInclude(GadletsMatcher gadletsMatcher, IGadletsVariableMapper variableMapper) throws IOException {
		
		String template = GadletsGenerator.class.getClassLoader()
						.getResource("org/gadlets/tag/decorate.xhtml")
						.toString();

		
		File file = File.createTempFile("gadlets_" + new Random().nextInt(), ".xhtml");
		FileWriter fw = new FileWriter(file);
		fw.write("<html xmlns=\"http://www.w3.org/1999/xhtml\"\n");
		fw.write("xmlns:ui=\"http://java.sun.com/jsf/facelets\">\n");
		
		fw.write("GEN START: " + file.getAbsolutePath() + "<br/>");
		
		Collection<GadletDefinition> instances = gadletsMatcher.match(GadletInstanceRepository.getInstances());
		for (GadletDefinition gadletDefinition : instances) {
			fw.write("<ui:decorate template=\""+ template +"\">");			
			fw.write("  <ui:define name=\"gadlet\">");
			fw.write("    <ui:include src=\""+ gadletDefinition.getPath() +"\">");
			
			Collection<GadletParameter> parameters = gadletDefinition.getParameters();
			for (GadletParameter gadletParameter : parameters) {
				String contextValue = variableMapper.getValue(gadletParameter.getName());
				String value = contextValue != null ? contextValue : gadletParameter.getValue();
				if(value == null && gadletParameter.isRequired()) {
					// Missing required attribute
					logger.warn("Missing value for required argument: " + gadletDefinition.getName() + "/" + gadletParameter.getName());
				} else {
					fw.write("    <ui:param name=\"" + gadletParameter.getName() + "\" value=\"" + value + "\"/>");
				}
			}

			fw.write("    </ui:include>");
			fw.write("  </ui:define>");
			fw.write("</ui:decorate>");			
		}
		
		fw.write("<br/> GEN END");
		
		fw.write("</html>");
		
		fw.close();
		
		return "file://" + file.getAbsolutePath();
		
		
		
	}
}
