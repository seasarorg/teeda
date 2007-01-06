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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;

/**
 * @author yone
 */
public class GraphicImageTag extends UIComponentTagBase {

    private String url;

    public String getComponentType() {
        return HtmlGraphicImage.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Image";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.URL_ATTR, getUrl());
        setComponentProperty(component, JsfConstants.ALT_ATTR, getAlt());
        setComponentProperty(component, JsfConstants.HEIGHT_ATTR, getHeight());
        setComponentProperty(component, JsfConstants.ISMAP_ATTR, getIsmap());
        setComponentProperty(component, JsfConstants.LONGDESC_ATTR,
                getLongdesc());
        setComponentProperty(component, JsfConstants.USEMAP_ATTR, getUsemap());
        setComponentProperty(component, JsfConstants.WIDTH_ATTR, getWidth());
        setComponentProperty(component, JsfConstants.BORDER_ATTR, getBorder());
    }

    public void release() {
        super.release();
        url = null;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
