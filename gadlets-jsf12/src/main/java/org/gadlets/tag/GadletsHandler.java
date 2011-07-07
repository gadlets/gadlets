package org.gadlets.tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.el.VariableMapper;
import javax.faces.component.UIComponent;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.el.VariableMapperWrapper;
import com.sun.facelets.tag.TagAttributeException;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

public class GadletsHandler extends TagHandler {


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
		String path = GadletsGenerator.generateGadletInclude();
		if (path == null || path.length() == 0) {
			return;
		}
        VariableMapper orig = ctx.getVariableMapper();
        ctx.setVariableMapper(new VariableMapperWrapper(orig));
		try {
			ctx.includeFacelet(parent, path);
        } catch (IOException e) {
            throw new TagAttributeException(this.tag, null, "Invalid path : " + path);            
        } finally {
            ctx.setVariableMapper(orig);
        }
	}
}
