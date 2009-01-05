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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.taglib.html.InputSecretTag;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlInputSecret;

/**
 * @author manhole
 */
public class TInputSecretTag extends InputSecretTag {

    private String errorStyleClass;

    public String getErrorStyleClass() {
        return errorStyleClass;
    }

    public void setErrorStyleClass(final String errorStyleClass) {
        this.errorStyleClass = errorStyleClass;
    }

    public String getComponentType() {
        return THtmlInputSecret.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlInputSecret.DEFAULT_RENDERER_TYPE;
    }

    protected void setProperties(final UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, ExtensionConstants.ERROR_STYLE_CLASS,
                getErrorStyleClass());
    }

    public void release() {
        super.release();
        errorStyleClass = null;
    }

}
