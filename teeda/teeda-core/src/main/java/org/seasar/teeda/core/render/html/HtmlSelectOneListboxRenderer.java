/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.render.html;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlSelectOneListbox;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.PostbackUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author manhole
 */
public class HtmlSelectOneListboxRenderer extends HtmlSelectManyListboxRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.SelectOne";

    public static final String RENDERER_TYPE = "javax.faces.Listbox";

    protected void renderMultiple(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
    }

    protected String[] getValuesForRender(FacesContext context,
            UIComponent component) {
        final ExternalContext externalContext = context.getExternalContext();
        final Map requestMap = externalContext.getRequestMap();
        final String value = ValueHolderUtil.getValueForRender(context, component);
        if (value == null) {
            return null;
        }
        if (!PostbackUtil.isPostback(requestMap)) {
            final ValueBinding vb = component.getValueBinding("value");
            if (vb != null) {
                final Class type = vb.getType(context);
                if (type.isPrimitive() && value.equals("0")) {
                    return null;
                }
            }
        }
        return new String[] { value };
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        getDecoder().decode(context, component);
    }

    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException {
        assertNotNull(context, component);
        return RendererUtil.getConvertedUIOutputValue(context,
                (UIOutput) component, submittedValue);
    }

    protected void renderStyleClass(FacesContext context,
            UIComponent component, ResponseWriter writer) throws IOException {
        HtmlSelectOneListbox htmlSelectOneListbox = (HtmlSelectOneListbox) component;
        final String styleClass = htmlSelectOneListbox.getStyleClass();
        RendererUtil.renderAttribute(writer, JsfConstants.STYLE_CLASS_ATTR,
                styleClass);
    }

}
