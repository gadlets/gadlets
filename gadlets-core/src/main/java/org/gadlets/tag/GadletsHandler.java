package org.gadlets.tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.el.VariableMapper;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;
import javax.faces.view.facelets.TagConfig;

import com.sun.faces.facelets.el.VariableMapperWrapper;
import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.util.FacesLogger;

public class GadletsHandler extends TagHandlerImpl {

	private static final Logger log = FacesLogger.FACELETS_INCLUDE.getLogger();

	/**
	 * @param config
	 */
	public GadletsHandler(TagConfig config) {
		super(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sun.facelets.FaceletHandler#apply(com.sun.facelets.FaceletContext,
	 * javax.faces.component.UIComponent)
	 */
	public void apply(FaceletContext ctx, UIComponent parent)
			throws IOException {
		String path = "file:///tmp/gt.xhtml";
		if (path == null || path.length() == 0) {
			return;
		}
		VariableMapper orig = ctx.getVariableMapper();
		ctx.setVariableMapper(new VariableMapperWrapper(orig));
		try {
			this.nextHandler.apply(ctx, null);
			ctx.includeFacelet(parent, path);
		} catch (IOException e) {
			if (log.isLoggable(Level.FINE)) {
				log.log(Level.FINE, e.toString(), e);
			}
			throw new TagAttributeException(this.tag, null,
					"Invalid path : " + path);
		} finally {
			ctx.setVariableMapper(orig);
		}
	}
}
