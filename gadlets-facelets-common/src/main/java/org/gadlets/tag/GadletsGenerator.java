package org.gadlets.tag;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.gadlets.core.GadletDefinition;
import org.gadlets.core.GadletInstanceRepository;
import org.gadlets.core.GadletParameter;
import org.gadlets.match.GadletsMatcher;
import org.gadlets.match.KeywordMatcher;
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
		String keyword = gadletsHandler.getAttributeValue("keyword");
		if(keyword != null) {
			return generateGadletInclude(new KeywordMatcher(keyword), variableMapper);
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
			Map<String, String> resolvArguments = resolvArguments(gadletDefinition, variableMapper);
			if(resolvArguments != null) {
				fw.write("<ui:decorate template=\""+ template +"\">");			
				fw.write("  <ui:define name=\"gadlet\">");
				fw.write("    <ui:include src=\""+ gadletDefinition.getPath() +"\">");
				
				Set<Entry<String,String>> entrySet = resolvArguments.entrySet();
				for (Entry<String, String> entry : entrySet) {
					fw.write("    <ui:param name=\"" + entry.getKey() + "\" value=\"" + entry.getValue() + "\"/>");
				}
	
				fw.write("    </ui:include>");
				fw.write("  </ui:define>");
				fw.write("</ui:decorate>");	
			}
		}
		
		fw.write("<br/> GEN END");
		
		fw.write("</html>");
		
		fw.close();
		
		return "file://" + file.getAbsolutePath();
	}
	
	private static Map<String, String> resolvArguments(GadletDefinition gadletDefinition, IGadletsVariableMapper variableMapper) {
		Map<String, String> vmap = new HashMap<String, String>();
		Collection<GadletParameter> parameters = gadletDefinition.getParameters();
		for (GadletParameter gadletParameter : parameters) {
			String contextValue = variableMapper.getValue(gadletParameter.getName());
			String value = contextValue != null ? contextValue : gadletParameter.getValue();
			vmap.put(gadletParameter.getName(), value);
			// If this is abstract is it ok to skip this 
			if(value == null && gadletParameter.isRequired() && gadletDefinition.isAbstract()) {
				return null;
			}
			// If it is not abstract missing argument is an error 
			if(value == null && gadletParameter.isRequired() && !gadletDefinition.isAbstract()) {
				throw new RuntimeException("Missing value for required argument: " + gadletDefinition.getName() + "/" + gadletParameter.getName());
			}			
			if(value != null) {
				vmap.put(gadletParameter.getName(), value);
			}
		}
		return vmap;
	}
}
