package org.gadlets.tag;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.gadlets.core.GadletInstance;
import org.gadlets.core.GadletInstanceRepository;
import org.gadlets.core.GadletParameter;

public class GadletsGenerator {

	public static String generateGadletInclude() throws IOException {
		
		String template = GadletsGenerator.class.getClassLoader()
						.getResource("org/gadlets/tag/decorate.xhtml")
						.toString();

		
		File file = File.createTempFile("gadlets_" + new Random().nextInt(), ".xhtml");
		FileWriter fw = new FileWriter(file);
		fw.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		fw.write("<html xmlns=\"http://www.w3.org/1999/xhtml\"\n");
		fw.write("xmlns:ui=\"http://java.sun.com/jsf/facelets\">\n");
		
		fw.write("GEN START: " + file.getAbsolutePath() + "<br/>");
		
		List<GadletInstance> instances = GadletInstanceRepository.getInstances();
		for (GadletInstance gadletInstance : instances) {
			fw.write("<ui:decorate template=\""+ template +"\">");			
			fw.write("  <ui:define name=\"gadlet\">");
			fw.write("    <ui:include src=\""+ gadletInstance.getGadletDefinition().getPath() +"\">");
			
			List<GadletParameter> parameters = gadletInstance.getParameters();
			for (GadletParameter gadletParameter : parameters) {
				fw.write("    <ui:param name=\"" + gadletParameter.getName() + "\" value=\"" + gadletParameter.getValue() + "\"/>");				
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
