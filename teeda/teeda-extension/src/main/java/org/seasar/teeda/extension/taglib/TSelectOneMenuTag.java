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
package org.seasar.teeda.extension.taglib;

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.extension.component.html.THtmlSelectOneMenu;
import org.seasar.teeda.extension.util.NullLabelHelper;

/**
 * @author higa
 * @author shot
 */
public class TSelectOneMenuTag extends TSelectTagBase {

    private static final String RENDERER_TYPE = "javax.faces.Menu";

    public String getComponentType() {
        return THtmlSelectOneMenu.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return RENDERER_TYPE;
    }

    protected boolean isRequired() {
        final String v = getValue();
        FacesContext context = getFacesContext();
        if (context == null) {
            context = FacesContext.getCurrentInstance();
        }
        NullLabelHelper helper = (NullLabelHelper) DIContainerUtil
                .getComponentNoException(NullLabelHelper.class);
        return helper.isRequired(context, v);
    }

}