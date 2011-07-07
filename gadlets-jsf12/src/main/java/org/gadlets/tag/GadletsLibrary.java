package org.gadlets.tag;

import com.sun.facelets.tag.AbstractTagLibrary;

public class GadletsLibrary extends AbstractTagLibrary {

    public final static String Namespace = "http://gadlets.org/gadlets/core";

    public final static GadletsLibrary Instance = new GadletsLibrary();

	public GadletsLibrary() {
        super(Namespace);

        this.addTagHandler("gadlets", GadletsHandler.class);
	}

}
