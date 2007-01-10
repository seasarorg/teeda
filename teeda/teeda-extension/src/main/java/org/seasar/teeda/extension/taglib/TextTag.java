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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

import org.seasar.teeda.extension.component.UIText;

/**
 * @author higa
 * @author manhole
 */
public class TextTag extends UIComponentTag {

    private String value;

    public TextTag() {
    }

    /*
     * UITextはfacetの子どもにしない。
     * 改行のみのTextが本来facet配下に来るべきcomponentを押しのけてしまうため。
     */
    protected UIComponent createFacet(final FacesContext context,
            final UIComponent parent, final String name, final String newId) {
        final UIComponent component = createComponent(context, newId);
        return component;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComponentType() {
        return UIText.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return UIText.DEFAULT_RENDERER_TYPE;
    }

    protected void setProperties(UIComponent component) {
        UIText text = (UIText) component;
        text.setValue(value);
    }

    public void release() {
        super.release();
        value = null;
    }

}
