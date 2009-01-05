/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.component.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.seasar.teeda.core.util.BindingUtil;

/**
 * @author higa
 * 
 */
public class THtmlElement extends UIComponentBase {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlElement";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.HtmlElement";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlElement";

    private String tagName;

    private List bindingPropertyNames = new ArrayList();

    private boolean idSet = false;

    public THtmlElement() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void encodeEnd(FacesContext context) throws IOException {
        Renderer renderer = getRenderer(context);
        if (renderer != null) {
            renderer.encodeEnd(context, this);
        }
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public boolean isIdSet() {
        return idSet;
    }

    public void setIdSet(boolean idSet) {
        this.idSet = idSet;
    }

    public void setValueBindingAttribute(String name, String value) {
        BindingUtil.setValueBinding(this, name, value);
        bindingPropertyNames.add(name);
    }

    public String[] getBindingPropertyNames() {
        return (String[]) bindingPropertyNames
                .toArray(new String[bindingPropertyNames.size()]);
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[5];
        values[0] = super.saveState(context);
        values[1] = tagName;
        values[2] = bindingPropertyNames;
        values[3] = Boolean.valueOf(idSet);
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        tagName = (String) values[1];
        bindingPropertyNames = (List) values[2];
        idSet = ((Boolean) values[3]).booleanValue();
    }
}