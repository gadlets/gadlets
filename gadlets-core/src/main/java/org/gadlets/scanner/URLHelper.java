package org.gadlets.scanner;

import java.net.MalformedURLException;
import java.net.URL;

class URLHelper {


	public static URL getResourceURL(URL xmlurl, String relativePath) {
		try {
			String urlPath = xmlurl.getPath();
			int lastIndexOf = urlPath.lastIndexOf("/");
			String resourcePath = urlPath.substring(0, lastIndexOf) + "/gadlets/" + relativePath;
			return new URL(xmlurl.getProtocol(), xmlurl.getHost(), xmlurl.getPort(), resourcePath) ;
		} catch (MalformedURLException e) {
			throw new RuntimeException("Internal Error!", e);
		}
	}

}
