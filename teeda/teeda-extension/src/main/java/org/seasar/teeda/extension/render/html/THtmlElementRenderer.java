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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.html.THtmlElement;

/**
 * @author higa
 *
 */
public class THtmlElementRenderer extends Renderer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.HtmlElement";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.HtmlElement";

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        if (!component.isRendered()) {
            return;
        }
        ResponseWriter writer = context.getResponseWriter();
        if (!(component instanceof THtmlElement)) {
            throw new IllegalArgumentException("not THtmlElement");
        }
        THtmlElement elem = (THtmlElement) component;
        String tagName = elem.getTagName();
        writer.startElement(tagName, component);
        renderAttributes(writer, elem);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        THtmlElement elem = (THtmlElement) component;
        ResponseWriter writer = context.getResponseWriter();
        if (component.isRendered()) {
            writer.endElement(elem.getTagName());
        }
    }

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        if (!component.isRendered()) {
            return;
        }
        RendererUtil.renderChildren(context, component);
    }

    protected void renderAttributes(ResponseWriter writer,
            THtmlElement component) throws IOException {

        if (component.isIdSet()) {
            String id = component.getId();
            RendererUtil.renderAttribute(writer, JsfConstants.ID_ATTR, id,
                    JsfConstants.ID_ATTR);
        }
        Map attrs = component.getAttributes();
        for (Iterator i = attrs.keySet().iterator(); i.hasNext();) {
            String attrName = (String) i.next();
            if (attrName.indexOf('.') > 0) {
                continue;
            }
            Object value = component.getAttributes().get(attrName);
            RendererUtil.renderAttribute(writer, attrName, value, attrName);
        }
        String[] bindingPropertyNames = component.getBindingPropertyNames();
        for (int i = 0; i < bindingPropertyNames.length; ++i) {
            String name = bindingPropertyNames[i];
            Object value = BindingUtil.getBindingValue(component, name);
            RendererUtil.renderAttribute(writer, name, value, name);
        }
    }
}