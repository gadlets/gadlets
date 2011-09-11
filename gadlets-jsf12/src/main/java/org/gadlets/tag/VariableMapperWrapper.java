package org.gadlets.tag;

import java.util.HashMap;
import java.util.Map;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.context.FacesContext;

import com.sun.facelets.FaceletContext;

/**
 * Utility class for wrapping another VariableMapper with a new context,
 * represented by a {@link java.util.Map Map}. Modifications occur to the Map
 * instance, but resolve against the wrapped VariableMapper if the Map doesn't
 * contain the ValueExpression requested.
 * 
 * @author Jacob Hookom
 * @version $Id: VariableMapperWrapper.java,v 1.7 2008/07/13 19:01:43 rlubke Exp
 *          $
 */
public final class VariableMapperWrapper extends VariableMapper implements IGadletsVariableMapper {

	private final VariableMapper target;

	private Map vars;

	/**
	 * 
	 */
	public VariableMapperWrapper(VariableMapper orig) {
		super();
		this.target = orig;
	}
	
	public String getValue(String argument) {
		ValueExpression ve = resolveVariable(argument);
		if(ve == null) {
			return null;
		}
		return ve.getValue(FacesContext.getCurrentInstance().getELContext()).toString();
	}

	/**
	 * First tries to resolve agains the inner Map, then the wrapped
	 * ValueExpression.
	 * 
	 * @see javax.el.VariableMapper#resolveVariable(java.lang.String)
	 */
	public ValueExpression resolveVariable(String variable) {
		ValueExpression ve = null;
		try {
			if (this.vars != null) {
				ve = (ValueExpression) this.vars.get(variable);
			}
			if (ve == null) {
				return this.target.resolveVariable(variable);
			}
			return ve;
		} catch (StackOverflowError e) {
			throw new ELException("Could not Resolve Variable [Overflow]: "
					+ variable, e);
		}
	}

	/**
	 * Set the ValueExpression on the inner Map instance.
	 * 
	 * @see javax.el.VariableMapper#setVariable(java.lang.String,
	 *      javax.el.ValueExpression)
	 */
	public ValueExpression setVariable(String variable,
			ValueExpression expression) {
		if (this.vars == null) {
			this.vars = new HashMap();
		}
		return (ValueExpression) this.vars.put(variable, expression);
	}
}
