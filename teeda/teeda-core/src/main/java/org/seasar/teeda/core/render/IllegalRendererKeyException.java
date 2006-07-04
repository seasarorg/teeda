package org.seasar.teeda.core.render;

import org.seasar.teeda.core.exception.ExtendFacesException;

public class IllegalRendererKeyException extends ExtendFacesException {

    private static final long serialVersionUID = 1L;

    private String family;

    private String renderType;

    public IllegalRendererKeyException(String family, String renderType) {
        super("ETDA0028", new Object[] { family, renderType });
        this.family = family;
        this.renderType = renderType;
    }

    public String getFamily() {
        return family;
    }

    public String getRenderType() {
        return renderType;
    }

}
