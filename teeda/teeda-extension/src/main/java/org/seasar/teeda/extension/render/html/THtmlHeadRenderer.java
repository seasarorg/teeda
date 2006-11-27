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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.extension.util.VirtualResource;

/**
 * @author higa
 */
public class THtmlHeadRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.HtmlHead";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.HtmlHead";

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        final ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.HEAD_ELEM, component);
        writer.write("\n");
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        ResponseWriter writer = context.getResponseWriter();
        Set resources = VirtualResource.getJSResources(context);
        for (Iterator i = resources.iterator(); i.hasNext();) {
            String path = (String) i.next();
            renderIncludeJavaScript(writer, VirtualResource
                    .convertVirtualPath(path));
            writer.write("\n");
        }
        writer.endElement(JsfConstants.HEAD_ELEM);
        writer.write("\n");
    }
}