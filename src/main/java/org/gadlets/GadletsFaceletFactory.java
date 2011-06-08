package org.gadlets;

import java.io.IOException;
import java.net.URL;

import javax.faces.view.facelets.ResourceResolver;

import com.sun.faces.facelets.Facelet;
import com.sun.faces.facelets.FaceletFactory;

public class GadletsFaceletFactory extends FaceletFactory {

	private FaceletFactory faceletFactory;

	public GadletsFaceletFactory(FaceletFactory faceletFactory) {
		this.faceletFactory = faceletFactory;
	}

	public Facelet getFacelet(String uri) throws IOException {
		System.out.println("uri:" + uri);
		return faceletFactory.getFacelet(uri);
	}

	public Facelet getFacelet(URL url) throws IOException {
		System.out.println("url:" + url);
		return faceletFactory.getFacelet(url);
	}

	public Facelet getMetadataFacelet(String uri) throws IOException {
		return faceletFactory.getMetadataFacelet(uri);
	}

	public Facelet getMetadataFacelet(URL url) throws IOException {
		return faceletFactory.getMetadataFacelet(url);
	}

	public ResourceResolver getResourceResolver() {
		return faceletFactory.getResourceResolver();
	}

	public long getRefreshPeriod() {
		return faceletFactory.getRefreshPeriod();
	}

}
