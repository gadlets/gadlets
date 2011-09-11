package org.gadlets.tag;

import java.io.IOException;

import javax.el.VariableMapper;
import javax.faces.component.UIComponent;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagAttributeException;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

public class GadletsHandler extends TagHandler implements IGadletsHandler {

	public GadletsHandler(TagConfig config) {
		super(config);
	}
	
	@Override
	public String getAttributeValue(String localName) {
		TagAttribute attribute = getAttribute(localName);
		if(attribute != null) {
			return attribute.getValue();
		}
		return null;
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
        VariableMapper orig = ctx.getVariableMapper();
        VariableMapperWrapper variableMapperWrapper = new VariableMapperWrapper(orig);
        ctx.setVariableMapper(variableMapperWrapper);
		nextHandler.apply(ctx, parent);

		String path = GadletsGenerator.generateGadletInclude(this, variableMapperWrapper);
		if (path == null || path.length() == 0) {
			return;
		}
		
		try {
			ctx.includeFacelet(parent, path);
        } catch (IOException e) {
            throw new TagAttributeException(this.tag, null, "Invalid path : " + path);            
        } finally {
            ctx.setVariableMapper(orig);
        }
	}
}
