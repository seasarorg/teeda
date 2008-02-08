/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.render.html.HtmlSelectOneRadioRenderer;

/**
 * @author manhole
 */
public class THtmlInputRadioRenderer extends HtmlSelectOneRadioRenderer {

    public static final String COMPONENT_FAMILY = HtmlSelectOneRadioRenderer.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.HtmlInputRadio";

    public void encodeBegin(final FacesContext context,
            final UIComponent component) throws IOException {
        // NO OP
        assertNotNull(context, component);
    }

    public void encodeEnd(final FacesContext context,
            final UIComponent component) throws IOException {
        // NO OP
        assertNotNull(context, component);
    }

}
