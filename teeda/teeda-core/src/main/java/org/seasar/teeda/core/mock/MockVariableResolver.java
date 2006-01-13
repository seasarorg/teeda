package org.seasar.teeda.core.mock;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

import org.seasar.teeda.core.JsfConstants;

public class MockVariableResolver extends VariableResolver {

    private Map values_ = new HashMap();

    private boolean inited_ = false;

    public MockVariableResolver() {
    }

    public void putValue(String key, Object value) {
        values_.put(key, value);
    }

    public Object resolveVariable(FacesContext context, String name)
            throws EvaluationException {
        if (!inited_) {
            mergePut(JsfConstants.APPLICATION_SCOPE, context.getExternalContext()
                    .getApplicationMap());
            mergePut(JsfConstants.SESSION_SCOPE, context.getExternalContext()
                    .getSessionMap());
            mergePut(JsfConstants.REQUEST_SCOPE, context.getExternalContext()
                    .getRequestMap());
            inited_ = true;
        }
        return values_.get(name);
    }

    private void mergePut(String key, Map value) {
        Map previous = (Map) values_.get(key);
        if (previous != null) {
            previous.putAll(value);
        } else {
            values_.put(key, value);
        }
    }

}
