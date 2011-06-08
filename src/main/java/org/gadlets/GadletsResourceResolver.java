package org.gadlets;

import java.net.MalformedURLException;
import java.net.URL;

import javax.faces.view.facelets.ResourceResolver;

public class GadletsResourceResolver extends ResourceResolver {

	private ResourceResolver resourceResolver;
	
	public GadletsResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	@Override
	public URL resolveUrl(String path) {
		System.out.println(path);
		if(path.contains("home")) {
			try {
				return new URL("file:///tmp/gt.xhtml");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resourceResolver.resolveUrl(path);
	}

}
