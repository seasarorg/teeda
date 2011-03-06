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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;
import org.seasar.teeda.extension.component.html.THtmlLink;

/**
 * @author shot
 */
public class TLinkTag extends UIComponentTagBase {

    public TLinkTag() {
    }

    private String rel;

    private String href;

    private String src;

    public String getComponentType() {
        return THtmlLink.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlLink.DEFAULT_RENDERER_TYPE;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.REL_ATTR, rel);
        setComponentProperty(component, JsfConstants.HREF_ATTR, href);
        setComponentProperty(component, JsfConstants.SRC_ATTR, src);
    }

    public void release() {
        super.release();
        rel = null;
        href = null;
        src = null;
    }

    public String getHref() {
        return href;
    }

    public String getRel() {
        return rel;
    }

    public String getSrc() {
        return src;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public void setSrc(String src) {
        this.src = src;
    }

}
