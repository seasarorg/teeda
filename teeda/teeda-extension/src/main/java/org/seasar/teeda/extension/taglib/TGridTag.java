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

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlGrid;

/**
 * @author manhole
 */
public class TGridTag extends TForEachTag {

    public TGridTag() {
    }

    private String scrollVertical;

    private String scrollHorizontal;

    private String async;

    private String callback;

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getComponentType() {
        return THtmlGrid.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlGrid.DEFAULT_RENDERER_TYPE;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.WIDTH_ATTR, getWidth());
        setComponentProperty(component, JsfConstants.HEIGHT_ATTR, getHeight());
        setComponentProperty(component, ExtensionConstants.SCROLL_HORIZONTAL,
                getScrollHorizontal());
        setComponentProperty(component, ExtensionConstants.SCROLL_VERTICAL,
                getScrollVertical());
        setComponentProperty(component, ExtensionConstants.ASYNC, getAsync());
        setComponentProperty(component, ExtensionConstants.CALLBACK,
                getCallback());
    }

    public String getScrollHorizontal() {
        return scrollHorizontal;
    }

    public void setScrollHorizontal(String scrollHorizontal) {
        this.scrollHorizontal = scrollHorizontal;
    }

    public String getScrollVertical() {
        return scrollVertical;
    }

    public void setScrollVertical(String scrollVertical) {
        this.scrollVertical = scrollVertical;
    }

    public String getAsync() {
        return async;
    }

    public void setAsync(String async) {
        this.async = async;
    }

    public void release() {
        super.release();
        scrollVertical = null;
        scrollHorizontal = null;
        async = null;
        callback = null;
    }
}
