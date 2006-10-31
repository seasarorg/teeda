/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

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
        if(PostbackUtil.isPostback(requestMap)) {
            String value = ValueHolderUtil.getValueForRender(context, component);
            return new String[] { value };
        } else {
            return null;
        }
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

}
