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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.taglib.UIComponentTagBase;
import org.seasar.teeda.extension.component.TForEach;

/**
 * @author higa
 *  
 */
public class TForEachTag extends UIComponentTagBase {

    private String tagName;

    private String pageName;

    private String itemsName;

    private String omittag;

    public TForEachTag() {
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getItemsName() {
        return itemsName;
    }

    public void setItemsName(String itemsName) {
        this.itemsName = itemsName;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getOmittag() {
        return omittag;
    }

    public void setOmittag(String omittag) {
        this.omittag = omittag;
    }

    public String getComponentType() {
        return TForEach.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "org.seasar.teeda.extension.ForEach";
    }

    /**
     * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
     */
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, "tagName", tagName);
        setComponentProperty(component, "pageName", pageName);
        setComponentProperty(component, "itemsName", itemsName);
        setComponentProperty(component, "omittag", omittag);
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release() {
        super.release();
        tagName = null;
        pageName = null;
        itemsName = null;
        omittag = null;
    }
}