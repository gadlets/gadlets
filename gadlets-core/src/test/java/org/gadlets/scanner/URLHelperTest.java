package org.gadlets.scanner;

import java.net.MalformedURLException;
import java.net.URL;

import org.testng.Assert;
import org.testng.annotations.Test;

public class URLHelperTest {

	@Test
	public void testGetGadletsURL() throws MalformedURLException {
		String base = "/tjo/flot/META-INF/";
		String relativePath = "my/fine/gadlet.xhtml"; 
		URL gadletsXmlURL = new URL("file", null, base + "gadlets.xml");
		URL gadletsDirURL = URLHelper.getResourceURL(gadletsXmlURL, relativePath);
		Assert.assertEquals("file:" + base + "gadlets/" + relativePath, gadletsDirURL.toExternalForm());
	}
}
