/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.render;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.teeda.extension.component.AbstractInclude;
import org.seasar.teeda.extension.component.TIncludeChildBody;

/**
 * @author higa
 */
public class TIncludeChildBodyRenderer extends AbstractIncludeRenderer {

    public static final String COMPONENT_FAMILY = TIncludeChildBody.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = TIncludeChildBody.DEFAULT_RENDERER_TYPE;

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeBegin(context, component);
        AbstractInclude inc = (AbstractInclude) component;
        if (!inc.isIncluded()) {
            include(context, inc);
        }
    }

    protected IncludedBody getIncludedBody(FacesContext context,
            AbstractInclude component) {

        return TViewRootRenderer.popIncludedBody(context);
    }
}